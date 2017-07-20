package puzzler.interview.redmart;

/**
 * @author Sergey Kuptsov
 * @since 14/05/2016
 */

import java.io.InputStream;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

/**
 * Redmart Spreadsheet Problem
 * Created by Sergey on 17.10.2015.
 * <p>
 * Solution motivation :
 * Normally i'm using Recursive Descent Parsing(here is  BNF for this )
 * BNF :
 * <expression>  ::=  <number>  |  <expression> <operator> <expression>
 * <operator>  ::=  "+" | "-" | "*" | "/"
 * <p>
 * But the stack-manner of input string cell text value and links to other cells tells us to use Dijkstra’s Two-Stack algorithm of expression evaluation
 * Getting that we already have right order of operands and values we can evaluate expression on fly with one stack without filling two stacks
 */
public class SpreadSheet {

    /**
     * Spreadsheet dimensions
     */
    private int sprSheetHeight;
    private int sprSheetWidth;
    /**
     * Given spreadsheet
     */
    private Cell[][] cells;

    /**
     * Start main method
     *
     * @param args
     */
    public static void main(String[] args) {

        SpreadSheet spreadSheet = new SpreadSheet();

        spreadSheet.readSpreadSheet(System.in);

        spreadSheet.evaluateCellsExpressions();

        spreadSheet.printFormattedResults();

    }

    /**
     * Method  evaluates current cell value
     *
     * @param cell                        - cell for value expression
     * @param currentExpressionValueStack - for recursive cycle loop identification
     *                                    We can use current stack of cell expression evaluation
     *                                    - if we met link to cell that already exists on stack - loop found
     * @return value of cell
     */
    private double expressionValue(Cell cell, Set<Cell> currentExpressionValueStack) {

        if (currentExpressionValueStack == null)
            currentExpressionValueStack = new HashSet<>();


        if (cell.isHasCalculatedValue()) {
            return cell.getValue();
        } else if (!currentExpressionValueStack.contains(cell)) {

            currentExpressionValueStack.add(cell);

            Stack<Double> ops = new Stack<>();

            String[] tokens = cell.getText().split(" ");

            for (int i = 0; i < tokens.length; i++) {
                String token = tokens[i];

                if (token.equals("+")) {
                    ops.push(ops.pop() + ops.pop());
                } else if (token.equals("-")) {
                    double val2 = ops.pop();
                    double val1 = ops.pop();

                    ops.push(val1 - val2);
                } else if (token.equals("*")) {
                    ops.push(ops.pop() * ops.pop());
                } else if (token.equals("/")) {

                    double val1 = ops.pop();
                    double val2 = ops.pop();

                    ops.push(val2 / val1);
                } else if (Helper.isNumeric(token)) {
                    ops.push(Double.parseDouble(token));
                } else if (Helper.isCellLink(token)) {
                    ops.push(expressionValue(findCellByToken(token), currentExpressionValueStack));
                } else
                    throw new IllegalArgumentException("Wrong expression syntax found at cell: " + cell.getText());
            }

            cell.setValue(ops.pop());
            cell.setHasCalculatedValue(true);


        } else {
            System.out.println("Found loop on evaluation expression of cell : " + cell.getText());

            System.out.println("Loop:  ");
            for (Cell loopCell : currentExpressionValueStack) {
                System.out.print(" cell : " + loopCell.getText() + " -> ");
            }

            System.exit(1);
        }

        return cell.getValue();

    }

    /**
     * Founds cell by string cell link
     *
     * @param token
     * @return cell
     */
    private Cell findCellByToken(String token) {

        int x = (int) token.charAt(0) - 65;
        int y = Integer.parseInt(token.substring(1, token.length())) - 1;

        if (x > sprSheetWidth || y > sprSheetHeight || x < 0 || y < 0)
            throw new IllegalArgumentException("Wrong value of cell link : " + token);

        return cells[x][y];

    }

    /**
     * Calculate and save expression value of each cell
     */
    private void evaluateCellsExpressions() {

        for (int i = 0; i < sprSheetWidth; i++) {
            for (int j = 0; j < sprSheetHeight; j++) {
                expressionValue(cells[i][j], null);
            }
        }

    }

    /**
     * Read SpreadsheetTemp values from input stream
     *
     * @param in input stream
     */
    private void readSpreadSheet(InputStream in) {

        Scanner scanner = new Scanner(in);

        //read size of spreadsheet
        if (scanner.hasNextLine()) {
            String[] dimensions = scanner.nextLine().split(" ");

            if (dimensions.length != 2) {
                throw new IllegalArgumentException("Invalid size of spreadsheet, must be: (int)X (int)Y");
            } else {

                sprSheetWidth = Integer.parseInt(dimensions[1]);
                if (sprSheetWidth > 26)
                    throw new IllegalArgumentException("Wrong numer of rows must be < 26");
                sprSheetHeight = Integer.parseInt(dimensions[0]);

                cells = new Cell[sprSheetWidth][sprSheetHeight];

            }
        }

        //filling spreadsheet
        int row = 0;
        int coll = 0;
        int numOfCells = 0;
        while (scanner.hasNextLine()) {
            String nextCellText = scanner.nextLine();
            if (nextCellText.isEmpty())
                break;

            cells[row][coll] = new Cell(nextCellText);
            numOfCells++;
            coll++;
            if (coll == sprSheetHeight) {
                row++;
                coll = 0;
            }
        }


        if (numOfCells != sprSheetWidth * sprSheetHeight)
            throw new IllegalArgumentException("Number of cells does not maatch given one");
    }

    /**
     * Prints results of spreadsheet cell expression values in form
     * x y,A1,A2..A<n>,B1,...B<n>..
     */
    private void printFormattedResults() {

        System.out.println(sprSheetHeight + " " + sprSheetWidth);
        for (int i = 0; i < sprSheetWidth; i++) {
            for (int j = 0; j < sprSheetHeight; j++) {
                System.out.printf("%.5f", cells[i][j].getValue());
                System.out.println();
            }
        }

    }
}

