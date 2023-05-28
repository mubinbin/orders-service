package com.bmu.ordersservice.service;

import com.bmu.ordersservice.dto.OrderLineItemsDto;
import com.bmu.ordersservice.dto.OrderRequest;
import com.bmu.ordersservice.dto.OrderResponse;
import com.bmu.ordersservice.model.Order;
import com.bmu.ordersservice.model.OrderLineItems;
import com.bmu.ordersservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderResponse getOrderByOrderNumber(String orderNumber) {
        Order order = orderRepository.getOrderByOrderNumber(orderNumber);
        List<OrderLineItemsDto> orderLineItemsDtoList = order.getOrderLineItemsList()
                .stream()
                .map(this::mapItemToDto)
                .collect(Collectors.toList());

        return OrderResponse.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .orderLineItemsDtoList(orderLineItemsDtoList)
                .build();
    }

    private OrderLineItemsDto mapItemToDto(OrderLineItems orderLineItems) {
        return OrderLineItemsDto.builder()
                .price(orderLineItems.getPrice())
                .quantity(orderLineItems.getQuantity())
                .skuCode(orderLineItems.getSkuCode())
                .build();
    }

    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItemsList = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapDtoToItems)
                .collect(Collectors.toList());
        order.setOrderLineItemsList(orderLineItemsList);

        orderRepository.save(order);
    }

    private OrderLineItems mapDtoToItems(OrderLineItemsDto orderLineItemsDto) {
        return OrderLineItems.builder()
                .price(orderLineItemsDto.getPrice())
                .skuCode(orderLineItemsDto.getSkuCode())
                .quantity(orderLineItemsDto.getQuantity())
                .build();
    }

    public void deleteAllOrder() {
        orderRepository.deleteAll();
    }

    public void deleteOrderById(Long id) {
        orderRepository.deleteById(id);
    }
}
