package puzzler.leetcode.bit.manipulation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.google.common.collect.ImmutableList.of;
import static org.testng.Assert.assertEquals;

/**
 * All DNA is composed of a series of nucleotides abbreviated as A, C, G, and T, for example: "ACGAATTCCG".
 * When studying DNA, it is sometimes useful to identify repeated sequences within the DNA.
 * Write a function to find all the 10-letter-long sequences (substrings) that occur more than once in a DNA molecule.
 */
public class RepeatedDNASequence {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {of("AAAAACCCCC", "CCCCCAAAAA"), "AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT"},
                {of("AAAAAAAAAA"), "AAAAAAAAAAA"},
                {of(), "AAAAAAAAAA"},
        };
    }

    @Test(dataProvider = "testData")
    public void test(List<String> a, String b) {
        assertEquals(findRepeatedDnaSequences(b), a);
    }

    public List<String> findRepeatedDnaSequences(String s) {
        Set<String> repeatedSequences = new HashSet<>();
        if (s.length() < 11) {
            return new ArrayList<>(repeatedSequences);
        }

        Set<String> sequences = new HashSet<>();
        for (int i = 0, j = 10; j <= s.length(); i++, j = i + 10) {
            String sequence = s.substring(i, j);
            if (sequences.contains(sequence)) {
                repeatedSequences.add(sequence);
            } else {
                sequences.add(sequence);
            }
        }

        return new ArrayList<>(repeatedSequences);
    }
}
