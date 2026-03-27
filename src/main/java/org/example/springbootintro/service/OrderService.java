package org.example.springbootintro.service;

import java.util.List;
import org.example.springbootintro.dto.order.CreateOrderRequestDto;
import org.example.springbootintro.dto.order.OrderDto;
import org.example.springbootintro.dto.order.OrderItemDto;
import org.example.springbootintro.model.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    OrderDto placeOrder(String userEmail, CreateOrderRequestDto requestDto);

    Page<OrderDto> getOrders(String userEmail, Pageable pageable);

    List<OrderItemDto> getOrderItems(Long orderId, String email);

    OrderItemDto getOrderItem(Long orderId, Long itemId, String email);

    OrderDto updateOrderStatus(Long orderId, Status status);
}
