package org.example.springbootintro.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.springbootintro.dto.order.CreateOrderRequestDto;
import org.example.springbootintro.dto.order.OrderDto;
import org.example.springbootintro.dto.order.OrderItemDto;
import org.example.springbootintro.exception.EntityNotFoundException;
import org.example.springbootintro.exception.OrderProcessingException;
import org.example.springbootintro.mapper.OrderMapper;
import org.example.springbootintro.model.CartItem;
import org.example.springbootintro.model.Order;
import org.example.springbootintro.model.OrderItem;
import org.example.springbootintro.model.ShoppingCart;
import org.example.springbootintro.model.Status;
import org.example.springbootintro.repository.OrderRepository;
import org.example.springbootintro.repository.ShoppingCartRepository;
import org.example.springbootintro.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public OrderDto placeOrder(String email, CreateOrderRequestDto dto) {
        ShoppingCart cart = shoppingCartRepository.findByUserEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Can't find cart by email: "
                        + email));

        if (cart.getCartItems().isEmpty()) {
            throw new OrderProcessingException("Shopping cart is empty for user: " + email);
        }

        Order order = new Order();
        order.setUser(cart.getUser());
        order.setStatus(Status.PENDING);
        order.setOrderDate(LocalDateTime.now());
        order.setShippingAddress(dto.getShippingAddress());
        order.setOrderItems(new HashSet<>());
        BigDecimal total = BigDecimal.ZERO;

        for (CartItem cartItem : cart.getCartItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setBook(cartItem.getBook());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getBook().getPrice());

            total = total
                    .add(orderItem
                            .getPrice()
                            .multiply(BigDecimal
                                    .valueOf(orderItem.getQuantity())));
            order.getOrderItems().add(orderItem);
        }

        order.setTotal(total);
        Order savedOrder = orderRepository.save(order);
        cart.getCartItems().clear();

        return orderMapper.toDto(savedOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderDto> getOrders(String email, Pageable pageable) {
        Page<Order> orders = orderRepository.findAllByUserEmail(email, pageable);
        return orders.map(orderMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderItemDto> getOrderItems(Long orderId, String email) {
        Order order = orderRepository.findByIdAndUserEmail(orderId, email)
                .orElseThrow(() -> new EntityNotFoundException("Order not found or access denied"));

        return order.getOrderItems().stream()
                .map(orderMapper::toItemDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public OrderItemDto getOrderItem(Long orderId, Long itemId, String email) {
        Order order = orderRepository.findByIdAndUserEmail(orderId, email)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        return order.getOrderItems().stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .map(orderMapper::toItemDto)
                .orElseThrow(() -> new EntityNotFoundException("OrderItem not found in this order: "
                        + itemId));
    }

    @Transactional
    @Override
    public OrderDto updateOrderStatus(Long orderId, Status status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Can't find order by id: "
                        + orderId));
        order.setStatus(status);
        return orderMapper.toDto(orderRepository.save(order));
    }
}
