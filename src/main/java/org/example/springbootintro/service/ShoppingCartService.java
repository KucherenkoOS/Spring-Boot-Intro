package org.example.springbootintro.service;

import org.example.springbootintro.dto.cart.AddToCartRequestDto;
import org.example.springbootintro.dto.cart.ShoppingCartDto;
import org.example.springbootintro.dto.cart.UpdateCartItemDto;
import org.example.springbootintro.model.User;

public interface ShoppingCartService {

    ShoppingCartDto getCartByEmail(String email);

    void addBookToCart(String email, AddToCartRequestDto dto);

    void updateItem(Long cartItemId, UpdateCartItemDto dto);

    void createShoppingCartForUser(User user);

    void deleteItem(Long cartItemId);
}
