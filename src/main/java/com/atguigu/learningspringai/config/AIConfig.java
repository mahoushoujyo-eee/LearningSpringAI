package com.atguigu.learningspringai.config;

import com.atguigu.learningspringai.context.JdbcChatMemory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AIConfig {

    @Autowired
    MessageChatMemoryAdvisor messageChatMemoryAdvisor;

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder) {
        // 使用内存聊天记录
        InMemoryChatMemory chatMemory = new InMemoryChatMemory();

        return builder
                .defaultAdvisors(
                        messageChatMemoryAdvisor
                )
                .defaultSystem("你是Qwen4大模型，" +
                "是Alidada开发的。").build();
    }
}