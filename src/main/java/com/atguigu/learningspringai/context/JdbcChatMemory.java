package com.atguigu.learningspringai.context;


import com.atguigu.learningspringai.entity.ChatMessage;
import com.atguigu.learningspringai.mapper.ChatMessageMapper;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//重写会话记忆配置可以自定义会话记忆存储方式
@Configuration
public class JdbcChatMemory implements ChatMemory
{
    @Autowired
    private ChatMessageMapper chatMessageMapper;

    @Override
    public void add(String conversationId, Message message) {
        // 将Spring AI消息转换为实体对象并保存到数据库
        ChatMessage chatMessage = ChatMessage.fromMessage(conversationId, message);
        chatMessageMapper.insertMessage(chatMessage);
    }

    @Override
    public void add(String conversationId, List<Message> messages) {
        // 批量添加消息
        List<ChatMessage> chatMessages = new ArrayList<>();
        for (Message message : messages) {
            chatMessages.add(ChatMessage.fromMessage(conversationId, message));
        }
        chatMessageMapper.batchInsertMessages(chatMessages);
    }

    @Override
    public List<Message> get(String conversationId) {
        return List.of();
    }


    @Override
    public void clear(String conversationId) {
        // 清除指定会话的所有消息
        chatMessageMapper.deleteByConversationId(conversationId);
    }
}
