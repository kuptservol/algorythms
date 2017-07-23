package java_.challenge;

public class DoubleInheritance {

    interface Crazy {
        void action();
    }

    // hiding works here
    interface Dazy extends Crazy {
        void action();
    }

    static class CrazyDazy implements Crazy, Dazy {

        @Override
        public void action() {
            System.out.println("CrazyDazy");
        }
    }

    public static void main(String[] args) {
        new CrazyDazy().action();
    }
}
