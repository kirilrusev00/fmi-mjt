package bg.sofia.uni.fmi.mjt.authorship.detection;

import java.util.HashMap;
import java.util.Map;

public final class Author {

    private String name;
    private LinguisticSignature signature;

    private static final int NAME = 0;
    private static final int STAT_AVERAGE_WORD_LENGTH = 1;
    private static final int STAT_TYPE_TOKEN_RATIO = 2;
    private static final int STAT_HAPAX_LEGOMENA_RATIO = 3;
    private static final int STAT_AVERAGE_SENTENCE_LENGTH = 4;
    private static final int STAT_AVERAGE_SENTENCE_COMPLEXITY = 5;

    private Author(String name, LinguisticSignature signature) {
        this.name = name;
        this.signature = signature;
    }

    public static Author createAuthor(String line) {

        String[] tokens = line.split(", ");

        String name = tokens[NAME];
        Map<FeatureType, Double> features = new HashMap<>();
        features.put(FeatureType.AVERAGE_WORD_LENGTH, Double.parseDouble(tokens[STAT_AVERAGE_WORD_LENGTH]));
        features.put(FeatureType.TYPE_TOKEN_RATIO, Double.parseDouble(tokens[STAT_TYPE_TOKEN_RATIO]));
        features.put(FeatureType.HAPAX_LEGOMENA_RATIO, Double.parseDouble(tokens[STAT_HAPAX_LEGOMENA_RATIO]));
        features.put(FeatureType.AVERAGE_SENTENCE_LENGTH, Double.parseDouble(tokens[STAT_AVERAGE_SENTENCE_LENGTH]));
        features.put(FeatureType.AVERAGE_SENTENCE_COMPLEXITY,
                Double.parseDouble(tokens[STAT_AVERAGE_SENTENCE_COMPLEXITY]));

        return new Author(name, new LinguisticSignature(features));
    }

    public String getName() {
        return name;
    }

    public LinguisticSignature getSignature() {
        return signature;
    }
}
