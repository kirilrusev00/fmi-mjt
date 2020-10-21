package bg.sofia.uni.fmi.mjt.stylechecker;

import bg.sofia.uni.fmi.mjt.stylechecker.check.*;

import java.io.*;
import java.util.*;

/**
 * Checks adherence to Java style guidelines.
 */
public class StyleChecker {

    private static final String PACKAGE_LINE_ERROR =
            "// FIXME Package name should not contain upper-case letters or underscores";
    private static final String LINE_LENGTH_ERROR = "// FIXME Length of line should not exceed 100 characters";
    private static final String WILDCARD_IMPORT_ERROR = "// FIXME Wildcards are not allowed in import statements";
    private static final String OPENING_BRACKET_ERROR =
            "// FIXME Opening brackets should be placed on the same line as the declaration";
    private static final String SINGLE_STATEMENT_ERROR = "// FIXME Only one statement per line is allowed";
    private static final int LINE_LENGTH_LIMIT = 100;

    private Set<CodeCheck> checks;

    public StyleChecker() {
        initChecks();
    }

    private void initChecks() {
        checks = new HashSet<>();
        checks.add(new BracketsCheck(OPENING_BRACKET_ERROR));
        checks.add(new LineLengthCheck(LINE_LENGTH_ERROR, LINE_LENGTH_LIMIT));
        checks.add(new PackageNameCheck(PACKAGE_LINE_ERROR));
        checks.add(new SingleStatementCheck(SINGLE_STATEMENT_ERROR));
        checks.add(new WildcardImportCheck(WILDCARD_IMPORT_ERROR));
    }

    private Set<String> getErrors(String line) {
        Set<String> errors = new HashSet<>();
        for (CodeCheck codeCheck : checks) {
            if (codeCheck.checkForError(line)) {
                errors.add(codeCheck.getErrorMessage());
            }
        }
        return errors;
    }

    /**
     * For each line from the given {@code source} performs code style checks
     * and writes to the {@code output}
     * 1. a FIX_ME comment line for each style violation in the input line, if any
     * 2. the input line itself.
     *
     * @param source
     * @param output
     */
    public void checkStyle(Reader source, Writer output) {
        ArrayList<String> sourceText = readFromFile(source);
        writeToFile(output, sourceText);
    }

    private ArrayList<String> readFromFile(Reader source) {
        ArrayList<String> lines = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(source)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error occurred while reading from source file", e);
        }
        return lines;
    }

    private void writeToFile(Writer output, ArrayList<String> text) {
        try (var fw = new BufferedWriter(output)) {
            Set<String> lineErrors;
            for (String line : text) {
                lineErrors = getErrors(line);
                for (String error : lineErrors) {
                    String whitespaces = line.substring(0, line.indexOf(line.trim()));
                    fw.write(whitespaces + error);
                    fw.newLine();
                }
                fw.write(line);
                fw.newLine();
            }
            fw.flush();
        } catch (IOException e) {
            throw new RuntimeException("Error occurred while writing to output file", e);
        }
    }
}
