package me.marioscalas.saikata.stockprices.model;

import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request for a ticker stock price, with optional dates range.")
public record StockPriceQuestion(
    @NotNull
    @Schema(description = "The request for quote request", example = "Get me the quote for AAPL the previous week")
    String text
) {
}
