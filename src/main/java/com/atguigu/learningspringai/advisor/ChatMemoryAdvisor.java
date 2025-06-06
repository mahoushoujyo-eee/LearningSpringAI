package com.atguigu.learningspringai.advisor;

import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;

public class ChatMemoryAdvisor extends MessageChatMemoryAdvisor
{

    public ChatMemoryAdvisor(ChatMemory chatMemory) {
        super(chatMemory);
    }

    public ChatMemoryAdvisor(ChatMemory chatMemory, String defaultConversationId, int chatHistoryWindowSize) {
        super(chatMemory, defaultConversationId, chatHistoryWindowSize);
    }

    public ChatMemoryAdvisor(ChatMemory chatMemory, String defaultConversationId, int chatHistoryWindowSize, int order) {
        super(chatMemory, defaultConversationId, chatHistoryWindowSize, order);
    }
}
