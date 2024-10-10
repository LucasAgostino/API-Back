package com.api.api.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.Authentication;

import com.api.api.DTO.OrderDto;
import com.api.api.entity.Order;
import com.api.api.entity.User;
import com.api.api.repository.OrderRepository;
import com.api.api.repository.UserRepository;
import com.api.api.service.DAO.OrderDao;
import com.api.api.service.Interfaces.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<OrderDto> findAll() {
        return orderDao.findAll();
    }

    @Override
    public Optional<OrderDto> findById(Long id) {
        return orderDao.findById(id);
    }

    @Override
    public List<OrderDto> findByUserId(Long userId) {
        return orderDao.findByUserId(userId);
    }

    @Transactional
    @Override
    public Order createOrder(Long userId, float total) {
        Optional<User> userOpt = userRepository.findById(userId);
        User user = userOpt.orElseThrow(() -> new RuntimeException("User not found"));
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setOrderTotal(total);
        return orderRepository.save(order);
    }

    @Override
    public Optional<OrderDto> findUserOrderById(Long orderId, Long userId) {
        // Verifica si la orden pertenece al usuario
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isPresent() && orderOpt.get().getUser().getId().equals(userId)) {
            return orderDao.findById(orderId); // Retorna la orden si el usuario es el dueño
        }
        return Optional.empty(); // Si no, retorna vacío
    }

    public Long getCurrentUserId() {
        // Obtén el ID del usuario autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal(); 
        return user.getId();
    }
}
