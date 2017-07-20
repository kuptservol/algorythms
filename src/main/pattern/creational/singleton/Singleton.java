package pattern.creational.singleton;

/**
 * @author Sergey Kuptsov
 * @since 29/04/2016
 */
public class Singleton {

    public static void main(String[] args) {

        LazySingleton.getInstance().run();
        LazySingleton.getInstance().run();
        LazySingleton.getInstance().run();

        InitializationSingleton.INSTANCE.run();
        InitializationSingleton.INSTANCE.run();
        InitializationSingleton.INSTANCE.run();
    }

    /**
     * todo: really thread safe?
     */
    private final static class LazySingleton {

        private static volatile LazySingleton singleton;

        private LazySingleton() {
            System.out.println("Create one LazySingleton");
        }

        public static LazySingleton getInstance() {
            if (singleton == null) {
                synchronized (LazySingleton.class) {
                    if (singleton == null) {
                        singleton = new LazySingleton();
                    }
                }
            }

            return singleton;
        }

        public void run() {
            System.out.println(this.toString() + " run");
        }

    }

    /**
     * actually this is prefferrable and lazy too
     */
    private final static class InitializationSingleton {
        public final static InitializationSingleton INSTANCE = new InitializationSingleton();

        private InitializationSingleton() {
            System.out.println("Create one InitializationSingleton");
        }

        public void run() {
            System.out.println(this.toString() + " run");
        }
    }

}
