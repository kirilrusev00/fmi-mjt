public class StockExchange {

    public static int maxProfit(int[] prices){
        int len = prices.length;
        int profit = 0;
        for(int i = 0; i < len; i++) {
            for(int j = i + 1; j < len; j++) {
                int firstProfit = prices[j] - prices[i];
                if (firstProfit > profit) {
                    profit = firstProfit;
                }
                for(int k = j + 1; k < len; k++) {
                    for(int l = k + 1; l < len; l++) {
                        int secondProfit = prices[l] - prices[k];
                        int sumProfits = firstProfit + secondProfit;
                        if (sumProfits > profit) {
                            profit = sumProfits;
                        }
                    }
                }
            }
        }
        return profit;
    }

    public static void main(String[] args) {
        int[] prices = {7, 6, 4, 3, 1};
        System.out.println(maxProfit(prices));
    }
}
