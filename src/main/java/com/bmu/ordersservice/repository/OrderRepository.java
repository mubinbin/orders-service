package com.bmu.ordersservice.repository;

import com.bmu.ordersservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Order getOrderByOrderNumber(String OrderNumber);
}
