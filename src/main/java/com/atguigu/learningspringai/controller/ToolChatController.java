package com.atguigu.learningspringai.controller;


import com.atguigu.learningspringai.tool.ToolTest;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/tool")
@RestController
public class ToolChatController
{
    @Autowired
    private ChatClient chatClient;

    @RequestMapping("/chat")
    public String chat(@RequestParam(value = "message",defaultValue = "你是谁") String message,
                       @RequestParam(value = "conversationId",defaultValue = "default") String conversationId)
    {
        return chatClient.prompt()
                .user(message)
                .advisors(advisor -> advisor.param(ChatMemory.CONVERSATION_ID, conversationId))
                .tools(new ToolTest())
                .call()
                .content();
    }
}
