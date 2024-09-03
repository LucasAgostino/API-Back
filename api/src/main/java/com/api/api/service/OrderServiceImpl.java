package com.api.api.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
