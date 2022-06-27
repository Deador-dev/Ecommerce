package com.plocky.deador.service;

import com.plocky.deador.entity.Order;
import com.plocky.deador.repository.OrderItemRepository;
import com.plocky.deador.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    OrderRepository orderRepository;
    OrderItemRepository orderItemRepository;
    ProductService productService;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Optional<Order> getOrderById(Integer id) {
        return orderRepository.findById(id);
    }

    public List<Order> getAllOrdersByEmail(String userEmail) {
        return orderRepository.findAllByEmail(userEmail);
    }

}
