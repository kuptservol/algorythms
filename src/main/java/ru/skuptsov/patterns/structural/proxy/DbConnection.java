package ru.skuptsov.patterns.structural.proxy;

/**
 * @author Sergey Kuptsov
 * @since 24/04/2016
 */
public interface DbConnection {

    Object select(String sql);

}
