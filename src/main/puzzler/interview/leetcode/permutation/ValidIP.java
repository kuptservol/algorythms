package puzzler.interview.leetcode.permutation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.google.common.collect.ImmutableList.of;
import static org.testng.Assert.assertEqualsNoOrder;

/**
 * @author Sergey Kuptsov
 * @since 11/10/2016
 * Given a string containing only digits, restore it
 * by returning all possible valid IP address combinations.
 * <p>
 * For example: given "25525511135",return ["255.255.11.135", "255.255.111.35"].
 */
public class ValidIP {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {"25525511135", of("255.255.11.135", "255.255.111.35")},
                {"010010", of("0.10.0.10", "0.100.1.0")}
        };
    }

    @Test(dataProvider = "testData")
    public void test(String a, List<String> b) {
        assertEqualsNoOrder(restoreIpAddresses(a).toArray(), b.toArray());
    }

    public List<String> restoreIpAddresses(String s) {
        Set<String> validIps = new HashSet<>();

        if (s.length() < 4 || s.length() > 12) {
            return new ArrayList<>(validIps);
        }

        if (s.chars()
                .filter(v -> !Character.isDigit(v))
                .findFirst()
                .isPresent())
        {
            return new ArrayList<>(validIps);
        }

        makeDFSPermutations(s, validIps, 0, "");

        return new ArrayList<>(validIps);
    }

    private void makeDFSPermutations(String s, Set<String> validIps, int section, String currIpCandidate) {
        section++;

        if (section == 5) {
            if (s.length() == 0) {
                validIps.add(currIpCandidate.substring(0, currIpCandidate.length() - 1));
            } else
                return;
        }

        if (s.length() > 0) {
            makeDFSPermutations(s.substring(1, s.length()), validIps, section, currIpCandidate + s.substring(0, 1) + "");
        }

        if (s.length() > 1) {
            if (!s.substring(0, 1).equals("0")) {
                makeDFSPermutations(s.substring(2, s.length()), validIps, section, currIpCandidate + s.substring(0, 2) + "");
            }
        }

        if (s.length() > 2) {
            Integer next = Integer.valueOf(s.substring(0, 3));
            if (next <= 255 && !s.substring(0, 1).equals("0")) {
                makeDFSPermutations(s.substring(3, s.length()), validIps, section, currIpCandidate + s.substring(0, 3) + "");
            }
        }
    }
}
