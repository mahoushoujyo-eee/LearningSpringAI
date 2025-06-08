package com.atguigu.learningspringai.config;

import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TextSplitterConfig {

    @Bean
    public TextSplitter textSplitter() {
        // 可以根据需要调整 chunk_size 和 chunk_overlap
        // chunk_size: 每个文本块的最大token数量
        // chunk_overlap: 相邻文本块之间的重叠token数量，有助于保持上下文连贯性
        return new TokenTextSplitter();
    }
}