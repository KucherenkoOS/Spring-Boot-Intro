package org.example.springbootintro.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.springbootintro.model.Status;

@Data
public class UpdateOrderStatusDto {
    @NotNull(message = "Status cannot be null")
    private Status status;
}
