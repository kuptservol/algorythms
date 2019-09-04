# @author Sergey Kuptsov

"""
You are given a string, s, and a list of words, words,
that are all of the same length.
Find all starting indices of substring(s) in s that is a concatenation
of each word in words exactly once and without any intervening characters.

Example 1:

Input:
  s = "barfoothefoobarman",
  words = ["foo","bar"]
Output: [0,9]
Explanation: Substrings starting at index 0 and 9 are "barfoor" and "foobar" respectively.
The output order does not matter, returning [9,0] is fine too.
Example 2:

Input:
  s = "wordgoodgoodgoodbestword",
  words = ["word","good","best","word"]
Output: []

"""
from collections import Counter
from typing import List


class Solution:
    def findSubstring(self, str: str, words: List[str]) -> List[int]:
        res = []

        if str == None or str == "" or len(words) == 0:
            return res

        word_len = len(words[0])
        target_words_count = Counter(words)

        to = len(str) - len(words) * word_len
        for i in range(0, to + 1):
            if self.check_range(str, i, word_len, target_words_count, len(words)):
                res.append(i)

        return res

    def check_range(self, str, from_i, word_len, target_words_count, words_count) -> bool:
        window_words_count = dict()
        for i in range(from_i, from_i + (words_count - 1) * word_len + 1, word_len):
            window_str = str[i:i + word_len]
            if not window_str in target_words_count:
                return False
            else:
                window_words_count[window_str] = window_words_count.get(window_str, 0) + 1

        for key in target_words_count:
            if not key in window_words_count or target_words_count[key] != window_words_count[key]:
                return False

        return True


def test():
    solution = Solution()

    result = solution.findSubstring("lingmindraboofooowingdingbarrwingmonkeypoundcake", ["fooo", "barr", "wing", "ding", "wing"])
    print(result)
    assert result == [13]

    result = solution.findSubstring("barfoothefoobarman", ["foo", "bar"])
    print(result)
    assert result == [0, 9]

    result = solution.findSubstring("wordgoodgoodgoodbestword", ["word", "good", "best", "word"])
    print(result)
    assert result == []

    result = solution.findSubstring("wordgoodgoodgoodbestword", ["word", "good", "best", "good"])
    print(result)
    assert result == [8]


test()
