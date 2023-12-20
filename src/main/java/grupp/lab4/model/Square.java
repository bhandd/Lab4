package grupp.lab4.model;

import java.io.Serializable;

public class Square implements Serializable {

    private int taken;
    private boolean hidden;
    private int value;

    /**
     * Initializes a new `Square` instance with the given properties.
     * @param taken Whether the square has a value placed in it.
     * @param locked Whether the square is visible or hidden.
     * @param value The value placed in the square.
     */
    public Square(int taken, boolean locked, int value) {
        this.taken = taken;
        this.hidden = locked;
        this.value = value;

    }

    /**
     * Initializes a new `Square` instance with default properties.
     * The square is considered empty with no value (`value = 0`), not locked (`hidden = false`), and not filled (`taken = 0`).
     */
    public Square() {
        this.taken = 0;
        this.hidden = false;
        this.value = 0;
    }

    /**
     * Determines whether the item is currently taken or not.
     *
     * @return An integer representing whether the item is taken: 1 if taken, 0 if not taken.
     */
    public int isTaken() {
        return taken;
    }

    public void setTaken() {
        this.taken = 0;
    }

    public void removeValue() {
        this.value = 0;
    }

    /**
     * Returns whether the object is currently hidden or not.
     *
     * @return True if the object is hidden, false otherwise.
     */
    public boolean isHidden() {
        return hidden;
    }

    /**
     * Sets the hidden state of the object.
     *
     * @param set True to set the object as hidden, false to set it as visible.
     */
    public void setHidden(boolean set) {
        if (set == true) {
            this.hidden = true;
        } else {
            this.hidden = false;
        }
    }

    /**
     * Retrieves the current value of the object.
     *
     * @return The current value of the object.
     */
    public int getValue() {
        return value;
    }

    /**
     * Sets the value of the object.
     *
     * @param value The new value to set for the object.
     */
    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "squareData{" +
                "correctValue=" + taken +
                ", hidden=" + hidden +
                ", currentValue=" + value +
                '}';
    }
}
