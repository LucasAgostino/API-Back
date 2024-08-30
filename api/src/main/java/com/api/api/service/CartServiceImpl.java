package com.api.api.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.api.DTO.CartDto;
import com.api.api.entity.Cart;
import com.api.api.entity.CartProduct;
import com.api.api.entity.Order;
import com.api.api.entity.Product;
import com.api.api.entity.User;
import com.api.api.repository.CartProductRepository;
import com.api.api.repository.CartRepository;
import com.api.api.repository.ProductRepository;
import com.api.api.repository.UserRepository;
import com.api.api.service.DAO.CartDao;
import com.api.api.service.Interfaces.CartService;
import com.api.api.service.Interfaces.OrderProductService;
import com.api.api.service.Interfaces.OrderService;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartProductRepository cartProductRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderProductService orderProductService;

    @Autowired
    private CartDao cartDao;

    @Override
    public void createCart(Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (!userOpt.isPresent()) {
            throw new RuntimeException("User not found");
        }
        User user = userOpt.get();
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setCreationDate(LocalDateTime.now());
        cartRepository.save(cart);
    }

    @Override
    public String addProduct(Long productId, Long userId, int quantity) {
        Product product = getProduct(productId);
        
        Optional<Cart> cartOpt = cartRepository.findByUserId(userId);
        Cart cart = cartOpt.orElseThrow(() -> new RuntimeException("Cart not found"));
    
        CartProduct existingCartProduct = cartProductRepository.findByCartAndProduct(cart, product);
        
        if (existingCartProduct != null) {
            int oldStock = existingCartProduct.getQuantity();
            if (!checkStock(product, oldStock + quantity)) {
                return "Insufficient stock";
            }
            existingCartProduct.setQuantity(oldStock + quantity);
            existingCartProduct.setTotalPrice(product.getPrice() * (oldStock + quantity));
            cartProductRepository.save(existingCartProduct);
        } else {
            CartProduct newCartProduct = new CartProduct();
            if (!checkStock(product, quantity)) {
                return "Insufficient stock";
            }
            newCartProduct.setCart(cart);
            newCartProduct.setProduct(product);
            newCartProduct.setQuantity(quantity);
            newCartProduct.setTotalPrice(product.getPrice() * quantity);
            cartProductRepository.save(newCartProduct);
        }
    
        return "Product added or updated in the cart";
    }

    @Override
    public String removeProduct(Long productId, Long userId) {
        Product product = getProduct(productId);
        
        Optional<Cart> cartOpt = cartRepository.findByUserId(userId);
        Cart cart = cartOpt.orElseThrow(() -> new RuntimeException("Cart not found"));
        
        CartProduct cartProduct = cartProductRepository.findByCartAndProduct(cart, product);
        
        if (cartProduct != null) {
            cartProductRepository.delete(cartProduct);
            return "Product removed from the cart";
        } else {
            return "Product not found in the cart";
        }
    }

    @Override
    public String decreaseCart(Long userId, Long productId, int quantity) {
        Optional<Cart> cartOpt = cartRepository.findByUserId(userId);
        Cart cart = cartOpt.orElseThrow(() -> new RuntimeException("Cart not found"));

        Product product = getProduct(productId);
        CartProduct existingCartProduct = cartProductRepository.findByCartAndProduct(cart, product);

        if (existingCartProduct != null) {
            int currentQuantity = existingCartProduct.getQuantity();

            if (currentQuantity < quantity) {
                return "Cannot decrease more quantity than what is in the cart";
            }

            int newQuantity = currentQuantity - quantity;

            if (newQuantity > 0) {
                existingCartProduct.setQuantity(newQuantity);
                existingCartProduct.setTotalPrice(product.getPrice() * newQuantity);
                cartProductRepository.save(existingCartProduct);
            } else {
                cartProductRepository.delete(existingCartProduct);
            }

            return "Product decreased in the cart";
        }

        return "Product not found in the cart";
    }

    @Override
    public void placeOrder(Long userId) {
        Optional<Cart> cartOpt = cartRepository.findByUserId(userId);
        Cart cart = cartOpt.orElseThrow(() -> new RuntimeException("Cart not found"));
        
        List<CartProduct> products = cartProductRepository.findByCart(cart);
        
        if (products.isEmpty()) {
            throw new RuntimeException("The cart is empty. Cannot place an order without products.");
        }
        
        float totalOrder = 0;
        
        for (CartProduct cartProduct : products) {
            Product product = cartProduct.getProduct();
            int quantity = cartProduct.getQuantity();
            
            if (!checkStock(product, quantity)) {
                throw new RuntimeException("Insufficient stock for the product: " + product.getProductName());
            }
            
            totalOrder += cartProduct.getTotalPrice();
        }

        Order order = orderService.createOrder(userId, totalOrder);

        for (CartProduct cartProduct : products) {
            Product product = cartProduct.getProduct();
            int quantity = cartProduct.getQuantity();
            
            product.setStock(product.getStock() - quantity);
            productRepository.save(product);
            
            orderProductService.addProduct(order.getOrderId(), product.getProductId(), quantity);
        }
        
        cartProductRepository.deleteAll(products);
    }

    @Override
    public Optional<CartDto> findCart(Long userId) {
        return cartDao.findByCartId(userId);
    }

    private Product getProduct(Long productId) {
        Optional<Product> productOpt = productRepository.findById(productId);
        if (!productOpt.isPresent()) {
            return null;
        }
        return productOpt.get();
    }

    private boolean checkStock(Product product, int quantity) {
        return product.getStock() >= quantity;
    }
}
