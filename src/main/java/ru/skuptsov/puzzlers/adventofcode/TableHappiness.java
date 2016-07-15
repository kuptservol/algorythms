package ru.skuptsov.puzzlers.adventofcode;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Table;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.Integer.valueOf;

/**
 * @author Sergey Kuptsov
 * @since 14/07/2016
 */
public class TableHappiness {

    private static Table<String, String, Integer> happinesTable = HashBasedTable.create();
    private static int peoplesCount;
    private static List<Integer> peoplesHappinessValues = new ArrayList<>();

    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(TableHappiness.class.getResourceAsStream("/tableHappiness.txt")));

        reader.lines()
                .forEach(line -> {
                    String[] parsed = line.split(" ");
                    happinesTable.put(parsed[0], parsed[10].substring(0, parsed[10].length() - 1), parsed[2].equals("gain") ? valueOf(parsed[3]) : -valueOf(parsed[3]));
                });

        Set<String> users = ImmutableSet.copyOf(happinesTable.rowKeySet());
        users.forEach(rowKey -> happinesTable.put("Me", rowKey, 0));
        users.forEach(rowKey -> happinesTable.put(rowKey, "Me", 0));

        peoplesCount = happinesTable.rowKeySet().size();

        findBestPositions();
    }

    private static void findBestPositions() {
        List<String> people = happinesTable.rowKeySet().stream().collect(Collectors.toList());

        //System.out.println(new TableDisposition(people).getTableHappiness());

        getPeopleVariants(new ArrayList<>());

        System.out.println(peoplesHappinessValues.stream().max(Integer::compareTo).get());

        //System.out.println(peoplesHappinessValues);
    }

    private static void getPeopleVariants(List<String> variants) {
        if (variants.size() == peoplesCount) {
            //System.out.println(variants);
            peoplesHappinessValues.add(new TableDisposition(variants).getTableHappiness());
        } else {
            List<String> possibleVariants = new ArrayList<>();
            for (String variant : happinesTable.rowKeySet()) {
                if (!variants.contains(variant)) {
                    possibleVariants.add(variant);
                }
            }

            for (String variant : possibleVariants) {
                List<String> variantsNew = new ArrayList<>(variants);
                variantsNew.add(variant);
                getPeopleVariants(variantsNew);
            }
        }
    }

    private static class TableDisposition {

        private TableSeat startSeat;
        private TableSeat lastSeat;

        private TableDisposition() {

        }

        public TableDisposition(List<String> people) {
            TableDisposition.TableSeat startSeat = new TableDisposition.TableSeat(people.get(0));
            add(startSeat);

            for (int i = 1; i < people.size(); i++) {
                add(new TableDisposition.TableSeat(people.get(i)));
            }

            getLastSeat().setNext(startSeat);
            getStartSeat().setPrev(getLastSeat());
        }

        public TableSeat getLastSeat() {
            return lastSeat;
        }

        public TableSeat getStartSeat() {
            return startSeat;
        }

        public void add(TableSeat tableSeat) {
            if (lastSeat == null) {
                startSeat = tableSeat;
                lastSeat = tableSeat;
                startSeat.setNext(lastSeat);
                lastSeat.setPrev(startSeat);
            } else {
                lastSeat.setNext(tableSeat);
                tableSeat.setPrev(lastSeat);
                lastSeat = tableSeat;
            }
        }

        public int getTableHappiness() {

            int happiness = 0;

            int i = 0;
            for (TableSeat currSeat = startSeat; i == 0 || !startSeat.equals(currSeat); currSeat = currSeat.next) {
                i = 1;
                System.out.println(currSeat.getPrev() + " " + currSeat + " " + currSeat.getNext());

                happiness += happinesTable.get(currSeat.name, currSeat.getPrev().name);
                happiness += happinesTable.get(currSeat.name, currSeat.getNext().name);
            }

            return happiness;
        }

        private static class TableSeat {

            private final String name;

            private TableSeat next;
            private TableSeat prev;

            private TableSeat(String name) {
                this.name = name;
            }

            public TableSeat getNext() {
                return next;
            }

            public void setNext(TableSeat next) {
                this.next = next;
            }

            public TableSeat getPrev() {
                return prev;
            }

            public void setPrev(TableSeat prev) {
                this.prev = prev;
            }

            @Override
            public String toString() {
                return MoreObjects.toStringHelper(this)
                        .add("name", name)
                        .toString();
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                TableSeat tableSeat = (TableSeat) o;
                return Objects.equal(name, tableSeat.name);
            }
        }
    }
}
