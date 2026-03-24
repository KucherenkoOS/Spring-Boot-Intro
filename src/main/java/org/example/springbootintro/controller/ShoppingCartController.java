package org.example.springbootintro.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.springbootintro.dto.cart.AddToCartRequestDto;
import org.example.springbootintro.dto.cart.ShoppingCartDto;
import org.example.springbootintro.dto.cart.UpdateCartItemDto;
import org.example.springbootintro.service.ShoppingCartService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@Tag(name = "Shopping Cart management", description = "Endpoints for managing user's shopping cart")
public class ShoppingCartController {
    private final ShoppingCartService service;

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    @Operation(summary = "Get user's shopping cart",
            description = "Retrieve the current user's shopping cart and all items in it")
    public ShoppingCartDto getCart(Authentication auth) {
        return service.getCartByEmail(auth.getName());
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    @Operation(summary = "Add item to cart",
            description = "Add a book to the user's shopping cart. "
            + "If the book is already in the cart, quantity will be updated.")
    public void add(@RequestBody @Valid AddToCartRequestDto dto,
                    Authentication auth) {
        service.addBookToCart(auth.getName(), dto);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/items/{id}")
    @Operation(summary = "Update item quantity",
            description = "Update the quantity of a specific book in the shopping cart")
    public void update(@PathVariable Long id,
                       @RequestBody @Valid UpdateCartItemDto dto) {
        service.updateItem(id, dto);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/items/{id}")
    @Operation(summary = "Remove item from cart",
            description = "Remove a specific book from the shopping cart by cart item ID")
    public void delete(@PathVariable Long id) {
        service.deleteItem(id);
    }
}
