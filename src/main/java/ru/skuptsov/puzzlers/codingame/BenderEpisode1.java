package ru.skuptsov.puzzlers.codingame;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//todo: move to stack
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

        Bender bender = new Bender(map);
        while (bender.isWalking) {
            bender.walk();
        }
    }

    public interface BenderWalker {
        void walk();
    }

    public interface BenderWalkerState {
        BenderWalkerState walk();
    }

    public static class Bender implements BenderWalker {
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

        public Boolean getWalking() {
            return isWalking;
        }

        private abstract class BaseBenderWalkState implements BenderWalkerState {

            @Override
            public BenderWalkerState walk() {
                int nextX = getNextX();
                int nextY = getNextY();
                if (!canWalk(nextX, nextY)) {
                    return getNextOnBrake(getDirection());
                }

                System.out.println(getDirection());

                benderX = nextX;
                benderY = nextY;

                return resolveNext();
            }

            protected BenderWalkerState getNextOnBrake(Direction direction) {
                Direction[] directionsOrder;
                if (mode == Mode.NORMAL) {
                    directionsOrder = Direction.normal;
                } else {
                    directionsOrder = Direction.inverted;
                }

                for (int i = 0; i < directionsOrder.length; i++) {
                    if (direction == directionsOrder[i]) {
                        return fromDirection(directionsOrder[i + 1]);
                    }
                }

                throw new IllegalArgumentException();
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
}
