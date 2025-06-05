package com.atguigu.learningspringai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/ai/role")
@RestController
public class RoleChatController
{
    @Autowired
    private ChatClient chatClient;

    @GetMapping("/chat/1")
    public String chat(@RequestParam(value = "message",defaultValue = "你是谁")
                       String message)
    {
        return chatClient.prompt()
                .user(message)
                //.functions("addOperation", "mulOperation")
                .call()
                .content();
    }
}
