package org.example.springbootintro.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CreateBookRequestDto {
    @NotBlank
    private String title;

    @NotBlank
    private String author;

    @NotBlank
    @Size(min = 10, max = 14)
    private String isbn;

    @NotNull
    @Positive
    private BigDecimal price;

    private String description;

    private String coverImage;
}
