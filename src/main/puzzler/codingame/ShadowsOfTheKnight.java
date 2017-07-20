package puzzler.codingame;

import java.util.Scanner;

/*
U (Up)
UR (Up-Right)
R (Right)
DR (Down-Right)
D (Down)
DL (Down-Left)
L (Left)
UL (Up-Left)
 */
public class ShadowsOfTheKnight {

    public static void main(String args[]) {

        Scanner in = new Scanner(System.in);
        int W = in.nextInt(); // width of the building.
        int H = in.nextInt(); // height of the building.
        int N = in.nextInt(); // maximum number of turns before game over.
        int X0 = in.nextInt();
        int Y0 = in.nextInt();

        int windowStartX = 0;
        int windowEndX = W;
        int windowStartY = 0;
        int windowEndY = H;

        int currX = X0;
        int currY = Y0;
        // game loop
        while (true) {
            String bombDir = in.next(); // the direction of the bombs from batman's current location (U, UR, R, DR, D, DL, L or UL)

            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");

            int deltaX = 0;
            int deltaY = 0;

            if (bombDir.contains("U")) {
                deltaY -= binaryDiv(currY, windowStartY);
                windowEndY = currY;
            }

            if (bombDir.contains("D")) {
                deltaY += binaryDiv(windowEndY, currY);
                windowStartY = currY;
            }

            if (bombDir.contains("R")) {
                deltaX += binaryDiv(windowEndX, currX);
                windowStartX = currX;
            }

            if (bombDir.contains("L")) {
                deltaX -= binaryDiv(currX, windowStartX);
                windowEndX = currX;
            }

            currX += deltaX;
            currY += deltaY;

            // the location of the next window Batman should jump to.
            System.out.println(currX + " " + currY);
        }
    }

    private static int binaryDiv(int from, int to) {

        if (from - to == 1)
            return 1;
        return (int) Math.ceil((from - to) / 2d);
    }
}
