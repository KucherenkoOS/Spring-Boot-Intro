package org.example.springbootintro.mapper;

import org.example.springbootintro.dto.cart.CartItemDto;
import org.example.springbootintro.dto.cart.ShoppingCartDto;
import org.example.springbootintro.model.CartItem;
import org.example.springbootintro.model.ShoppingCart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ShoppingCartMapper {

    @Mapping(target = "userId", source = "user.id")
    ShoppingCartDto toDto(ShoppingCart cart);

    @Mapping(target = "bookId", source = "book.id")
    @Mapping(target = "bookTitle", source = "book.title")
    CartItemDto toDto(CartItem item);
}
