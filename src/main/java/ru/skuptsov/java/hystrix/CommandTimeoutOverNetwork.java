package ru.skuptsov.java.hystrix;

import java.util.concurrent.TimeoutException;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import org.testng.annotations.Test;

/**
 * @author Sergey Kuptsov <kuptservol@yandex-team.ru>
 */
public class CommandTimeoutOverNetwork extends HystrixCommand<String> {

    public static final String SUCCESS_RESULT = "success result";

    protected CommandTimeoutOverNetwork(HystrixCommandGroupKey groupKey) {
        super(groupKey);
    }

    protected CommandTimeoutOverNetwork(Setter setter) {
        super(setter);
    }

    @Override
    protected String run() throws Exception {
        Thread.sleep(1000);

        return SUCCESS_RESULT;
    }


    public static class TestRetry {

        @Test
        public void testSuccess() {
            CommandTimeoutOverNetwork command =
                    new CommandTimeoutOverNetwork(
                            Setter
                                    .withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"))
                                    .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                                            .withExecutionTimeoutEnabled(false)
                                    )
                    );

            command.execute();
        }

        @Test(expectedExceptions = Exception.class)
        public void testWithTimeout() {
            CommandTimeoutOverNetwork command =
                    new CommandTimeoutOverNetwork(
                            Setter
                                    .withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"))
                                    .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                                            .withExecutionTimeoutEnabled(true)
                                            .withExecutionTimeoutInMilliseconds(500)
                                    )
                    );

            command.execute();
        }
    }
}
