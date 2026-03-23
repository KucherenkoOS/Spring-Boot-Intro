package org.example.springbootintro.repository;

import java.util.Optional;
import org.example.springbootintro.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    @Query("SELECT sc FROM ShoppingCart sc "
            + "LEFT JOIN FETCH sc.cartItems ci "
            + "LEFT JOIN FETCH ci.book "
            + "WHERE sc.user.email = :email")
    Optional<ShoppingCart> findByUserEmail(String email);
}
