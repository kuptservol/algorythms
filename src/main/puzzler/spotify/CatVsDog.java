package puzzler.spotify;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Sergey Kuptsov
 * @since 31/08/2016
 */
public class CatVsDog {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int testCaseNum = scanner.nextInt();

        for (int i = 0; i < testCaseNum; i++) {
            int cats = scanner.nextInt();
            int dogs = scanner.nextInt();
            int voters = scanner.nextInt();

            Map<String, Integer> petToHappiness = new TreeMap<>();
            for (int j = 0; j < voters; j++) {
                String petToStay = scanner.next();
                String petToRemove = scanner.next();

                String petsPairKey = petToStay + petToRemove;
                petToHappiness.put(petsPairKey, petToHappiness.get(petsPairKey) == null ? 1 : petToHappiness.get(petsPairKey) + 1);
            }

            Set<String> petsToStay = new HashSet<>();
            Set<String> petsToRemove = new HashSet<>();

            int n = voters;
            int happyVoter = 0;

            Map.Entry<String, Integer> stringIntegerEntry;
            for (Iterator<Map.Entry<String, Integer>> iterator = petToHappiness.entrySet().iterator(); iterator.hasNext() && n > 0; ) {
                stringIntegerEntry = iterator.next();
                String petToStay = stringIntegerEntry.getKey().substring(0, 1);
                String petToRemove = stringIntegerEntry.getKey().substring(2, 3);

                int count = stringIntegerEntry.getValue();

                if (!petsToRemove.contains(petToStay)) {
                    petsToRemove.add(petToRemove);
                    n -= count;
                    happyVoter += count;
                }
            }


            System.out.println(happyVoter);
        }
    }
}
