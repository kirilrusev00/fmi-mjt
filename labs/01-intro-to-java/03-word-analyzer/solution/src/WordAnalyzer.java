public class WordAnalyzer {

    public static String getSharedLetters(String word1, String word2) {
        String lower1 = word1.toLowerCase();
        String lower2 = word2.toLowerCase();
        String sharedLetters = new String();
        for (char symbol = 'a'; symbol <= 'z'; symbol++) {
            if (lower1.contains(Character.toString(symbol)) == true && lower2.contains(Character.toString(symbol)) == true) {
                sharedLetters += symbol;
            }
        }
        return sharedLetters;
    }

    public static void main(String[] args) {
        System.out.println(getSharedLetters("house", "home"));
    }
}
