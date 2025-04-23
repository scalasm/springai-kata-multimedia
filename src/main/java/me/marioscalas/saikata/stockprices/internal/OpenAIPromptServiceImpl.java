package me.marioscalas.saikata.stockprices.internal;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import jakarta.validation.Valid;
import me.marioscalas.saikata.stockprices.PromptService;
import me.marioscalas.saikata.stockprices.model.StockPriceAnswer;
import me.marioscalas.saikata.stockprices.model.StockPriceQuestion;

@Component
public class OpenAIPromptServiceImpl implements PromptService {

    @Value("classpath:/templates/get-stock-prices-prompt.st")
    private Resource getStockPricesPromptTemplate;

    private final OpenAiChatModel chatModel;
    
    public OpenAIPromptServiceImpl(OpenAiChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @Override
    public StockPriceAnswer getAnswer(@Valid StockPriceQuestion question) {
        final Prompt prompt = new Prompt(
            new SystemMessage(getStockPricesPromptTemplate),
            new UserMessage(question.text())
        );

        final String responseContent = ChatClient.create(chatModel)
            .prompt(prompt)
            .call()
            .content();

        return new StockPriceAnswer(responseContent);
    }
}
