package com.atguigu.learningspringai.rag;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RagService {

    private final VectorStore vectorStore;
    private final TextSplitter textSplitter;

    @Value("classpath:docs/")
    private Resource documentsDirectory;

    @Autowired
    public RagService(VectorStore vectorStore, TextSplitter textSplitter) {
        this.vectorStore = vectorStore;
        this.textSplitter = textSplitter;
    }



    /**
     * 从resources/documents目录加载文档并添加到向量存储
     * @param filename 文件名
     * @throws IOException 如果文件读取失败
     */
    public void addDocumentFromResources(String filename) throws IOException {
        // 构建完整的资源路径
        Resource documentResource = documentsDirectory.createRelative(filename);

        if (!documentResource.exists()) {
            throw new IOException("文件不存在: " + filename);
        }

        // 读取文件内容
        String content;
        try (InputStream inputStream = documentResource.getInputStream()) {
            content = new String(inputStream.readAllBytes());
        }

        // 使用现有方法添加到向量存储
        addDocumentToVectorStore(content, filename);

        System.out.println("成功从resources加载并添加文档: " + filename);
    }

    public void addDocumentToVectorStore(String content, String documentName) {
        // 1. 加载文档 (这里简化为直接传入内容)
        Document document = new Document(content);
        document.getMetadata().put("name", documentName);

        // 2. 文本分割
        List<Document> chunks = textSplitter.split(List.of(document));

        // 3. 生成嵌入并存储到向量数据库
        // VectorStore.add() 方法会自动使用配置的 EmbeddingModel 生成嵌入向量
        vectorStore.add(chunks);
        System.out.println("Document '" + documentName + "' added to vector store.");
    }

    public void query(String queryText) {
        List<Document> documents = vectorStore.similaritySearch(queryText);
        if (documents != null) {
            for(Document document : documents)
                System.out.println("Document: " + document.getText());
        }
        else
            System.out.println("No documents found.");
    }

    // ... existing code ...
}