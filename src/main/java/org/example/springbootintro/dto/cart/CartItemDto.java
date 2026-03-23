package org.example.springbootintro.dto.cart;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemDto {
    @Schema(description = "Cart item ID", example = "5")
    private Long id;

    @Schema(description = "Book ID", example = "1")
    private Long bookId;

    @Schema(description = "Title of the book", example = "The Fellowship of the Ring")
    private String bookTitle;

    @Positive
    @Schema(description = "Quantity of books in this item", example = "2")
    private int quantity;
}
