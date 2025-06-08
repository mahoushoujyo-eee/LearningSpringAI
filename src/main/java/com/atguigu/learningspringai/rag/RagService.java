package com.atguigu.learningspringai.rag;

import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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



    //直接往向量存储中添加文本文件
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

    //添加文字内容
    public void addDocumentToVectorStore(String content, String documentName) {
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

    //添加pdf文件到向量库中
    public void addPdfToVectorStore(String documentName) throws IOException
    {
        Resource documentResource = documentsDirectory.createRelative(documentName);
        List<Document> documents = readPdfAsDocument(documentResource.getFile().getAbsolutePath());
        if (documents != null)
        {
            System.out.println("Reading PDF: " + documentName);
        }
        else
        {
            System.out.println("No documents found.");
            return;
        }
        for (Document document : documents) {
            System.out.println("Document: " + document.getText());
            addDocumentToVectorStore(document.getText(), documentName);
        }

        System.out.println("Document '" + documentName + "' added to vector store.");
    }

    //检查读取pdf文件
    public void checkPdf(String documentName) throws IOException
    {
        Resource documentResource = documentsDirectory.createRelative(documentName);
        if (!documentResource.exists()) {
            throw new IOException("文件不存在: " + documentName);
        }

        List<Document> documents = readPdfAsDocument(documentResource.getFile().getAbsolutePath());
        for (Document document : documents) {
            System.out.println("Document: " + document.getText());
        }
    }

    //读取pdf文件为Document对象，辅助方法
    public List<Document> readPdfAsDocument(String filePath) {
        ExtractedTextFormatter extractedTextFormatter = ExtractedTextFormatter
                .builder()
                .withNumberOfTopTextLinesToDelete(0)
                .build();

        PdfDocumentReaderConfig pdfDocumentReaderConfig = PdfDocumentReaderConfig
                .builder()
                .withPageTopMargin(0)
                .withPageExtractedTextFormatter(extractedTextFormatter)
                .withPagesPerDocument(13)
                .build();

        PagePdfDocumentReader pdfReader = getPagePdfDocumentReader(filePath, pdfDocumentReaderConfig);

        return pdfReader.read();
    }

    //创建PagePdfDocumentReader对象
    private static PagePdfDocumentReader getPagePdfDocumentReader(String filePath, PdfDocumentReaderConfig pdfDocumentReaderConfig) {
        PagePdfDocumentReader pdfReader;
        try {
            // 如果是绝对路径，使用file:前缀
            if (new File(filePath).isAbsolute()) {
                pdfReader = new PagePdfDocumentReader("file:" + filePath, pdfDocumentReaderConfig);
            } else {
                // 如果是相对路径，当作classpath资源处理
                pdfReader = new PagePdfDocumentReader("classpath:" + filePath, pdfDocumentReaderConfig);
            }
        } catch (Exception e) {
            throw new RuntimeException("无法读取PDF文件: " + filePath, e);
        }
        return pdfReader;
    }
}