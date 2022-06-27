package com.plocky.deador.repository;

import com.plocky.deador.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findAllByEmail(String userEmail);
    List<Order> findAll();
    Optional<Order> findById(Integer id);

}
