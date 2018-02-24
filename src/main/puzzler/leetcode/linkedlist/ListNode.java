package puzzler.leetcode.linkedlist;

public class ListNode {
    public int val;
    public ListNode next;

    public ListNode(int x) {
        val = x;
    }

    public static ListNode of(int... args) {
        if (args.length == 0) {
            return null;
        }

        ListNode startNode = new ListNode(args[0]);

        ListNode prevNode = startNode;
        for (int i = 1; i < args.length; i++) {
            ListNode next = new ListNode(args[i]);
            prevNode.next = next;
            prevNode = next;
        }

        return startNode;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        ListNode next = this;
        while (next != null) {
            s.append(next.val).append(" -> ");
            next = next.next;
        }
        return s.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return obj.toString().equals(toString());
    }
}
