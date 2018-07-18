package puzzler.leetcode.map;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.google.common.collect.ImmutableSet.of;
import static org.testng.Assert.assertEquals;
import static puzzler.leetcode.PuzzlerUtils.strA;

/**
 * @author Sergey Kuptsov
 *         Given an array of strings, group anagrams together.
 *         <p>
 *         Example:
 *         <p>
 *         Input: ["eat", "tea", "tan", "ate", "nat", "bat"],
 *         Output:
 *         [
 *         ["ate","eat","tea"],
 *         ["nat","tan"],
 *         ["bat"]
 *         ]
 *         Note:
 *         <p>
 *         All inputs will be in lowercase.
 *         The order of your output does not matter.
 */
public class GroupAnagrams {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {strA(of("eat", "tea", "tan", "ate", "nat", "bat")), of(of("ate", "eat", "tea"), of("nat", "tan"), of("bat"))}
        };
    }

    @Test(dataProvider = "testData")
    public void test(String[] strs, Set<Set<String>> res) {
        assertEquals(new HashSet<>(groupAnagrams(strs)), new HashSet<>(res));
    }

    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> anagramsMap = new HashMap<>();

        for (String str : strs) {
            char[] chars = str.toCharArray();
            Arrays.sort(chars);
            String sorted = new String(chars);
            anagramsMap.compute(sorted, (k, v) -> {
                if (v == null) v = new ArrayList<>();
                v.add(str);
                return v;
            });
        }

        return new ArrayList<>(anagramsMap.values());
    }
}
