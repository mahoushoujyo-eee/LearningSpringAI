package com.atguigu.learningspringai.controller;

import com.atguigu.learningspringai.context.JdbcChatMemory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RequestMapping("/chat-memory")
@RestController
public class ChatMemoryController
{
    @Autowired
    ChatClient chatClient;

    @GetMapping("/chat")
    public String memoryChat(@RequestParam(value = "message",defaultValue = "") String message ,
                             @RequestParam(value = "conversationId",defaultValue = "default") String conversationId)
    {
        log.info("userId:{}",conversationId);

        String  response = chatClient.prompt()
                .user(message)
                //这里的key-value如何配合ChatMemory使用？两个进行都不会影响对话记录
                //破案了，根本就没使用
                //终于找到了常量的位置
                .advisors(advisor -> advisor.param(ChatMemory.CONVERSATION_ID, conversationId))
                .call()
                .content();

        return response;
    }

}
