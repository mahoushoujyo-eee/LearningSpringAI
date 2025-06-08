package com.atguigu.learningspringai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AIConfig {

    @Autowired
    private ChatMemory chatMemory;

    @Autowired
    private VectorStore  vectorStore;

    //利用一个配置类来完成ChatClient的配置，包括上下文记忆、提示词、RAG等
    @Bean
    public ChatClient chatClient(ChatClient.Builder builder) {

        return builder
                .defaultAdvisors(
                        MessageChatMemoryAdvisor.builder(chatMemory).build(),
                        QuestionAnswerAdvisor.builder(vectorStore).build()
                )
                .defaultSystem("你是Qwen4大模型，" +
                "是Alidada开发的。").build();
    }
}