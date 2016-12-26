package ru.skuptsov.puzzlers.job.interview.leetcode.linkedlist;

import org.testng.annotations.Test;

import java.util.Arrays;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

/**
 * @author Sergey Kuptsov
 * @since 19/11/2016
 */
public class ArrayStack<T> {

    private static final int INITIAL_SIZE = 10;
    private Object[] array;
    private int currPos = -1;

    public ArrayStack() {
        array = new Object[INITIAL_SIZE];
    }

    public void push(T e) {
        if (currPos + 1 == array.length) {
            array = Arrays.copyOf(array, 2 * array.length);
        }

        array[++currPos] = e;
    }

    @SuppressWarnings("unchecked")
    public T pop() {
        if (currPos == -1) {
            return null;
        }

        T e = (T) array[currPos];
        array[currPos--] = null;

        return e;
    }

    @SuppressWarnings("unchecked")
    public T peek() {
        if (currPos == -1) {
            return null;
        }

        return (T) array[currPos];
    }

    public int getSize() {
        return currPos + 1;
    }

    @Test
    public void test() {
        ArrayStack<Integer> stack = new ArrayStack<>();
        assertNull(stack.pop());
        assertNull(stack.peek());
        assertEquals(stack.getSize(), 0);

        stack.push(1);
        assertEquals(stack.getSize(), 1);
        assertEquals(stack.peek(), new Integer(1));
        assertEquals(stack.pop(), new Integer(1));
        assertNull(stack.pop());
        assertNull(stack.peek());

        assertEquals(stack.getSize(), 0);

        stack.push(1);
        stack.push(2);

        assertEquals(stack.pop(), new Integer(2));
        assertEquals(stack.pop(), new Integer(1));
    }
}
