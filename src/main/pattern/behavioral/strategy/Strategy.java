package pattern.behavioral.strategy;

/**
 * @author Sergey Kuptsov
 * @since 01/05/2016
 */
public class Strategy {

    public static void main(String[] args) {
        SortClient sortClient = new SortClient(new MergeSort());

        sortClient.sort(new Object[]{});
    }

    interface Sort {
        void sort(Object[] massive);
    }

    private static class MergeSort implements Sort {

        @Override
        public void sort(Object[] massive) {
            System.out.println("MergeSort");
        }
    }

    private static class QuickSort implements Sort {

        @Override
        public void sort(Object[] massive) {
            System.out.println("QuickSort");
        }
    }

    private static class SortClient {

        private final Sort sortStrategy;

        SortClient(Sort sortStrategy) {
            this.sortStrategy = sortStrategy;
        }

        public void sort(Object[] massive) {
            sortStrategy.sort(massive);
        }
    }
}
