package ru.skuptsov.puzzlers.job.interview.leetcode.dynamic.programming;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.google.common.collect.ImmutableList.of;
import static org.testng.Assert.assertEqualsNoOrder;

/**
 * @author Sergey Kuptsov
 * @since 21/03/2017
 * Given a string s and a dictionary of words dict, add spaces in s to construct a sentence where each word is a valid dictionary word.
 * Return all such possible sentences. For example, given s = "catsanddog", dict = ["cat", "cats", "and", "sand", "dog"],
 * the solution is ["cats and dog", "cat sand dog"].
 */
public class WordBreakII {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {"catsanddog", of("cat", "cats", "and", "sand", "dog"), of("cats and dog", "cat sand dog")},
                {"ab", of("a", "b"), of("a b")},
                {"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaabaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", of("a", "aa", "aaa", "aaaa", "aaaaa", "aaaaaa", "aaaaaaa", "aaaaaaaa", "aaaaaaaaa", "aaaaaaaaaa"), of()}
        };
    }

    @Test(dataProvider = "testData")
    public void test(String s, List<String> wordDict, List<String> result) {
        assertEqualsNoOrder(wordBreak(s, wordDict).toArray(), result.toArray());
    }

    public List<String> wordBreak(String s, List<String> wordDict) {
        Map<Integer, List<String>> buckets = new HashMap<>();

        List<List<Integer>> line = new ArrayList<>(s.length());
        for (int i = 0; i < s.length(); i++) {
            line.add(i, new ArrayList<>());
        }

        int bucketCounter = 0;

        for (int i = 0; i < wordDict.size(); i++) {
            String nextDictWord = wordDict.get(i);
            if (nextDictWord.length() > s.length())
                continue;

            if (nextDictWord.equals(s.substring(0, nextDictWord.length()))) {
                bucketCounter++;

                buckets.put(bucketCounter, new ArrayList<>());
                buckets.get(bucketCounter).add(nextDictWord);

                int dictWordBucketEnd = nextDictWord.length() - 1;

                List<Integer> bucketsList = line.get(dictWordBucketEnd);

                bucketsList.add(bucketCounter);

            }
        }

        for (int i = 0; i < s.length(); i++) {
            if (line.get(i).isEmpty())
                continue;

            for (String nextDictWord : wordDict) {
                if (i + 1 + nextDictWord.length() > s.length())
                    continue;

                if (nextDictWord.equals(s.substring(i + 1, i + 1 + nextDictWord.length()))) {

                    for (Integer existingBucketId : line.get(i)) {

                        bucketCounter++;

                        buckets.put(bucketCounter, new ArrayList<>(buckets.get(existingBucketId)));
                        buckets.get(bucketCounter).add(nextDictWord);

                        int dictWordBucketEnd = i + nextDictWord.length();

                        List<Integer> bucketsList = line.get(dictWordBucketEnd);

                        List<String> candidateBucket = buckets.get(bucketCounter);

                        boolean doNotAddExistingBucket = bucketsList
                                .stream()
                                .filter(bucket -> buckets.get(bucket).equals(candidateBucket))
                                .findFirst()
                                .map((q) -> true)
                                .orElse(false);

                        if (!doNotAddExistingBucket) {
                            bucketsList.add(bucketCounter);
                        }
                    }
                }

            }
        }

        return line
                .get(line.size() - 1)
                .stream()
                .map(k -> buckets.get(k).stream().collect(Collectors.joining(" ")))
                .collect(Collectors.toList());
    }
}
