package pattern.behavioral.iterator;

import java.util.ConcurrentModificationException;
import java.util.List;

import com.google.common.collect.ImmutableList;

/**
 * Created by Сергей on 12.05.2016.
 */
public class Iterator {

    public static void main(String[] args) {
        for (Long aLong : new OddNumberIterator(ImmutableList.of(1L, 2L, 3L, 5L, 6L, 6L, 7L, 8L))) {
            System.out.println(aLong);
        }
    }

    private static class OddNumberIterator implements Iterable<Long>, java.util.Iterator<Long> {

        private final List<Long> list;
        private final int listSize;
        private int currentPos;

        private OddNumberIterator(List<Long> list) {
            this.list = list;
            listSize = list.size();
        }

        @Override
        public java.util.Iterator<Long> iterator() {
            return new OddNumberIterator(list);
        }

        private void checkConcurrentModification() {
            if (list.size() != listSize)
                throw new ConcurrentModificationException();
        }

        @Override
        public boolean hasNext() {
            checkConcurrentModification();
            for (int j = currentPos; j < listSize; j++) {
                if (list.get(j) % 2 != 0)
                    return true;
            }
            return false;
        }

        @Override
        public Long next() {
            checkConcurrentModification();
            while (list.get(currentPos) % 2 == 0) {
                currentPos++;
            }
            Long aLong = list.get(currentPos);
            currentPos++;
            return aLong;
        }
    }
}
