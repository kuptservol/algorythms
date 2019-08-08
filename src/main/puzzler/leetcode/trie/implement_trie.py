# @author Sergey Kuptsov

"""
Implement a trie with insert, search, and startsWith methods.
"""

class TrieNode:
    def __init__(self):
        self.letters = {}
        self.word = False

    def set_letter(self, letter):
        next = TrieNode()
        self.letters[letter] = next
        return next

class Trie:

    def __init__(self):
        self.root = TrieNode()

    def insert(self, word: str) -> None:
        next = self.root
        for letter in word:
            if letter in next.letters:
                next = next.letters[letter]
            else:
                next = next.set_letter(letter)

        next.word = True

    def search(self, word: str) -> bool:
        next = self.root
        for letter in word:
            if not letter in next.letters:
                return False
            else:
                next = next.letters[letter]

        return next.word

    def startsWith(self, prefix: str) -> bool:
        next = self.root
        for letter in prefix:
            if not letter in next.letters:
                return False
            else:
                next = next.letters[letter]

        return True


def test():
    trie = Trie()

    trie.insert("apple")

    assert trie.search("apple") is True  # returns true
    assert trie.search("app") is False  # returns false
    assert trie.startsWith("app") is True  # returns true
    trie.insert("app")
    assert trie.search("app") is True
    assert trie.search("ap") is False


test()
