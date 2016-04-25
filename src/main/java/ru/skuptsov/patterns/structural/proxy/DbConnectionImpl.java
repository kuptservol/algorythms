package ru.skuptsov.patterns.structural.proxy;

import org.springframework.stereotype.Component;
import ru.skuptsov.patterns.structural.proxy.aspectJ.SpringAspectJProxy.Transactional;

/**
 * @author Sergey Kuptsov
 * @since 24/04/2016
 */
public class DbConnectionImpl implements DbConnection {

    @Override
    @Transactional
    public Object select(String sql) {
        System.out.println("Running query " + sql);
        return sql;
    }
}
