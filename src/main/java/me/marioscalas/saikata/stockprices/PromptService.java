package me.marioscalas.saikata.stockprices;

import me.marioscalas.saikata.stockprices.model.StockPriceAnswer;
import me.marioscalas.saikata.stockprices.model.StockPriceQuestion;

public interface PromptService {
    StockPriceAnswer getAnswer(StockPriceQuestion question);
}
