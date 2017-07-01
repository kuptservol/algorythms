package ru.skuptsov.puzzlers.codingame;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.Scanner;

public class BenderEpisode1 {
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int L = in.nextInt();
        int C = in.nextInt();
        if (in.hasNextLine()) {
            in.nextLine();
        }

        Character[][] map = new Character[L][C];

        for (int i = 0; i < L; i++) {
            String row = in.nextLine();

            char[] chars = row.toCharArray();
            for (int j = 0; j < chars.length; j++) {
                map[i][j] = chars[j];
            }
        }


        try {
            Bender bender = new Bender(map);
            while (bender.isWalking) {
                bender.walk();
            }

            for (Bender.Turn turn : bender.turns) {
                System.out.println(turn.direction);
            }
        } catch (CycleException e) {
            System.out.println("LOOP");
        }
    }

    public interface BenderWalker {
        void walk();
    }

    public interface BenderWalkerState {
        BenderWalkerState walk();
    }

    public static class Bender implements BenderWalker {
        private Optional<Direction> turningPrevDirectionType = Optional.empty();

        private Queue<Turn> turns = new LinkedList<>();
        private int loopsCount = 0;

        private class Turn {
            private final int x;
            private final int y;
            private final Direction direction;
            private int stepCount = 0;

            private Turn(int x, int y, Direction direction) {
                this.x = x;
                this.y = y;
                this.direction = direction;
            }

            @Override
            public String toString() {
                return "Turn{" +
                        "direction=" + direction +
                        '}';
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;

                Turn turn = (Turn) o;

                if (x != turn.x) return false;
                return y == turn.y;
            }

            @Override
            public int hashCode() {
                int result = x;
                result = 31 * result + y;
                return result;
            }
        }

        private enum Direction {
            SOUTH,
            EAST,
            NORTH,
            WEST;

            public static Direction[] normal = {SOUTH, EAST, NORTH, WEST};
            public static Direction[] inverted = {WEST, NORTH, EAST, SOUTH};
        }

        private enum Polarity {
            NORMAL,
            INVERTED
        }

        private enum Mode {
            NORMAL,
            BREAKER
        }

        private BenderWalkerState benderWalkState;
        private int benderX;
        private int benderY;
        private final Character[][] map;
        private Mode mode = Mode.NORMAL;
        private Boolean isWalking = true;
        private Polarity polarity = Polarity.NORMAL;

        private class Teleport {
            private final int teleportX;
            private final int teleportY;

            private Teleport(int teleportX, int teleportY) {
                this.teleportX = teleportX;
                this.teleportY = teleportY;
            }
        }

        private List<Teleport> teleports = new ArrayList<>();

        void switchMode() {
            if (mode == Mode.NORMAL) {
                mode = Mode.BREAKER;
            } else {
                mode = Mode.NORMAL;
            }
        }

        void teleport() {
            Teleport teleport1 = teleports.get(0);
            Teleport teleport2 = teleports.get(1);
            if (benderX == teleport1.teleportX && benderY == teleport1.teleportY) {
                benderX = teleport2.teleportX;
                benderY = teleport2.teleportY;
            } else if (benderX == teleport2.teleportX && benderY == teleport2.teleportY) {
                benderX = teleport1.teleportX;
                benderY = teleport1.teleportY;
            } else
                throw new IllegalArgumentException();
        }

        void finish() {
            isWalking = false;
        }

        void switchPolarity() {
            if (polarity == Polarity.NORMAL) {
                polarity = Polarity.INVERTED;
            } else {
                polarity = Polarity.NORMAL;
            }
        }

        public Bender(Character[][] map) {
            this.map = map;
            this.benderWalkState = new GoSouth();
            for (int i = 0; i < map.length; i++) {
                for (int j = 0; j < map[i].length; j++) {
                    if (map[i][j].equals('T')) {
                        addTeleport(j, i);
                    }
                    if (map[i][j].equals('@')) {
                        this.benderY = i;
                        this.benderX = j;
                    }
                }
            }
        }

        private void addTeleport(int i, int j) {
            teleports.add(new Teleport(i, j));
        }

        @Override
        public void walk() {
            this.benderWalkState = benderWalkState.walk();
        }

        private abstract class BaseBenderWalkState implements BenderWalkerState {

            @Override
            public BenderWalkerState walk() {
                int nextX = getNextX();
                int nextY = getNextY();
                if (!canWalk(nextX, nextY)) {
                    return getNextOnTurn();
                }

                turningPrevDirectionType = Optional.empty();

                checkLoop(nextX, nextY, getDirection());

                turns.add(new Turn(benderX, benderY, getDirection()));

                benderX = nextX;
                benderY = nextY;


                return resolveNext();
            }

            protected BenderWalkerState getNextOnTurn() {
                Direction[] directionsOrder;
                Direction next = null;
                if (turningPrevDirectionType.isPresent()) {
                    if (polarity == Polarity.NORMAL) {
                        directionsOrder = Direction.normal;
                    } else {
                        directionsOrder = Direction.inverted;
                    }

                    for (int i = 0; i < directionsOrder.length; i++) {
                        if (turningPrevDirectionType.get() == directionsOrder[i]) {
                            next = directionsOrder[i + 1];
                        }
                    }
                } else {
                    if (polarity == Polarity.NORMAL) {
                        next = Direction.SOUTH;
                    } else {
                        next = Direction.WEST;
                    }
                }

                turningPrevDirectionType = Optional.of(next);
                return fromDirection(next);
            }

            protected abstract Direction getDirection();

            protected abstract int getNextY();

            protected abstract int getNextX();

            protected BenderWalkerState fromDirection(Direction direction) {
                switch (direction) {
                    case SOUTH:
                        return new GoSouth();
                    case NORTH:
                        return new GoNorth();
                    case EAST:
                        return new GoEast();
                    case WEST:
                        return new GoWest();
                }

                throw new IllegalArgumentException("Inknown direction");
            }

            protected BenderWalkerState resolveNext() {
                Character current = map[benderY][benderX];

                switch (current) {
                    case 'I':
                        switchPolarity();
                        return this;
                    case ' ':
                        return this;
                    case '@':
                        return this;
                    case 'B':
                        switchMode();
                        return this;
                    case 'T':
                        teleport();
                        return this;
                    case '$':
                        finish();
                        return this;
                    case 'N':
                        return fromDirection(Direction.NORTH);
                    case 'S':
                        return fromDirection(Direction.SOUTH);
                    case 'E':
                        return fromDirection(Direction.EAST);
                    case 'W':
                        return fromDirection(Direction.WEST);
                    default:
                        throw new IllegalArgumentException("Illegal move found " + current + " " + benderX + " " + benderY);
                }
            }

            boolean canWalk(int benderNextX, int benderNextY) {
                Character next = map[benderNextY][benderNextX];

                if (next.equals('#'))
                    return false;

                if (next.equals('X')) {
                    if (mode == Mode.NORMAL)
                        return false;
                    else {
                        map[benderNextY][benderNextX] = ' ';
                        return true;
                    }
                }

                return true;
            }
        }

        private void checkLoop(int nextX, int nextY, Direction direction) {
            Turn turn = new Turn(nextX, nextY, direction);
            if (turns.contains(turn)) {
                loopsCount++;
            }

            Iterator<Turn> iterator = turns.iterator();
            while (iterator.hasNext()) {
                Turn turnIter = iterator.next();
                if (turnIter.equals(turn)) {
                    turnIter.stepCount++;
                    if (turnIter.stepCount > 1) {
                        throw new CycleException(turn);
                    }
                }
            }
        }

        class GoSouth extends BaseBenderWalkState {
            @Override
            protected Direction getDirection() {
                return Direction.SOUTH;
            }

            @Override
            protected int getNextY() {
                return benderY + 1;
            }

            @Override
            protected int getNextX() {
                return benderX;
            }
        }

        class GoNorth extends BaseBenderWalkState {
            @Override
            protected Direction getDirection() {
                return Direction.NORTH;
            }

            @Override
            protected int getNextY() {
                return benderY - 1;
            }

            @Override
            protected int getNextX() {
                return benderX;
            }
        }

        class GoWest extends BaseBenderWalkState {

            @Override
            protected Direction getDirection() {
                return Direction.WEST;
            }

            @Override
            protected int getNextY() {
                return benderY;
            }

            @Override
            protected int getNextX() {
                return benderX - 1;
            }
        }

        class GoEast extends BaseBenderWalkState {
            @Override
            protected Direction getDirection() {
                return Direction.EAST;
            }

            @Override
            protected int getNextY() {
                return benderY;
            }

            @Override
            protected int getNextX() {
                return benderX + 1;
            }
        }
    }

    private static class CycleException extends RuntimeException {

        public CycleException(Bender.Turn turn) {
            super("" + turn);
        }
    }
}
