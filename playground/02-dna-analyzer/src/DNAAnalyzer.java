public class DNAAnalyzer {

    public static String longestRepeatingSequence(String dna) {
        int len = dna.length();
        int beginIndex = 0, size = 0;
        for (int i = 0; i < len; i++) {
            for (int j = len - i; j > 0; j--){
                String str = dna.substring(i, i + j);
                for (int k = i + 1; k <= len - j; k++) {
                    String str2 = dna.substring(k, k + j);
                    if (str.equals(str2) && j > size) {
                        beginIndex = i;
                        size = j;
                    }
                }
            }
        }
        return dna.substring(beginIndex, beginIndex + size);
    }

    public static void main(String[] args) {
        System.out.println(longestRepeatingSequence("abracadabra"));
        System.out.println(longestRepeatingSequence("ATACTCGGTACTCT"));
        System.out.println(longestRepeatingSequence("bbvvbmbnvvbb"));
    }
}
