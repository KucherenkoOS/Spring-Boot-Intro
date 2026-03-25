package org.example.springbootintro.dto.cart;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddToCartRequestDto {
    @Schema(description = "Book ID to add to cart", example = "1")
    private Long bookId;

    @Positive
    @Schema(description = "Quantity of books", example = "2")
    private int quantity;
}
