package com.plocky.deador.repository;

import com.plocky.deador.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    public List<OrderItem> findAll();
    public List<OrderItem> findAllByOrderId(Integer id);
}
