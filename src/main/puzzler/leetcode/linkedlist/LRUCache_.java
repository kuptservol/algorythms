package puzzler.leetcode.linkedlist;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 *         Design and implement a data structure for Least Recently Used (LRU) cache.
 *         It should support the following operations: get and put.
 */
public class LRUCache_ {

    private class Node {
        int key;
        int value;
        Node prev;
        Node next;
    }

    /**
     * double-linked list to support addHead and removal for O(1)
     * map for indexing linked-list elements by key for O(1)
     */
    private class LRUCache {

        private final int capacity;
        private int size = 0;
        private final Map<Integer, Node> map;
        private Node head;
        private Node tail;

        public LRUCache(int capacity) {
            this.capacity = capacity;
            this.map = new HashMap<>();
        }

        public int get(int key) {
            if (!map.containsKey(key)) {
                return -1;
            }

            Node node = map.get(key);
            remove(node);
            addFirst(node.key, node.value);

            return node.value;
        }

        private Node addFirst(int key, int value) {
            Node newHead = new Node();
            newHead.key = key;
            newHead.value = value;

            if (head != null) {
                head.prev = newHead;
                newHead.next = head;
            } else {
                tail = newHead;
            }

            head = newHead;

            return newHead;
        }

        private void remove(Node node) {
            if (node.next != null) {
                if (node.prev == null) {
                    node.next.prev = null;
                    head = node.next;
                } else {
                    node.next.prev = node.prev;
                }
            }

            if (node.prev != null) {
                if (node.next == null) {
                    tail = node.prev;
                    node.prev.next = null;
                } else {
                    node.prev.next = node.next;
                }
            }
        }

        public void put(int key, int value) {
            if (map.containsKey(key)) {
                Node node = map.get(key);
                remove(node);
            } else {
                if (size == capacity) {
                    map.remove(tail.key);
                    tail = tail.prev;
                    tail.next = null;
                } else {
                    size++;
                }
            }

            Node newNode = addFirst(key, value);
            map.put(key, newNode);
        }
    }

    @Test
    public void test() {
        LRUCache LRUCache = new LRUCache(2 /* capacity */);

        LRUCache.put(1, 1);
        LRUCache.put(2, 2);
        assertEquals(LRUCache.get(1), 1);       // returns 1
        LRUCache.put(3, 3);    // evicts key 2
        assertEquals(LRUCache.get(2), -1);       // returns -1 (not found)
        LRUCache.put(4, 4);    // evicts key 1
        assertEquals(LRUCache.get(1), -1);       // returns -1 (not found)
        assertEquals(LRUCache.get(3), 3);       // returns 3
        assertEquals(LRUCache.get(4), 4);       // returns 4
        LRUCache.put(4, 5);
        assertEquals(LRUCache.get(4), 5);
    }
}
