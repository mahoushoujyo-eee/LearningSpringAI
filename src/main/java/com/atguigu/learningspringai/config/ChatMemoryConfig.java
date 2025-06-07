package com.atguigu.learningspringai.config;

import com.atguigu.learningspringai.context.RepositoryChatMemory;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepositoryDialect;
import org.springframework.ai.chat.memory.repository.jdbc.MysqlChatMemoryRepositoryDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class ChatMemoryConfig {

    //对上下文记忆的配置，持久化到数据库中
    @Bean
    public JdbcChatMemoryRepository jdbcChatMemoryRepository(JdbcTemplate jdbcTemplate) {
        return JdbcChatMemoryRepository.builder()
                .jdbcTemplate(jdbcTemplate)
                //可以使用JdbcChatMemoryRepositoryDialect.from(datasource)的形式补齐
                //new MysqlChatMemoryRepositoryDialect()
                .dialect(new MysqlChatMemoryRepositoryDialect())
                .build();
    }

    @Bean
    public ChatMemory chatMemory(JdbcChatMemoryRepository jdbcChatMemoryRepository) {

        //return MessageWindowChatMemory.builder().build();
        return MessageWindowChatMemory.builder()
                .chatMemoryRepository(jdbcChatMemoryRepository)
                .maxMessages(10) // Configure the maximum number of messages to retain
                .build();
    }
}