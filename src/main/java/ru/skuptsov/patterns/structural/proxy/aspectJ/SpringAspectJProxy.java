package ru.skuptsov.patterns.structural.proxy.aspectJ;

import org.aspectj.lang.Aspects;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Component;
import ru.skuptsov.patterns.structural.proxy.DbConnection;
import ru.skuptsov.patterns.structural.proxy.DbConnectionImpl;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.aspectj.lang.Aspects.aspectOf;

/**
 * @author Sergey Kuptsov
 * @since 24/04/2016
 */
public class SpringAspectJProxy {

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

    @Retention(RUNTIME)
    public @interface Transactional {
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

    public static void main(String[] args) {
        AnnotationConfigApplicationContext springConfig = new AnnotationConfigApplicationContext(AspectConfiguration.class);
        DbConnection dbConnection = springConfig.getBean(DbConnection.class);
        dbConnection.select("Select * from users");
    }
}
