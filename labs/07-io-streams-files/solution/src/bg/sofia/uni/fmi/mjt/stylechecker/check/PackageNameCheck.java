package bg.sofia.uni.fmi.mjt.stylechecker.check;

public class PackageNameCheck extends CodeCheck {

    public PackageNameCheck(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public boolean checkForError(String line) {
        line = line.trim();
        if (!line.startsWith("package")) {
            return false;
        }
        return (line.contains("_") || !line.equals(line.toLowerCase()));
    }
}
