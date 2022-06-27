package com.plocky.deador.service;

import com.plocky.deador.entity.OrderItem;
import com.plocky.deador.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService {
    OrderItemRepository orderItemRepository;

    @Autowired
    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    public List<OrderItem> getAllOrderItemsById(Integer id){
        return orderItemRepository.findAllByOrderId(id);
    }

}
