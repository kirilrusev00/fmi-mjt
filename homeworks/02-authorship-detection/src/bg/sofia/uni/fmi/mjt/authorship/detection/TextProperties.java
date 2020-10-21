package bg.sofia.uni.fmi.mjt.authorship.detection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class TextProperties {
    private double numberOfWords;
    private double numberOfDistinctWords;
    private double numberOfWordsIncludedOnce;
    private double sumOfLengthOfWords;
    private double numberOfPhrases;
    private double numberOfSentences;

    public TextProperties(InputStream mysteryText) {
        if (mysteryText == null) {
            throw new IllegalArgumentException();
        }

        String text = getTextAsString(mysteryText);
        List<String> allWords = getAllWords(text);
        List<String> allSentences = getAllSentences(text);
        List<String> allPhrases = getAllPhrases(allSentences);

        setNumberOfWords(allWords);
        setNumberOfDistinctWords(allWords);
        setNumberOfWordsIncludedOnce(allWords);
        setSumOfLengthOfWords(allWords);
        setNumberOfPhrases(allPhrases);
        setNumberOfSentences(allSentences);
    }

    private List<String> getAllTokens(String mysteryText) {
        return Arrays.asList(mysteryText.split("\\s+"));
    }

    private List<String> getAllWords(String mysteryText) {
        List<String> allTokens = getAllTokens(mysteryText);
        List<String> allWords = new ArrayList<>();
        for (String token : allTokens) {
            String word = cleanUp(token);
            if (!word.isEmpty()) {
                allWords.add(word);
            }
        }
        return allWords;
    }

    private List<String> getAllSentences(String text) {
        List<String> allSentences = new ArrayList<>();
        String[] sentences = text.split("[.!?]");
        for (String sentence : sentences) {
            if (!sentence.trim().isEmpty()) {
                allSentences.add(sentence.trim());
            }
        }
        return allSentences;
    }

    private List<String> getAllPhrases(List<String> allSentences) {
        List<String> allPhrases = new ArrayList<>();
        for (String sentence : allSentences) {
            String[] phrases = sentence.split("[,:;]");
            for (String phrase : phrases) {
                if (!phrase.trim().isEmpty()) {
                    allPhrases.add(phrase.trim());
                }
            }
        }
        return allPhrases;
    }

    private String getTextAsString(InputStream mysteryText) {
        StringBuilder textBuilder = new StringBuilder();
        String line;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(mysteryText))) {
            while ((line = br.readLine()) != null) {
                textBuilder.append(System.lineSeparator()).append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return textBuilder.toString().trim();
    }

    private void setNumberOfWords(List<String> allWords) {
        this.numberOfWords = allWords.size();
    }

    private void setNumberOfDistinctWords(List<String> allWords) {
        Set<String> distinctWords = new HashSet<>(allWords);
        this.numberOfDistinctWords = distinctWords.size();
    }

    private void setNumberOfWordsIncludedOnce(List<String> allWords) {
        Set<String> wordsIncludedOnce = new HashSet<>();
        for (String word : allWords) {
            if (wordsIncludedOnce.contains(word)) {
                wordsIncludedOnce.remove(word);
            } else {
                wordsIncludedOnce.add(word);
            }
        }
        this.numberOfWordsIncludedOnce = wordsIncludedOnce.size();
    }

    private void setSumOfLengthOfWords(List<String> allWords) {
        this.sumOfLengthOfWords = allWords.stream()
                .mapToInt(String::length)
                .sum();
    }

    private void setNumberOfPhrases(List<String> allPhrases) {
        this.numberOfPhrases = allPhrases.size();
    }

    private void setNumberOfSentences(List<String> allSentences) {
        this.numberOfSentences = allSentences.size();
    }

    public static String cleanUp(String word) {
        return word.toLowerCase()
                .replaceAll("^[!.,:;\\-?<>#\\*\'\"\\[\\(\\]\\n\\t\\\\]+" +
                        "|[!.,:;\\-?<>#\\*\'\"\\[\\(\\]\\n\\t\\\\]+$", "");
    }

    public double getNumberOfWords() {
        return numberOfWords;
    }

    public double getNumberOfDistinctWords() {
        return numberOfDistinctWords;
    }

    public double getNumberOfWordsIncludedOnce() {
        return numberOfWordsIncludedOnce;
    }

    public double getSumOfLengthOfWords() {
        return sumOfLengthOfWords;
    }

    public double getNumberOfPhrases() {
        return numberOfPhrases;
    }

    public double getNumberOfSentences() {
        return numberOfSentences;
    }
}
