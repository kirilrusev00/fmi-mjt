package bg.sofia.uni.fmi.mjt.authorship.detection;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;
import java.util.Set;

import static org.junit.Assert.*;

public class AuthorshipDetectorTest {
    private static Set<Author> authors;
    private static AuthorshipDetectorImpl authorshipDetector;

    private static final double[] WEIGHTS = {11, 33, 30, 0.4, 4};
    private static final String SIGNATURES_FILE_PATH = "resources" + File.separator + "knownSignatures.txt";

    private static final String MYSTERY_TEXT1_FILE_PATH = "resources" + File.separator + "mysteryText1.txt";
    private static LinguisticSignature linguisticSignatureMysteryText1;
    private static final double NUMBER_OF_WORDS = 121.0;
    private static final double NUMBER_OF_DISTINCT_WORDS = 82.0;
    private static final double NUMBER_OF_WORDS_INCLUDED_ONCE = 69.0;
    private static final double SUM_OF_LENGTH_OF_WORDS = 554.0;
    private static final double NUMBER_OF_PHRASES = 15.0;
    private static final double NUMBER_OF_SENTENCES = 3.0;

    private static final String MYSTERY_TEXT2_FILE_PATH = "resources" + File.separator + "mysteryText2.txt";
    private static LinguisticSignature linguisticSignatureMysteryText2;

    private static final String MYSTERY_TEXT3_FILE_PATH = "resources" + File.separator + "mysteryText3.txt";
    private static LinguisticSignature linguisticSignatureMysteryText3;

    @BeforeClass
    public static void setUp() throws FileNotFoundException {
        authorshipDetector = new AuthorshipDetectorImpl(new FileInputStream(SIGNATURES_FILE_PATH), WEIGHTS);
        authors = authorshipDetector.getAuthors();

        InputStream mysteryText1 = new FileInputStream(MYSTERY_TEXT1_FILE_PATH);
        linguisticSignatureMysteryText1 = authorshipDetector.calculateSignature(mysteryText1);

        InputStream mysteryText2 = new FileInputStream(MYSTERY_TEXT2_FILE_PATH);
        linguisticSignatureMysteryText2 = authorshipDetector.calculateSignature(mysteryText2);

        InputStream mysteryText3 = new FileInputStream(MYSTERY_TEXT3_FILE_PATH);
        linguisticSignatureMysteryText3 = authorshipDetector.calculateSignature(mysteryText3);
    }

    @Test
    public void testCalculateSimilarityWhenEqualSignatures() {
        LinguisticSignature linguisticSignature = authors.iterator().next().getSignature();
        String assertMessage = "The number should be 0.";
        assertEquals(assertMessage, 0, authorshipDetector.calculateSimilarity(linguisticSignature,
                linguisticSignature), 0.0);
    }

    @Test
    public void testCalculateSimilarityWhenNotEqualSignatures() {
        String assertMessage = "The number should not be 0.";
        assertNotEquals(assertMessage, 0, authorshipDetector
                .calculateSimilarity(linguisticSignatureMysteryText1, linguisticSignatureMysteryText2), 0.0);
    }

    @Test
    public void testCalculateAverageWordLength() {
        String assertMessage = "The average word length is not calculated correctly.";
        double expected = SUM_OF_LENGTH_OF_WORDS / NUMBER_OF_WORDS;

        double actual = linguisticSignatureMysteryText1.getFeatures().get(FeatureType.AVERAGE_WORD_LENGTH);
        assertEquals(assertMessage, expected, actual, 0.0);
    }

    @Test
    public void testCalculateAverageWordLengthWhenTextIsEmpty() {
        String assertMessage = "The average word length is not calculated correctly.";
        double expected = 0.0;

        double actual = linguisticSignatureMysteryText3.getFeatures().get(FeatureType.AVERAGE_WORD_LENGTH);
        assertEquals(assertMessage, expected, actual, 0.0);
    }

    @Test
    public void testCalculateTypeTokenRatio() {
        String assertMessage = "The type token ratio is not calculated correctly.";
        double expected = NUMBER_OF_DISTINCT_WORDS / NUMBER_OF_WORDS;

        double actual = linguisticSignatureMysteryText1.getFeatures().get(FeatureType.TYPE_TOKEN_RATIO);
        assertEquals(assertMessage, expected, actual, 0.0);
    }

    @Test
    public void testCalculateTypeTokenRatioWhenTextIsEmpty() {
        String assertMessage = "The type token ratio is not calculated correctly.";
        double expected = 0.0;

        double actual = linguisticSignatureMysteryText3.getFeatures().get(FeatureType.TYPE_TOKEN_RATIO);
        assertEquals(assertMessage, expected, actual, 0.0);
    }

    @Test
    public void testCalculateHapaxLegomenaRatio() {
        String assertMessage = "The hapax legomena ratio is not calculated correctly.";
        double expected = NUMBER_OF_WORDS_INCLUDED_ONCE / NUMBER_OF_WORDS;

        double actual = linguisticSignatureMysteryText1.getFeatures().get(FeatureType.HAPAX_LEGOMENA_RATIO);
        assertEquals(assertMessage, expected, actual, 0.0);
    }

    @Test
    public void testCalculateHapaxLegomenaRatioWhenTextIsEmpty() {
        String assertMessage = "The hapax legomena ratio is not calculated correctly.";
        double expected = 0.0;

        double actual = linguisticSignatureMysteryText3.getFeatures().get(FeatureType.HAPAX_LEGOMENA_RATIO);
        assertEquals(assertMessage, expected, actual, 0.0);
    }

    @Test
    public void testCalculateAverageSentenceLength() {
        String assertMessage = "The average sentence length is not calculated correctly.";
        double expected = NUMBER_OF_WORDS / NUMBER_OF_SENTENCES;

        double actual = linguisticSignatureMysteryText1.getFeatures().get(FeatureType.AVERAGE_SENTENCE_LENGTH);
        assertEquals(assertMessage, expected, actual, 0.0);
    }

    @Test
    public void testCalculateAverageSentenceLengthWhenTextIsEmpty() {
        String assertMessage = "The average sentence length is not calculated correctly.";
        double expected = 0.0;

        double actual = linguisticSignatureMysteryText3.getFeatures().get(FeatureType.AVERAGE_SENTENCE_LENGTH);
        assertEquals(assertMessage, expected, actual, 0.0);
    }

    @Test
    public void testCalculateAverageSentenceComplexity() {
        String assertMessage = "The average sentence complexity is not calculated correctly.";
        double expected = NUMBER_OF_PHRASES / NUMBER_OF_SENTENCES;

        double actual = linguisticSignatureMysteryText1.getFeatures().get(FeatureType.AVERAGE_SENTENCE_COMPLEXITY);
        assertEquals(assertMessage, expected, actual, 0.0);
    }

    @Test
    public void testCalculateAverageSentenceComplexityWhenTextIsEmpty() {
        String assertMessage = "The average sentence complexity is not calculated correctly.";
        double expected = 0.0;

        double actual = linguisticSignatureMysteryText3.getFeatures().get(FeatureType.AVERAGE_SENTENCE_COMPLEXITY);
        assertEquals(assertMessage, expected, actual, 0.0);
    }

    @Test
    public void testFindAuthorText1() throws FileNotFoundException {
        InputStream mysteryText = new FileInputStream(MYSTERY_TEXT1_FILE_PATH);
        String assertMessage = "The author is not found correctly.";
        String expected = "Douglas Adams";
        String actual = authorshipDetector.findAuthor(mysteryText);
        assertEquals(assertMessage, expected, actual);
    }

    @Test
    public void testFindAuthorText2() throws FileNotFoundException {
        InputStream mysteryText = new FileInputStream(MYSTERY_TEXT2_FILE_PATH);
        String assertMessage = "The author is not found correctly.";
        String expected = "Lewis Carroll";
        String actual = authorshipDetector.findAuthor(mysteryText);
        assertEquals(assertMessage, expected, actual);
    }
}
