package puzzler.leetcode.linkedlist;

import java.util.LinkedList;

import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

/**
 * @author Sergey Kuptsov
 *
 * Implement the following operations of a stack using queues.
 *
 * push(x) -- Push element x onto stack.
 * pop() -- Removes the element on top of the stack.
 * top() -- Get the top element.
 * empty() -- Return whether the stack is empty.
 */
public class ImplementStackUsingQueues {

    class MyStack {

        private final LinkedList<Integer> queueOne = new LinkedList<>();
        private final LinkedList<Integer> queueTwo = new LinkedList<>();
        private LinkedList<Integer> currentQueue;
        private LinkedList<Integer> secondQueue;

        /**
         * Initialize your data structure here.
         */
        public MyStack() {
            currentQueue = queueOne;
            secondQueue = queueTwo;
        }

        /**
         * Push element x onto stack.
         */
        public void push(int x) {
            currentQueue.addLast(x);
        }

        /**
         * Removes the element on top of the stack and returns that element.
         */
        public int pop() {
            int el = 0;
            int size = currentQueue.size();
            for (int i = 0; i < size; i++) {
                el = currentQueue.pop();
                if (i != size - 1) {
                    secondQueue.addLast(el);
                }
            }

            LinkedList<Integer> tmp = currentQueue;
            currentQueue = secondQueue;
            secondQueue = tmp;

            return el;
        }

        /**
         * Get the top element.
         */
        public int top() {
            int el = 0;
            int size = currentQueue.size();
            for (int i = 0; i < size; i++) {
                el = currentQueue.pop();
                secondQueue.addLast(el);
            }

            LinkedList<Integer> tmp = currentQueue;
            currentQueue = secondQueue;
            secondQueue = tmp;

            return el;
        }

        /**
         * Returns whether the stack is empty.
         */
        public boolean empty() {
            return currentQueue.isEmpty();
        }
    }

    @Test
    public void test() {
        MyStack stack = new MyStack();

        assertEquals(stack.empty(), true);

        stack.push(1);
        stack.push(2);

        assertEquals(stack.top(), 2);
        assertEquals(stack.pop(), 2);

        assertEquals(stack.top(), 1);
        assertEquals(stack.empty(), false);
        stack.push(3);
        assertEquals(stack.top(), 3);
        assertEquals(stack.pop(), 3);
        assertEquals(stack.empty(), false);
    }
}
