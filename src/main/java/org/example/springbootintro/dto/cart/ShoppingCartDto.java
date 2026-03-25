package org.example.springbootintro.dto.cart;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShoppingCartDto {
    @Schema(description = "Shopping cart ID", example = "1")
    private Long id;

    @Schema(description = "ID of the user who owns the cart", example = "1")
    private Long userId;

    @Schema(description = "Set of items in the shopping cart")
    private Set<CartItemDto> cartItems;
}
