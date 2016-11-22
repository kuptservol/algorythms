package ru.skuptsov.puzzlers.job.interview.programcreek.tree.traversal;

import lombok.Builder;
import lombok.Data;

/**
 * @author Sergey Kuptsov
 * @since 22/11/2016
 */
@Data
@Builder
public class Tree<T> {
    Node<T> root;
}
