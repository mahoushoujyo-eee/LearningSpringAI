package com.atguigu.learningspringai.context;


import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.context.annotation.Configuration;

import java.util.List;

//重写会话记忆配置可以自定义会话记忆存储方式
@Configuration
public class JdbcChatMemory implements ChatMemory
{

    @Override
    public void add(String conversationId, Message message) {
        ChatMemory.super.add(conversationId, message);
    }

    @Override
    public void add(String conversationId, List<Message> messages) {

    }

    @Override
    public List<Message> get(String conversationId, int lastN) {
        return List.of();
    }

    @Override
    public void clear(String conversationId) {

    }
}
