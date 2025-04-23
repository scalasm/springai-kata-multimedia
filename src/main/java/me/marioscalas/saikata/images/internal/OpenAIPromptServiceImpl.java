package me.marioscalas.saikata.images.internal;

import java.util.Base64;
import java.util.List;

import org.apache.james.mime4j.dom.Multipart;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.model.Media;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import me.marioscalas.saikata.images.AIPromptService;
import me.marioscalas.saikata.images.model.Question;
import me.marioscalas.saikata.images.model.VisionDescriptionAnswer;

@Component
public class OpenAIPromptServiceImpl implements AIPromptService {

    private static final String DALL_E_3_MODEL = "dall-e-3";
    private static final String B64_JSON_FORMAT = "b64_json";

    // There is a more generic ImageModel interface, but we are using OpenAiImageModel
    private final OpenAiImageModel imageModel;

    private final OpenAiChatModel chatModel;
    
    public OpenAIPromptServiceImpl(OpenAiImageModel imageModel, OpenAiChatModel chatModel) {
        this.imageModel = imageModel;
        this.chatModel = chatModel;
    }

    @Override
    public byte[] generateImage(@Valid Question question) {
        final OpenAiImageOptions options = OpenAiImageOptions.builder()
            .width(1024).height(1024)
            .withResponseFormat(B64_JSON_FORMAT)
            .withModel(DALL_E_3_MODEL)
            .withQuality("hd") // Only DALL-E 3 supports this
            .withStyle("vivid") // "vivid" or "natural", default is "vivid"
            .build();


        final ImagePrompt prompt = new ImagePrompt(
            question.text(), options
        );

        final ImageResponse imageResponse = imageModel.call(prompt);

        return Base64.getDecoder().decode(
            imageResponse.getResult().getOutput().getB64Json()
        );
    }

    @Override
    public VisionDescriptionAnswer describeImage(MultipartFile imageFile) {
        final OpenAiChatOptions chatOptions = OpenAiChatOptions.builder()
            .model(OpenAiApi.ChatModel.GPT_4_TURBO.getValue())
            .temperature(0.7)
            .maxTokens(1000)
            .build();
        
        MimeType imageMimeType = MimeTypeUtils.parseMimeType(imageFile.getContentType());
        final var userMessage = new UserMessage("Explain the image in detail",
            List.of(
                new Media(imageMimeType, imageFile.getResource())        
            )
        );
        final String responseContent = ChatClient.create(chatModel)
            .prompt(new Prompt(userMessage))
            .options(chatOptions)
            .call()
            .content();

        return new VisionDescriptionAnswer(responseContent);
    }
}
