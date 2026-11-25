package com.example.ai_service.controller;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ai/embeddings")
public class EmbeddingController {

    private final EmbeddingModel embeddingModel;

    // spring injects the EmbeddingModel bean, which is configured in the application properties
    public EmbeddingController(EmbeddingModel embeddingModel) {
        this.embeddingModel = embeddingModel;
    }

    // 1. test embedding generation for a given text and return the vector representation
    @GetMapping("/inspect")
    public Map<String, Object> inspectEmbedding(
            @RequestParam(value = "text", defaultValue = "placute de frana uzate") String text) {

        float[] vector = this.embeddingModel.embed(text);

        // Extragem primele 10 valori pentru afisare lizibila in JSON
        List<Float> preview = new ArrayList<>();
        for (int i = 0; i < Math.min(vector.length, 10); i++) {
            preview.add(vector[i]);
        }

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("textOriginal", text);
        result.put("dimensiuneVector", vector.length);
        result.put("primele10Valori", preview);
        return result;
    }

    // 2. Demonstreaza cautarea semantica prin calculul Cosine Similarity intre concepte auto
    @GetMapping("/similarity")
    public Map<String, Object> compareAutomotiveConcepts(
            @RequestParam(value = "base", defaultValue = "placute de frana tocite") String base,
            @RequestParam(value = "related", defaultValue = "sistem de franare defect") String related,
            @RequestParam(value = "unrelated", defaultValue = "schimb ulei motor si filtru") String unrelated) {

        float[] baseVector = this.embeddingModel.embed(base);
        float[] relatedVector = this.embeddingModel.embed(related);
        float[] unrelatedVector = this.embeddingModel.embed(unrelated);

        double similarityRelated = calculateCosineSimilarity(baseVector, relatedVector);
        double similarityUnrelated = calculateCosineSimilarity(baseVector, unrelatedVector);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("termenReferinta", base);
        result.put("termenInrudit", related);
        result.put("scorSimilitudineInrudit", Math.round(similarityRelated * 10000.0) / 10000.0);
        result.put("termenDiferit", unrelated);
        result.put("scorSimilitudineDiferit", Math.round(similarityUnrelated * 10000.0) / 10000.0);
        return result;
    }

    // Formula matematica: produsul scalar impartit la produsul normelor euclidiene
    private double calculateCosineSimilarity(float[] vectorA, float[] vectorB) {
        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;

        for (int i = 0; i < vectorA.length; i++) {
            dotProduct += vectorA[i] * vectorB[i];
            normA += Math.pow(vectorA[i], 2);
            normB += Math.pow(vectorB[i], 2);
        }
        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }
}