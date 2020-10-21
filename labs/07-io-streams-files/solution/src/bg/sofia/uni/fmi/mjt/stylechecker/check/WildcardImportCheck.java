package bg.sofia.uni.fmi.mjt.stylechecker.check;

public class WildcardImportCheck extends CodeCheck {

    public WildcardImportCheck(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public boolean checkForError(String line) {
        line = line.trim();
        if (!line.startsWith("import")) {
            return false;
        }
        return (line.contains(".*"));
    }
}
