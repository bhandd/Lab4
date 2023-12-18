package grupp.lab4.model;

import java.io.Serializable;

public class Square implements Serializable {

    //TODO ändra namn på variablerna

    private int taken;
    private boolean hidden;
    private int value;


    public Square(int taken, boolean locked, int value) {
        this.taken = taken;
        this.hidden = locked;
        this.value = value;

    }

    public Square() {
        this.taken = 0;
        this.hidden = false;
        this.value = 0;
    }

    public int isTaken() {
        return taken;
    }

    public void setTaken() {
        this.taken = 0;
    }

    public void removeValue() {
        this.value = 0;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean set) {
        if (set == true) {
            this.hidden = true;
        } else {
            this.hidden = false;
        }
    }

    public int getValue() {
        return value;
    }

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
