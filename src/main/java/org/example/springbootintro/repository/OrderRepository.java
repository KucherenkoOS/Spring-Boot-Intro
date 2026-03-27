package org.example.springbootintro.repository;

import java.util.Optional;
import org.example.springbootintro.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findAllByUserEmail(String email, Pageable pageable);

    Optional<Order> findByIdAndUserEmail(Long id, String email);
}
