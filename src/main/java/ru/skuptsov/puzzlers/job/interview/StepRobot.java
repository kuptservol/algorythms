package ru.skuptsov.puzzlers.job.interview;

/**
 * @author Sergey Kuptsov
 * @since 22/09/2016
 */
public class StepRobot {

    public static void main(String[] args) {
        Object monitor = new Object();
        Robot left = new Robot("left", monitor);
        Robot right = new Robot("right", monitor);

        Thread leftTh = new Thread(left, "left");
        Thread rightTh = new Thread(right, "right");

        leftTh.start();
        rightTh.start();
    }

    private static class Robot implements Runnable {
        private final String leg;
        private final Object monitor;

        private Robot(String leg, Object monitor) {
            this.leg = leg;
            this.monitor = monitor;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    makeStep();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        protected void makeStep() throws InterruptedException {
            synchronized (monitor) {
                monitor.notify();
                System.out.println(leg);
                monitor.wait();
            }
        }
    }
}
