package ru.skuptsov.patterns.structural.bridge;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by Сергей on 18.04.2016.
 */
public class Bridge {
    public static void main(String[] args) throws InterruptedException {
        Notifier immediateNotifier = new ImmediateNotifier(new SmsProvider());
        Notifier collectNotifier = new CollectNotifier(new PushProvider());


        for (int i = 0; i < 100; i++) {

            Thread.currentThread().sleep(1000);
            immediateNotifier.notify("text" + i);
            collectNotifier.notify("text" + i);
        }
    }

    public interface Provider {
        void send(String text);
    }

    private final static class SmsProvider implements Provider {

        @Override
        public void send(String text) {
            System.out.println("Sending sms");
        }
    }

    private final static class PushProvider implements Provider {

        @Override
        public void send(String text) {
            System.out.println("Sending push");
        }
    }

    private abstract static class Notifier {
        protected final Provider provider;

        protected Notifier(Provider provider) {
            this.provider = provider;
        }

        public abstract void notify(String text);
    }

    private final static class CollectNotifier extends Notifier {

        private final static BlockingQueue<String> queue = new LinkedBlockingQueue<>();

        private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        {
            executor.scheduleWithFixedDelay(() -> {
                List<String> l = new ArrayList<>();
                queue.drainTo(l);
                l.forEach(provider::send);
            }, 0, 5, TimeUnit.SECONDS);
        }


        protected CollectNotifier(Provider provider) {
            super(provider);
        }

        @Override
        public void notify(String text) {
            queue.add(text);
        }
    }

    private final static class ImmediateNotifier extends Notifier {

        protected ImmediateNotifier(Provider provider) {
            super(provider);
        }

        @Override
        public void notify(String text) {
            provider.send(text);
        }
    }
}
