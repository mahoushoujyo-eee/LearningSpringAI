package com.atguigu.learningspringai.mapper;

import com.atguigu.learningspringai.entity.ChatMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ChatMessageMapper {
    // 插入一条消息记录
    void insertMessage(ChatMessage message);

    // 批量插入消息记录
    void batchInsertMessages(List<ChatMessage> messages);

    // 获取指定会话的最近N条消息
    List<ChatMessage> getLastNMessages(@Param("conversationId") String conversationId, @Param("lastN") int lastN);

    // 删除指定会话的所有消息
    void deleteByConversationId(String conversationId);
}
