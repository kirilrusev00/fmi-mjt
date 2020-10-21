public class FractionSimplifier {

    public static String simplify(String fraction) {
        String[] numbers = fraction.split("/");
        int numerator = Integer.parseInt(numbers[0]);
        int denominator = Integer.parseInt(numbers[1]);
        int gcd = getGCD(numerator, denominator);
        numerator /= gcd;
        denominator /= gcd;
        if (denominator != 1 && numerator != 0) {
            return numerator + "/" + denominator;
        }
        else {
            return Integer.toString(numerator);
        }
    }

    public static int getGCD (int num1, int num2) {
        int result = 1;
        if (num1 >= num2) {
            for (int i = 2; i <= num2; i++) {
                if (num1 % i == 0 && num2 % i == 0) {
                    result = i;
                }
            }
        }
        else {
            for (int i = 2; i <= num1; i++) {
                if (num1 % i == 0 && num2 % i == 0) {
                    result = i;
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(simplify("0/4"));
    }
}
