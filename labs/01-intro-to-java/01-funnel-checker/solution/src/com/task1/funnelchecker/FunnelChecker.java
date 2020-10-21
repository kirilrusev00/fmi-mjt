public class FunnelChecker {

    public static boolean isFunnel(String str1, String str2) {
        int len = str1.length();
        for (int i = 0; i < str1.length(); i++) {
            String checkString = str1.substring(0, i) + str1.substring(i + 1, len);
            if (str2.equals(checkString) == true) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(isFunnel("skiff", "ski"));
    }
}

