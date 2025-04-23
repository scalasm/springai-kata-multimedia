package me.marioscalas.saikata.images.internal;

import java.util.Base64;

import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import jakarta.validation.Valid;
import me.marioscalas.saikata.images.AIPromptService;
import me.marioscalas.saikata.images.model.Question;

@Component
public class OpenAIPromptServiceImpl implements AIPromptService {

    @Value("classpath:/templates/get-stock-prices-prompt.st")
    private Resource getStockPricesPromptTemplate;

    private final ImageModel imageModel;
    
    public OpenAIPromptServiceImpl(ImageModel imageModel) {
        this.imageModel = imageModel;
    }

    @Override
    public byte[] generateImage(@Valid Question question) {
        final OpenAiImageOptions options = OpenAiImageOptions.builder()
            .width(1024).height(1024)
            .withResponseFormat("b64_json")
            .withModel("dall-e-3")
            .build();


        final ImagePrompt prompt = new ImagePrompt(
            question.text(), options
        );

        final ImageResponse imageResponse = imageModel.call(prompt);

        return Base64.getDecoder().decode(
            imageResponse.getResult().getOutput().getB64Json()
        );
    }
}
