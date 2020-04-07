package bg.sofia.uni.fmi.mjt.authorship.detection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

import static bg.sofia.uni.fmi.mjt.authorship.detection.Author.createAuthor;
import static bg.sofia.uni.fmi.mjt.authorship.detection.FeatureType.*;

public class AuthorshipDetectorImpl implements AuthorshipDetector {

    private static final int STAT_AVERAGE_WORD_LENGTH = 0;
    private static final int STAT_TYPE_TOKEN_RATIO = 1;
    private static final int STAT_HAPAX_LEGOMENA_RATIO = 2;
    private static final int STAT_AVERAGE_SENTENCE_LENGTH = 3;
    private static final int STAT_AVERAGE_SENTENCE_COMPLEXITY = 4;

    private InputStream signaturesDataset;
    private double[] weights;

    private Set<Author> authors;

    public AuthorshipDetectorImpl(InputStream signaturesDataset, double[] weights) {
        this.signaturesDataset = signaturesDataset;
        this.weights = weights;
        this.authors = createAuthors();
    }

    @Override
    public LinguisticSignature calculateSignature(InputStream mysteryText) {
        if (mysteryText == null) {
            throw new IllegalArgumentException("InputStream is null.");
        }

        TextProperties textProperties = new TextProperties(mysteryText);

        Map<FeatureType, Double> features = new HashMap<>();
        features.put(AVERAGE_WORD_LENGTH,
                calculateAverageWordLength(textProperties.getNumberOfWords(), textProperties.getSumOfLengthOfWords()));
        features.put(TYPE_TOKEN_RATIO,
                calculateTypeTokenRatio(textProperties.getNumberOfWords(), textProperties.getNumberOfDistinctWords()));
        features.put(HAPAX_LEGOMENA_RATIO, calculateHapaxLegomenaRatio(textProperties.getNumberOfWords(),
                textProperties.getNumberOfWordsIncludedOnce()));
        features.put(AVERAGE_SENTENCE_LENGTH, calculateAverageSentenceLength(textProperties.getNumberOfWords(),
                textProperties.getNumberOfSentences()));
        features.put(AVERAGE_SENTENCE_COMPLEXITY, calculateAverageSentenceComplexity(textProperties
                .getNumberOfPhrases(), textProperties.getNumberOfSentences()));

        return new LinguisticSignature(features);
    }

    private double calculateAverageWordLength(double numberOfWords, double sumLengthWords) {
        return (numberOfWords != 0) ? sumLengthWords / numberOfWords : 0;
    }

    private double calculateTypeTokenRatio(double numberOfAllWords, double numberOfDistinctWords) {
        return (numberOfAllWords != 0) ? numberOfDistinctWords / numberOfAllWords : 0;
    }

    private double calculateHapaxLegomenaRatio(double numberOfAllWords, double numberOfWordsIncludedOnce) {
        return (numberOfAllWords != 0) ? numberOfWordsIncludedOnce / numberOfAllWords : 0;
    }

    private double calculateAverageSentenceLength(double numberOfAllWords, double numberOfAllSentences) {
        return (numberOfAllSentences != 0) ? numberOfAllWords / numberOfAllSentences : 0;
    }

    private double calculateAverageSentenceComplexity(double numberOfAllPhrases, double numberOfAllSentences) {
        return (numberOfAllSentences != 0) ? numberOfAllPhrases / numberOfAllSentences : 0;
    }

    @Override
    public double calculateSimilarity(LinguisticSignature firstSignature, LinguisticSignature secondSignature) {
        if (firstSignature == null || secondSignature == null) {
            throw new IllegalArgumentException("Null linguistic signature");
        }

        double similarity = 0;
        Map<FeatureType, Double> firstFeatures = firstSignature.getFeatures();
        Map<FeatureType, Double> secondFeatures = secondSignature.getFeatures();
        similarity += Math.abs(firstFeatures.get(AVERAGE_WORD_LENGTH) - secondFeatures.get(AVERAGE_WORD_LENGTH))
                * weights[STAT_AVERAGE_WORD_LENGTH];
        similarity += Math.abs(firstFeatures.get(TYPE_TOKEN_RATIO) - secondFeatures.get(TYPE_TOKEN_RATIO))
                * weights[STAT_TYPE_TOKEN_RATIO];
        similarity += Math.abs(firstFeatures.get(HAPAX_LEGOMENA_RATIO) - secondFeatures.get(HAPAX_LEGOMENA_RATIO))
                * weights[STAT_HAPAX_LEGOMENA_RATIO];
        similarity += Math.abs(firstFeatures.get(AVERAGE_SENTENCE_LENGTH) - secondFeatures.get(AVERAGE_SENTENCE_LENGTH))
                * weights[STAT_AVERAGE_SENTENCE_LENGTH];
        similarity += Math.abs(firstFeatures.get(AVERAGE_SENTENCE_COMPLEXITY)
                - secondFeatures.get(AVERAGE_SENTENCE_COMPLEXITY)) * weights[STAT_AVERAGE_SENTENCE_COMPLEXITY];

        return similarity;
    }

    @Override
    public String findAuthor(InputStream mysteryText) {
        if (mysteryText == null) {
            throw new IllegalArgumentException("Null input stream");
        }

        LinguisticSignature signature = calculateSignature(mysteryText);

        return authors.stream()
                .min(Comparator.comparing(x -> calculateSimilarity(x.getSignature(), signature)))
                .get().getName();
    }

    private Set<Author> createAuthors() {
        String line;
        Set<Author> authors = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(signaturesDataset))) {
            while ((line = br.readLine()) != null) {
                authors.add(createAuthor(line));
            }
        } catch (IOException e) {
            System.err.println("Error while reading from buffer.");
        }
        return authors;
    }

    public Set<Author> getAuthors() {
        return authors;
    }
}
