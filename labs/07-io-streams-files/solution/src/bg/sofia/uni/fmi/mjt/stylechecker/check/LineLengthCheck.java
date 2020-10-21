package bg.sofia.uni.fmi.mjt.stylechecker.check;

public class LineLengthCheck extends CodeCheck {

    private int lengthLimit;

    public LineLengthCheck(String errorMessage, int lengthLimit) {
        super(errorMessage);
        this.lengthLimit = lengthLimit;
    }

    @Override
    public boolean checkForError(String line) {
        line = line.trim();
        if (line.startsWith("import")) {
            return false;
        }
        return (line.length() > lengthLimit);
    }

}
