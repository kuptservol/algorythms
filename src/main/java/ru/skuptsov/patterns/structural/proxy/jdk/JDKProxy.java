package ru.skuptsov.patterns.structural.proxy.jdk;

import ru.skuptsov.patterns.structural.proxy.DbConnection;
import ru.skuptsov.patterns.structural.proxy.DbConnectionImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


/**
 * @author Sergey Kuptsov
 * @since 24/04/2016
 */
public class JDKProxy {

    public static void main(String[] args) {
        DbConnection dbConnection = (DbConnection) Proxy.newProxyInstance(
                DbConnection.class.getClassLoader(),
                new Class[]{DbConnection.class},
                new TransactionProxy(new DbConnectionImpl()));

        dbConnection.select("Select * from users");
    }

    private static class TransactionProxy implements InvocationHandler {

        private final DbConnection dbConnection;

        private TransactionProxy(DbConnection dbConnection) {
            this.dbConnection = dbConnection;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("Starting new transaction");
            try {
                return method.invoke(dbConnection, args);
            } finally {
                System.out.println("Commiting transaction");
            }
        }
    }


}
