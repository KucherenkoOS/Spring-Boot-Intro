package org.example.springbootintro.service.impl;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.springbootintro.dto.cart.AddToCartRequestDto;
import org.example.springbootintro.dto.cart.ShoppingCartDto;
import org.example.springbootintro.dto.cart.UpdateCartItemDto;
import org.example.springbootintro.exception.EntityNotFoundException;
import org.example.springbootintro.mapper.ShoppingCartMapper;
import org.example.springbootintro.model.Book;
import org.example.springbootintro.model.CartItem;
import org.example.springbootintro.model.ShoppingCart;
import org.example.springbootintro.repository.BookRepository;
import org.example.springbootintro.repository.CartItemRepository;
import org.example.springbootintro.repository.ShoppingCartRepository;
import org.example.springbootintro.service.ShoppingCartService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final BookRepository bookRepository;
    private final ShoppingCartMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public ShoppingCartDto getCartByEmail(String email) {
        ShoppingCart cart = shoppingCartRepository.findByUserEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Cart not found"));

        return mapper.toDto(cart);
    }

    @Override
    @Transactional
    public void addBookByEmail(String email, AddToCartRequestDto dto) {
        ShoppingCart cart = shoppingCartRepository.findByUserEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Cart not found"));

        Book book = bookRepository.findById(dto.getBookId())
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));

        Optional<CartItem> existingItem = cart.getCartItems()
                .stream()
                .filter(i -> i.getBook().getId().equals(book.getId()))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + dto.getQuantity());
        } else {
            CartItem item = new CartItem();
            item.setShoppingCart(cart);
            item.setBook(book);
            item.setQuantity(dto.getQuantity());

            cart.getCartItems().add(item);
        }

        shoppingCartRepository.save(cart);
    }

    @Override
    @Transactional
    public void updateItem(Long cartItemId, UpdateCartItemDto dto) {
        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Cart item not found with id: " + cartItemId)
                );

        item.setQuantity(dto.getQuantity());
    }

    @Override
    @Transactional
    public void deleteItem(Long cartItemId) {
        if (!cartItemRepository.existsById(cartItemId)) {
            throw new EntityNotFoundException("Can't find cart item by id: " + cartItemId);
        }
        cartItemRepository.deleteById(cartItemId);
    }
}
