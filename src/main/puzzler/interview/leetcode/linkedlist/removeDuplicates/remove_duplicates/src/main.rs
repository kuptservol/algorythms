fn main() {

}

fn deleteDuplicates(head: ListNode) -> ListNode {
    let mut prev = head;
   // let mut next = prev.next;

   // while next !=null {
    //    if prev.val == next.val {
     //       prev.next = next.next;
      //  }
   // }

    prev
}

struct ListNode {
    val: u32,
    next: &mut ListNode
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
