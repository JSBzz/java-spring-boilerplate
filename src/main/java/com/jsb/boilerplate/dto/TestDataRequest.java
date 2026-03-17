package com.jsb.boilerplate.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TestDataRequest {
    @Schema(description = "ID (Required for Update and Delete)")
    private Long id;

    @NotBlank(message = "Title is required")
    @Schema(description = "Title")
    private String title;

    @Schema(description = "Content")
    private String content;

    @NotBlank(message = "Row status (I/U/D) is required")
    @Schema(description = "Row Status: I (Insert), U (Update), D (Delete)", example = "I")
    private String rowStatus;
}
