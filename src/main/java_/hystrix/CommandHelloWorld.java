package java_.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class CommandHelloWorld extends HystrixCommand<String> {

    private final String name;

    public CommandHelloWorld(String name) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.name = name;
    }

    public static void main(String[] args) {
        String s = new CommandHelloWorld("Bob").execute();

        System.out.println(s);
    }

    @Override
    protected String run() {
        return "Hello " + name + "!";
    }
}
