fn main() {
    println!("Hello, world! {}", x(5));
}

fn deleteDuplicates(head: &[ListNode]) {
    let mut prev = head;
    let mut next = head.next;
}

fn x(x: i32) -> i32 {
    x
}

struct ListNode {
    val: u32,
    next: Box<ListNode>
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
