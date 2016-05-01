package ru.skuptsov.puzzlers.adventofcode;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Sergey Kuptsov
 * @since 21/03/2016
 */
public class PresentDeliveredTwice {

    public static final int ALREADY_COUNTED = 1;

    public static void main(String[] args) throws IOException {

        Reader reader = new InputStreamReader(PresentDeliveredTwice.class.getResourceAsStream("/deliveredTwice.txt"));

        int direction = 0;
        int housesDistinct = 1;
        Map<House, Integer> visitedHouses = new HashMap<>();
        House startHouse = new House(0, 0);
        visitedHouses.put(startHouse, ALREADY_COUNTED);
        House currentHouseSanta = startHouse;
        House currentHouseRoboSanta = startHouse;
        House currentSanta;
        int i = 0;
        while ((direction = reader.read()) != -1) {

            if (i % 2 != 0)
                currentSanta = currentHouseSanta;
            else
                currentSanta = currentHouseRoboSanta;

            int x = currentSanta.x;
            int y = currentSanta.y;
            char dir = (char) direction;
            if (dir == '^') {
                y++;
            }
            else if (dir == 'v') {
                y--;
            }
            else if (dir == '<') {
                x--;
            }
            else if (dir == '>') {
                x++;
            }

            House newHouse = new House(x, y);
            if (i % 2 != 0) {
                currentHouseSanta = newHouse;
            }
            else {
                currentHouseRoboSanta = newHouse;
            }

            if (!visitedHouses.containsKey(newHouse)) {
                housesDistinct += 1;
                visitedHouses.put(newHouse, ALREADY_COUNTED);
            } else {
                if (visitedHouses.get(newHouse) != ALREADY_COUNTED) {
                    housesDistinct += 1;
                    visitedHouses.put(newHouse, ALREADY_COUNTED);
                }
            }
            i++;
        }

        System.out.println(housesDistinct);
    }

    private static class House {

        int x;
        int y;

        public House(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int hashCode() {
            return x + y;
        }

        public boolean equals(Object o) {
            if (o == this) return true;
            if (o.getClass() != this.getClass()) return false;
            House that = (House) o;

            return that.x == x && that.y == y;

        }
    }

}

