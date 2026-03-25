package org.example.springbootintro.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.springbootintro.dto.order.CreateOrderRequestDto;
import org.example.springbootintro.dto.order.OrderDto;
import org.example.springbootintro.dto.order.OrderItemDto;
import org.example.springbootintro.dto.order.UpdateOrderStatusDto;
import org.example.springbootintro.model.User;
import org.example.springbootintro.service.OrderService;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Tag(name = "Order management", description = "Endpoints for managing orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Place a new order")
    public OrderDto placeOrder(Authentication authentication,
                               @RequestBody @Valid CreateOrderRequestDto dto) {
        User user = (User) authentication.getPrincipal();
        return orderService.placeOrder(user.getEmail(), dto);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Get all orders by user")
    public List<OrderDto> getOrders(Authentication authentication, Pageable pageable) {
        User user = (User) authentication.getPrincipal();
        return orderService.getOrders(user.getEmail(), pageable);
    }

    @GetMapping("/{orderId}/items")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Get all items in order")
    public List<OrderItemDto> getOrderItems(@PathVariable Long orderId,
                                            Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return orderService.getOrderItems(orderId, user.getEmail());
    }

    @GetMapping("/{orderId}/items/{itemId}")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Get item info by item id")
    public OrderItemDto getOrderItem(@PathVariable Long orderId,
                                     @PathVariable Long itemId,
                                     Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return orderService.getOrderItem(orderId, itemId, user.getEmail());
    }

    @PatchMapping("/{orderId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update order status (admin operation)")
    public OrderDto updateOrderStatus(@PathVariable Long orderId,
                                      @RequestBody @Valid UpdateOrderStatusDto dto) {
        return orderService.updateOrderStatus(orderId, dto.getStatus());
    }
}
