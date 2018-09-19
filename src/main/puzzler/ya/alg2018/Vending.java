package puzzler.ya.alg2018;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * https://contest.yandex.ru/algorithm2018/contest/8254/problems/
 */
public class Vending {

    public static final int MILLION = 1000000;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String[] first = scanner.nextLine().split(" ");
        String[] second = scanner.nextLine().split(" ");

        int uBanknote = Integer.valueOf(first[0]);
        int uCoin = Integer.valueOf(first[1]);

        int price = Integer.valueOf(second[0]);
        int vCoin = Integer.valueOf(second[1]);

        int[] maxBottles = new int[1];
        maxBottles[0] = 0;
        Set<Integer> uCoins = new HashSet<>();
        findMaxBottlesDfs(uBanknote, uCoin, price, vCoin, 0, maxBottles, uCoins);
        System.out.println(maxBottles[0]);
    }

    private static void findMaxBottlesDfs(int uBanknote, int uCoin, int price, int vCoin, int currBottles, int[] maxBottles, Set<Integer> uCoins) {
        if (uCoins.contains(uCoin)) {
            //got cycle
            return;
        } else {
            uCoins.add(uCoin);
        }

        maxBottles[0] = Math.max(maxBottles[0], currBottles);

        if (uBanknote * MILLION + uCoin < price) return;

        if (uCoin >= price) {
            //pay with coins
            findMaxBottlesDfs(uBanknote, uCoin - price, price, vCoin + price, currBottles + 1, maxBottles, uCoins);
        } else {
            int banknoteToPay = price / MILLION;
            int coinToPay = price % MILLION;

            if (uCoin < coinToPay) {
                banknoteToPay += 1;
                if (uBanknote < banknoteToPay) {
                    return;
                } else {
                    int cashBack = banknoteToPay * MILLION - price;
                    if (vCoin < cashBack) {
                        return;
                    } else {
                        findMaxBottlesDfs(uBanknote - banknoteToPay, uCoin + cashBack, price, vCoin - cashBack, currBottles + 1, maxBottles, uCoins);
                    }
                }
            } else {
                int cashBack = banknoteToPay * MILLION + coinToPay - price;
                if (vCoin < cashBack) {
                    return;
                } else {
                    findMaxBottlesDfs(uBanknote - banknoteToPay, uCoin - coinToPay + cashBack, price, vCoin + coinToPay - cashBack, currBottles + 1, maxBottles, uCoins);
                }
            }
        }
    }
}
