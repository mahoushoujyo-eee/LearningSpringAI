package com.atguigu.learningspringai.controller;

import io.modelcontextprotocol.client.McpAsyncClient;
import io.modelcontextprotocol.spec.McpSchema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.mcp.AsyncMcpToolCallbackProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/mcp")
public class MCPController
{
    @Autowired
    List<McpAsyncClient> mcpAsyncClients;

    @Autowired
    private ChatClient chatClient;

    @Autowired
    private AsyncMcpToolCallbackProvider toolCallbackProvider;

    @RequestMapping("/test")
    public Mono<McpSchema.CallToolResult> test() {
        var mcpClient = mcpAsyncClients.get(0);

        return mcpClient.listTools()
                .flatMap(tools -> {
                    log.info("tools: {}", tools);

                    return mcpClient.callTool(
                            new McpSchema.CallToolRequest(
                                    "maps_weather",
                                    Map.of("city", "北京")
                            )
                    );
                });
    }

    @RequestMapping("/chat")
    public String chat(@RequestParam(value = "message",defaultValue = "你是谁") String message,
                       @RequestParam(value = "conversationId",defaultValue = "default") String conversationId)
    {
        String  response = chatClient.prompt()
                .user(message)
                .toolCallbacks(toolCallbackProvider)
                .advisors(advisor -> advisor.param(ChatMemory.CONVERSATION_ID, conversationId))
                .call()
                .content();

        return response;
    }

}
