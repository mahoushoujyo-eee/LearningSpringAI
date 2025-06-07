package com.atguigu.learningspringai.tool;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;

@Slf4j
@Data
public class ToolTest
{
    @Tool(description = "测试工具调用的接口")
    public String test(String message)
    {
        log.info("测试工具调用成功！" + message);
        return "测试工具调用成功！" + message;
    }

    @Tool(description = "那一天的忧郁，忧郁起来")
    public String test2()
    {
        log.info("测试工具调用成功！");
        return "那一天的寂寞，寂寞起来";
    }
}
