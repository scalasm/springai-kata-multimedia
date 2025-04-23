package me.marioscalas.saikata.images;

import org.springframework.web.multipart.MultipartFile;

import me.marioscalas.saikata.images.model.Question;
import me.marioscalas.saikata.images.model.VisionDescriptionAnswer;

public interface AIPromptService {
    byte[] generateImage(Question question);

    VisionDescriptionAnswer describeImage(MultipartFile imageFile);
}
