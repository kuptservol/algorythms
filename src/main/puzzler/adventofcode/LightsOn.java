package puzzler.adventofcode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

/**
 * @author Sergey Kuptsov
 * @since 05/04/2016
 */
public class LightsOn {

    public static final int DESK_SIZE = 1000;
    public static int lightsOn = 0;
    private static byte[][] lightDesk = new byte[DESK_SIZE][DESK_SIZE];

    public static void main(String[] args) throws IOException {

        for (int i = 0; i < DESK_SIZE; i++) {
            for (int j = 0; j < DESK_SIZE; j++) {
                lightDesk[i][j] = 0;
            }
        }

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(LightsOn.class.getResourceAsStream("/lightOnCfg")));

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            processLine(line);
        }

        System.out.println(lightsOn);
    }

    private static void processLine(String line) {
        String[] tokens = line.split(" ");
        switch (tokens[0]) {
            case "turn":
                if (tokens[1].equals("on"))
                    turnOn(getIterator(tokens[2], tokens[4]));
                else if (tokens[1].equals("off"))
                    turnOff(getIterator(tokens[2], tokens[4]));
                break;
            case "toggle":
                toggle(getIterator(tokens[1], tokens[3]));
        }
    }

    private static Iterator<Pair<Integer, Integer>> getIterator(String leftBottom, String rightUpper) {
        int xLeftBottom = Integer.valueOf(leftBottom.split(",")[0]);
        int yLeftBottom = Integer.valueOf(leftBottom.split(",")[1]);
        int xRightUpper = Integer.valueOf(rightUpper.split(",")[0]);
        int yRightUpper = Integer.valueOf(rightUpper.split(",")[1]);

        return new RectangleIterator(xLeftBottom, yLeftBottom, xRightUpper, yRightUpper);
    }

    private static void turnOn(Iterator<Pair<Integer, Integer>> iterator) {
        iterator.forEachRemaining(pair -> {
            increaseLightsOn(lightDesk[pair.getKey()][pair.getValue()]);
            lightDesk[pair.getKey()][pair.getValue()] = 1;
        });
    }

    private static void turnOff(Iterator<Pair<Integer, Integer>> iterator) {
        iterator.forEachRemaining(pair -> {
            decreaseLightsOn(lightDesk[pair.getKey()][pair.getValue()]);
            lightDesk[pair.getKey()][pair.getValue()] = 0;
        });
    }

    private static void decreaseLightsOn(byte b) {
        if (b == 1)
            lightsOn--;
    }

    private static void increaseLightsOn(byte b) {
        if (b == 0)
            lightsOn++;
    }

    private static void toggle(Iterator<Pair<Integer, Integer>> iterator) {
        iterator.forEachRemaining(pair -> {
            int value = lightDesk[pair.getKey()][pair.getValue()];
            if (value == 1) {
                decreaseLightsOn(lightDesk[pair.getKey()][pair.getValue()]);
                lightDesk[pair.getKey()][pair.getValue()] = 0;
            } else {
                increaseLightsOn(lightDesk[pair.getKey()][pair.getValue()]);
                lightDesk[pair.getKey()][pair.getValue()] = 1;
            }
        });
    }

    private static class RectangleIterator implements Iterator<Pair<Integer, Integer>> {

        int xLeftBottom, yLeftBottom, xRightUpper, yRightUpper;
        int x, y;

        public RectangleIterator(int xLeftBottom, int yLeftBottom, int xRightUpper, int yRightUpper) {
            this.xLeftBottom = xLeftBottom;
            this.yLeftBottom = yLeftBottom;
            this.xRightUpper = xRightUpper;
            this.yRightUpper = yRightUpper;

            x = xLeftBottom;
            y = yLeftBottom;
        }

        @Override
        public boolean hasNext() {
            return x <= xRightUpper && y <= yRightUpper;
        }

        @Override
        public Pair<Integer, Integer> next() {
            Pair pair = new Pair<>(x, y);

            if (x == xRightUpper) {
                y++;
                x = xLeftBottom;
            } else {
                x++;
            }

            return pair;
        }
    }
}
