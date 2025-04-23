package me.marioscalas.saikata.images.adapters.web;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import me.marioscalas.saikata.images.AIPromptService;
import me.marioscalas.saikata.images.model.Question;
import me.marioscalas.saikata.images.model.VisionDescriptionAnswer;

/**
 * Simple REST API for images generation.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/images")
public class ImagesPromptController {
    
    private final AIPromptService aiPromptService;
    
    ImagesPromptController(AIPromptService aiPromptService) {
        this.aiPromptService = aiPromptService;
    }

    /**
     * Generates an image from a prompt and returns it as a response.
     *
     * @param question the prompt to generate the image from
     * @return the generated image as a byte array
     */
    @Operation(summary = "Generate an image from a prompt and return it as response")
    @PostMapping(produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE}, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<byte[]> generateImage(@RequestBody @Valid Question question) {
        log.debug("Generating image for prompt: {}", question.text());
        return ResponseEntity.ok().body(
            aiPromptService.generateImage(question)    
        );
    }

    @Operation(summary = "Identify the content within and image and return it as text response")
    @PostMapping(value = "/describe", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VisionDescriptionAnswer> describeImage(@RequestParam("imageFile") @Valid MultipartFile imageFile) {
        return ResponseEntity.ok().body(
            aiPromptService.describeImage(imageFile)    
        );
    }    
}
