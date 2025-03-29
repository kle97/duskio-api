package com.duskio.common.service;

import com.duskio.common.constant.Constant;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class EnglishDictionary {
    
    private Set<String> englishDictionary;
    
    public boolean contains(String word) {
        if (englishDictionary == null) {
            try {
                this.englishDictionary = new HashSet<>();
                String englishWordsPath = Constant.MISC_RESOURCES_PATH + "words_alpha.txt";
                try (BufferedReader reader = Files.newBufferedReader(Path.of(englishWordsPath), Constant.ENCODING)) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (!(line = line.trim().toLowerCase()).isEmpty()) {
                            englishDictionary.add(line);
                        }
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return englishDictionary.contains(word);
    }
}
