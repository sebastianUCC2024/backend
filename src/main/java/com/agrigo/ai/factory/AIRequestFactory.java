package com.agrigo.ai.factory;

import com.agrigo.farmer.entity.Crop;

public interface AIRequestFactory {
    String createPrompt(Crop crop);
}
