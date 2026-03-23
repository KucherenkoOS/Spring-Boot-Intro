package org.example.springbootintro.dto.cart;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCartItemDto {

    @Positive
    @Schema(description = "New quantity for the cart item", example = "5")
    private int quantity;
}
