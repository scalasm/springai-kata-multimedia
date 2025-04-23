package me.marioscalas.saikata.images;

import me.marioscalas.saikata.images.model.Question;

public interface AIPromptService {
    byte[] generateImage(Question question);
}
