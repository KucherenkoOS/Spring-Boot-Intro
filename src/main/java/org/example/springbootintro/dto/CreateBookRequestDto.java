package org.example.springbootintro.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Request DTO for creating a new book")
public class CreateBookRequestDto {
    @Schema(description = "Book title", example = "The Fellowship of the Ring")
    @NotBlank
    private String title;

    @Schema(description = "Book author", example = "J. R. R. Tolkien")
    @NotBlank
    private String author;

    @Schema(description = "ISBN number", example = "9780261102354")
    @NotBlank
    @Size(min = 10, max = 14)
    private String isbn;

    @Schema(description = "Book price", example = "13.99")
    @NotNull
    @Positive
    private BigDecimal price;

    @Schema(description = "Short description of the book",
            example = "The first volume of The Lord of the Rings where Frodo begins his journey.")
    private String description;

    @Schema(description = "URL to the book cover image",
            example = "https://example.com/images/fellowship_of_the_ring.jpg")
    private String coverImage;
}
