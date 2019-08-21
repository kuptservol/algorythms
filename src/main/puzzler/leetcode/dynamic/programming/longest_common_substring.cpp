#include <string>

#include "test_runner/test_runner.h"

using namespace std;

/**
 * The longest common substring problem is to find the longest string that is a substring of two or more strings.
 */
class Solution {
public:
    int longestCommonSubstring(const string &text1, const string &text2) {
        if (text1.empty() || text2.empty()) {
            return 0;
        }

        int max = 0;
        int commons[text1.size()][text2.size()];
        for (int i = 0; i < text1.size(); i++) {
            for (int j = 0; j < text2.size(); j++) {
                if (text1[i] == text2[j]) {
                    if (i == 0 || j == 0) {
                        commons[i][j] = 1;
                    } else {
                        commons[i][j] = commons[i - 1][j - 1] + 1;
                    }
                } else {
                    commons[i][j] = 0;
                }

                if (commons[i][j] > max) {
                    max = commons[i][j];
                }
            }
        }

        return max;
    }
};

void Tests() {
    Solution solution;
    AssertEqual(solution.longestCommonSubstring("ABABC", "BABCA"), 4, "1");
    AssertEqual(solution.longestCommonSubstring("CCC", "CCC"), 3, "2");
    AssertEqual(solution.longestCommonSubstring("C", "C"), 1, "2");
    AssertEqual(solution.longestCommonSubstring("", ""), 0, "3");
}

int main() {
    TestRunner tr;
    tr.RunTest(Tests, "Tests");

    return 0;
}
