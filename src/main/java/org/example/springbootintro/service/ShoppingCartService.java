package org.example.springbootintro.service;

import org.example.springbootintro.dto.cart.AddToCartRequestDto;
import org.example.springbootintro.dto.cart.ShoppingCartDto;
import org.example.springbootintro.dto.cart.UpdateCartItemDto;

public interface ShoppingCartService {

    ShoppingCartDto getCartByEmail(String email);

    void addBookByEmail(String email, AddToCartRequestDto dto);

    void updateItem(Long cartItemId, UpdateCartItemDto dto);

    void deleteItem(Long cartItemId);
}
