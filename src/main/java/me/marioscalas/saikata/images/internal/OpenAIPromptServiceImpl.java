package me.marioscalas.saikata.images.internal;

import java.util.Base64;

import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.stereotype.Component;

import jakarta.validation.Valid;
import me.marioscalas.saikata.images.AIPromptService;
import me.marioscalas.saikata.images.model.Question;

@Component
public class OpenAIPromptServiceImpl implements AIPromptService {

    private static final String DALL_E_3_MODEL = "dall-e-3";
    private static final String B64_JSON_FORMAT = "b64_json";

    private final ImageModel imageModel;
    
    public OpenAIPromptServiceImpl(ImageModel imageModel) {
        this.imageModel = imageModel;
    }

    @Override
    public byte[] generateImage(@Valid Question question) {
        final OpenAiImageOptions options = OpenAiImageOptions.builder()
            .width(1024).height(1024)
            .withResponseFormat(B64_JSON_FORMAT)
            .withModel(DALL_E_3_MODEL)
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
