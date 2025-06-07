package com.atguigu.learningspringai.context;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.util.Assert;

//重写了一个框架内的类MessageWindowChatMemory
@Slf4j
public class RepositoryChatMemory implements ChatMemory 
{
    private static final int DEFAULT_MAX_MESSAGES = 20;
    private final ChatMemoryRepository chatMemoryRepository;
    private final int maxMessages;

    private RepositoryChatMemory(ChatMemoryRepository chatMemoryRepository, int maxMessages) {
        Assert.notNull(chatMemoryRepository, "chatMemoryRepository cannot be null");
        Assert.isTrue(maxMessages > 0, "maxMessages must be greater than 0");
        this.chatMemoryRepository = chatMemoryRepository;
        this.maxMessages = maxMessages;
    }

    @Override
    public void add(String conversationId, Message message) {
        add(conversationId, List.of(message));
    }

    public void add(String conversationId, List<Message> messages) {
        Assert.hasText(conversationId, "conversationId cannot be null or empty");
        Assert.notNull(messages, "messages cannot be null");
        Assert.noNullElements(messages, "messages cannot contain null elements");
        List<Message> memoryMessages = this.chatMemoryRepository.findByConversationId(conversationId);
        List<Message> processedMessages = this.process(memoryMessages, messages);
        log.info("Memory messages: {}", memoryMessages);
        log.info("Processed messages: {}", processedMessages);
        this.chatMemoryRepository.saveAll(conversationId, processedMessages);
    }


    public List<Message> get(String conversationId) {
        Assert.hasText(conversationId, "conversationId cannot be null or empty");
        return this.chatMemoryRepository.findByConversationId(conversationId);
    }

    public void clear(String conversationId) {
        Assert.hasText(conversationId, "conversationId cannot be null or empty");
        this.chatMemoryRepository.deleteByConversationId(conversationId);
    }

    private List<Message> process(List<Message> memoryMessages, List<Message> newMessages) {
        List<Message> processedMessages = new ArrayList<>();
        Set<Message> memoryMessagesSet = new HashSet<>(memoryMessages);

        // 检查是否有新的SystemMessage不在已有消息集合中
        boolean hasNewSystemMessage = newMessages.stream()
                .filter(message -> message instanceof SystemMessage)
                .anyMatch(message -> !memoryMessagesSet.contains(message));

        // 如果有新的SystemMessage，过滤掉原有的SystemMessage
        memoryMessages.stream()
                .filter(message -> !hasNewSystemMessage || !(message instanceof SystemMessage))
                .forEach(processedMessages::add);

        processedMessages.addAll(newMessages);

        if (processedMessages.size() <= this.maxMessages) {
            return processedMessages;
        } else {
            int messagesToRemove = processedMessages.size() - this.maxMessages;
            List<Message> trimmedMessages = new ArrayList<>();
            int removed = 0;

            for(Message message : processedMessages) {
                if (!(message instanceof SystemMessage) && removed < messagesToRemove) {
                    ++removed;
                } else {
                    trimmedMessages.add(message);
                }
            }

            return trimmedMessages;
        }
    }

    public static RepositoryChatMemory.Builder builder() {
        return new RepositoryChatMemory.Builder();
    }

    public static final class Builder {
        private ChatMemoryRepository chatMemoryRepository;
        private int maxMessages = 20;

        private Builder() {
        }

        public RepositoryChatMemory.Builder chatMemoryRepository(ChatMemoryRepository chatMemoryRepository) {
            this.chatMemoryRepository = chatMemoryRepository;
            return this;
        }

        public RepositoryChatMemory.Builder maxMessages(int maxMessages) {
            this.maxMessages = maxMessages;
            return this;
        }

        public RepositoryChatMemory build() {
            if (this.chatMemoryRepository == null) {
                this.chatMemoryRepository = new InMemoryChatMemoryRepository();
            }

            return new RepositoryChatMemory(this.chatMemoryRepository, this.maxMessages);
        }
    }
}
