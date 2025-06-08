package com.atguigu.learningspringai.config;

import org.springframework.ai.document.MetadataMode;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.ai.openai.OpenAiEmbeddingOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.retry.RetryUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class EmbeddingConfig
{
    @Bean
    @Primary
    public EmbeddingModel embeddingClient()
    {
        OpenAiApi openAiApi = OpenAiApi.builder()
                .baseUrl("https://dashscope.aliyuncs.com/compatible-mode")
                .apiKey("sk-a4d9b8b15f6a494b92491f3b6f2129f2")
                .build();
        OpenAiEmbeddingOptions embeddingOptions = OpenAiEmbeddingOptions.builder()
                .model("text-embedding-v4")
                .build();

        return new OpenAiEmbeddingModel(openAiApi, MetadataMode.EMBED,embeddingOptions, RetryUtils.DEFAULT_RETRY_TEMPLATE);
    }
}
