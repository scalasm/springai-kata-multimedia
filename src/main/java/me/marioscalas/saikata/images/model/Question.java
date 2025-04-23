package me.marioscalas.saikata.images.model;

import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request for an image to be generated.")
public record Question(
    @NotNull
    @Schema(description = "The request for the image", example = "Get me a picture of a cat playing with a ball")
    String text
) {
}
