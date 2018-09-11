package puzzler.interview.wunderfund;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FindMaxProfit {

    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(FindMaxProfit.class.getResourceAsStream("/wunderFund_data.csv")));

        String line;
        List<Integer> docPrices = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            String[] split = line.split(",");
            docPrices.add(Integer.valueOf(split[1]));
        }

        System.out.println(maxProfit(docPrices.size(), docPrices));
    }

    public static int maxProfit(int k, List<Integer> prices) {
        int[][] transDays = new int[k + 1][prices.size()];

        for (int trans = 0; trans <= k; trans++) {
            for (int day = 0; day < prices.size(); day++) {

                // we do not have transactions - no profit possible
                if (trans == 0) {
                    transDays[trans][day] = 0;
                    continue;
                }

                // it's first day - can only by - not sell - no profit
                if (day == 0) {
                    transDays[trans][day] = 0;
                    continue;
                }

                //do nothing
                int maxProfit = transDays[trans][day - 1];

                // max cell diff + max on previous transaction on day before cell
                for (int m = 0; m <= day - 1; m++) {
                    maxProfit = Math.max(maxProfit, prices.get(day) - prices.get(m) + transDays[trans - 1][m]);
                }

                transDays[trans][day] = maxProfit;
            }
        }

        return transDays[k][prices.size() - 1];
    }
}
