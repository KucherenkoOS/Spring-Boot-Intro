package org.example.springbootintro.mapper;

import org.example.springbootintro.dto.order.OrderDto;
import org.example.springbootintro.dto.order.OrderItemDto;
import org.example.springbootintro.model.Order;
import org.example.springbootintro.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "user.id", target = "userId")
    OrderDto toDto(Order order);

    @Mapping(source = "book.id", target = "bookId")
    OrderItemDto toItemDto(OrderItem orderItem);
}
