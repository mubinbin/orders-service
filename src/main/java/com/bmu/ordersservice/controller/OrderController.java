package com.bmu.ordersservice.controller;

import com.bmu.ordersservice.dto.OrderRequest;
import com.bmu.ordersservice.model.Order;
import com.bmu.ordersservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest orderRequest) throws SQLException {
        orderService.placeOrder(orderRequest);
        return "Orders placed successfully!";
    }

    @GetMapping("/{orderNumber}")
    @ResponseStatus(HttpStatus.OK)
    public Order getOrderById(@PathVariable String orderNumber) {
        return orderService.getOrderByOrderNumber(orderNumber);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String deleteOrderById(@PathVariable Long id) {
        orderService.deleteOrderById(id);
        return "Order " + id + " is deleted";
    }


    @DeleteMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String deleteAllOrder() {
        orderService.deleteAllOrder();
        return "All orders deleted successfully";
    }
}
