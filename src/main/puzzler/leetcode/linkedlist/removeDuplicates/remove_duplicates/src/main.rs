fn main() {

}

fn deleteDuplicates(head: &mut ListNode) {
    let &mut prev = head;
}

struct ListNode<'a> {
    val: u32,
    next: &'a mut ListNode<'a>,
}
/*

    public static class ListNode {
        int val;
        ListNode next;

    public ListNode deleteDuplicates(ListNode head) {
        ListNode prev = head;
        ListNode next = head.next;
        while (next != null) {
            if (prev.val == next.val) {
                prev.next = next.next;
            } else {
                prev = next;
            }

            next = next.next;
        }

        return head;
    }
*/
