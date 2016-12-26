package ru.skuptsov.puzzlers.job.interview.leetcode.tree.traversal;

import com.google.common.collect.ImmutableList;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Stack;

import static java.util.Optional.ofNullable;
import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 * @since 22/11/2016
 * Preorder binary tree traversal is a classic interview problem about trees.
 * The key to solve this problem is to understand the following:
 * What is preorder? (parent node is processed before its children)
 */
public class PreOrderTraversal {

    public static List<Object> traverse(Tree<Object> tree) {
        ImmutableList.Builder<Object> traverse = ImmutableList.builder();
        Stack<Node<Object>> objects = new Stack<>();
        Node<Object> root = tree.root;
        objects.push(root);

        while (!objects.isEmpty()) {
            Node<Object> nextNode = objects.pop();
            traverse.add(nextNode.value);
            ofNullable(nextNode.getRight())
                    .map(objects::push);
            ofNullable(nextNode.getLeft())
                    .map(objects::push);
        }

        return traverse.build();
    }

    @Test
    public void test() {
        Tree<Object> tree = Tree.builder()
                .root(Node.builder()
                        .value(1)
                        .left(Node.builder()
                                .value(2)
                                .left(Node.builder()
                                        .value(3)
                                        .build())
                                .right(Node.builder()
                                        .value(4)
                                        .build())
                                .build())
                        .right(Node.builder()
                                .value(5)
                                .left(Node.builder()
                                        .value(6)
                                        .build())
                                .right(Node.builder()
                                        .value(7)
                                        .build())
                                .build())
                        .build())
                .build();

        assertEquals(traverse(tree), ImmutableList.of(1, 2, 3, 4, 5, 6, 7));
    }
}
