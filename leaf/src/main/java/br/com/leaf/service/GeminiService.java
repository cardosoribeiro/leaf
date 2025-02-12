package br.com.leaf.service; 

import java.io.IOException;

public interface GeminiService {
    String generateText(String prompt) throws IOException;
}