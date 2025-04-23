package me.marioscalas.saikata.images.adapters.web;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import me.marioscalas.saikata.images.AIPromptService;
import me.marioscalas.saikata.images.model.Question;

/**
 * Simple REST API for images generation.
 */
@RestController
@RequestMapping("/api/v1/images")
public class AIPromptController {
    
    private final AIPromptService aiPromptService;
    
    AIPromptController(AIPromptService aiPromptService) {
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
    public ResponseEntity<byte[]> submitQuestion(@RequestBody @Valid Question question) {
        return ResponseEntity.ok().body(
            aiPromptService.generateImage(question)    
        );
    }
}
