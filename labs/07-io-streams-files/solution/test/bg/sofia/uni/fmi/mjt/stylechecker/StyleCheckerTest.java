package bg.sofia.uni.fmi.mjt.stylechecker;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertEquals;

public class StyleCheckerTest {
    private static final String PACKAGE_LINE_ERROR =
            "// FIXME Package name should not contain upper-case letters or underscores";
    private static final String LINE_LENGTH_ERROR = "// FIXME Length of line should not exceed 100 characters";
    private static final String WILDCARD_IMPORT_ERROR = "// FIXME Wildcards are not allowed in import statements";
    private static final String OPENING_BRACKET_ERROR =
            "// FIXME Opening brackets should be placed on the same line as the declaration";
    private static final String SINGLE_STATEMENT_ERROR = "// FIXME Only one statement per line is allowed";

    private static StyleChecker checker;
    private String testString;
    @BeforeClass
    public static void beforeClass() {
        checker = new StyleChecker();
    }

    @Test
    public void testPackage() {
        testString = "package bg.sofia.uni_fmi.mjt.Stylechecker;";
        Reader input = new StringReader(testString);
        Writer output = new StringWriter();

        checker.checkStyle(input, output);
        String actual = output.toString();

        System.out.println(actual);

        assertEquals(PACKAGE_LINE_ERROR
                + System.lineSeparator() + testString, actual.strip());
    }

    @Test
    public void testLengthOfLine() {
        testString = "private static final String OPENING_BRACKET_ERROR = \"// FIXME Opening"
                + "brackets should be placed on the same line as the declaration\";";
        Reader input = new StringReader(testString);
        Writer output = new StringWriter();

        checker.checkStyle(input, output);
        String actual = output.toString();

        System.out.println(actual);

        assertEquals(LINE_LENGTH_ERROR + System.lineSeparator() + testString, actual.strip());
    }

    @Test
    public void testLengthOfLineInImport() {
        testString = "import private static final String OPENING_BRACKET_ERROR = \"// FIXME Opening" +
                "brackets should be placed on the same line as the declaration\";";
        Reader input = new StringReader(testString);
        Writer output = new StringWriter();

        checker.checkStyle(input, output);
        String actual = output.toString();

        System.out.println(actual);

        assertEquals(testString, actual.strip());
    }

    @Test
    public void testImport() {
        testString = "import java.util.*;";
        Reader input = new StringReader(testString);
        Writer output = new StringWriter();

        checker.checkStyle(input, output);
        String actual = output.toString();

        System.out.println(actual);

        assertEquals(WILDCARD_IMPORT_ERROR
                + System.lineSeparator() + testString, actual.strip());
    }

    @Test
    public void testSingleStatement() {
        testString = "int x=0;int v=5;";
        Reader input = new StringReader(testString);
        Writer output = new StringWriter();

        checker.checkStyle(input, output);
        String actual = output.toString();

        System.out.println(actual);

        assertEquals(SINGLE_STATEMENT_ERROR
                + System.lineSeparator() + testString, actual.strip());
    }

    @Test
    public void testBrackets() {
        testString = "for (int i = 0, i < n, i++)" + System.lineSeparator() + "{" + System.lineSeparator() + "m;"
                + System.lineSeparator() + "m;" + System.lineSeparator() + "}";
        Reader input = new StringReader(testString);
        Writer output = new StringWriter();

        checker.checkStyle(input, output);
        String actual = output.toString();

        System.out.println(actual);
        assertEquals("for (int i = 0, i < n, i++)" + System.lineSeparator() + OPENING_BRACKET_ERROR
                + System.lineSeparator() + "{" + System.lineSeparator() + "m;"
                + System.lineSeparator() + "m;" + System.lineSeparator() + "}", actual.strip());
    }
}
