package com.atguigu.learningspringai.entity;

import lombok.Data;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.MessageType;


import java.util.Date;
import java.util.Map;

//一次实现接口尝试，不是很成功
@Data
public class ChatMessage implements Message {
    private String conversationId;
    private Date creationTime;
    private Message message;
    private MessageType messageType;

    public ChatMessage(String conversationId, Message message)
    {
        this.conversationId = conversationId;
        this.creationTime = new Date();
        this.message = message;
    }

    @Override
    public MessageType getMessageType() {
        return this.message.getMessageType();
    }

    @Override
    public String getText() {
        return message.getText();
    }

    @Override
    public Map<String, Object> getMetadata() {
        return message.getMetadata();
    }

    public static ChatMessage fromMessage(String conversationId, Message message) {
       return new ChatMessage(conversationId, message);
    }
}
