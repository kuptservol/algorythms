package ru.skuptsov.patterns.structural.proxy.aspectJ;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import ru.skuptsov.patterns.structural.proxy.DbConnection;
import ru.skuptsov.patterns.structural.proxy.DbConnectionImpl;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Sergey Kuptsov
 * @since 24/04/2016
 */
public class SpringAspectJProxy {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext springConfig = new AnnotationConfigApplicationContext(AspectConfiguration.class);
        DbConnection dbConnection = springConfig.getBean(DbConnection.class);
        dbConnection.select("Select * from users");
    }

    @Retention(RUNTIME)
    public @interface Transactional {
    }

    @Configuration
    @EnableAspectJAutoProxy(proxyTargetClass = true)
    public static class AspectConfiguration {

        @Bean
        public DbConnection dbConnection(){
            return new DbConnectionImpl();
        }

        @Bean
        public TransactionAspect transactionAspect(){
            return new TransactionAspect();
        }
    }

    @Aspect
    @Component
    public static class TransactionAspect {

        @Around(value = "execution(* *(..)) && @annotation(transactional)", argNames = "joinPoint,transactional")
        public Object doInTransaction(final ProceedingJoinPoint joinPoint, Transactional transactional) throws Throwable {

            System.out.println("Starting new transaction");
            try {
                return joinPoint.proceed();
            } finally {
                System.out.println("Commiting transaction");
            }
        }
    }
}
