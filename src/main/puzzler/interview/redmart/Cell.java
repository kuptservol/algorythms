package puzzler.interview.redmart;

/**
 * @author Sergey Kuptsov
 * @since 14/05/2016
 */

/**
 * Sheet cell
 */
public final class Cell {

    /**
     * Cell expression Value
     */
    private double value;
    /**
     * String cell text
     */
    private String text;
    /**
     * Identifies if cell already have been counted
     */
    private boolean hasCalculatedValue;


    public Cell(String text) {
        this.text = text;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isHasCalculatedValue() {
        return hasCalculatedValue;
    }

    public void setHasCalculatedValue(boolean hasCalculatedValue) {
        this.hasCalculatedValue = hasCalculatedValue;
    }
}
