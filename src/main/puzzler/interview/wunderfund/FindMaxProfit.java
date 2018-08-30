package puzzler.interview.wunderfund;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FindMaxProfit {

    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(FindMaxProfit.class.getResourceAsStream("/wunderFund_data.csv")));

        int profit = 0;
        String line;
        List<Integer> docPrices = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            String[] split = line.split(",");
            docPrices.add(Integer.valueOf(split[1]));
        }

        for (int i = 0; i < docPrices.size(); i++) {
            Integer currCost = docPrices.get(i);
            if (currCost == -1) {
                continue;
            }

            Integer maxCost = Integer.MAX_VALUE;
            Integer maxJ = null;
            int j = i + 1;
            for (; j < docPrices.size(); j++) {
                Integer priceCand = docPrices.get(j);
                if (priceCand == -1) {
                    continue;
                }
                if (priceCand > currCost && priceCand < maxCost) {
                    maxCost = Math.min(maxCost, priceCand);
                    maxJ = j;
                }
            }

            if (maxCost != Integer.MAX_VALUE) {
                profit += maxCost - currCost;
                docPrices.set(maxJ, -1);
                docPrices.set(i, maxJ);
            }
        }

        System.out.println(docPrices);
        System.out.println(profit);
    }
}
