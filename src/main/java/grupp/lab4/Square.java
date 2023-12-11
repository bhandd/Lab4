package grupp.lab4;

import java.io.Serializable;

public class Square implements Serializable {

        //TODO ändra namn på variablerna

        private int taken;
        private boolean locked;
        private int value;


        public Square(int taken, boolean locked, int value) {
            this.taken = taken;
            this.locked = locked;
            this.value = value;

        }

        public Square() {
            this.taken = 0;
            this.locked = false;
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

        public boolean isLocked() {
            return locked;
        }

        public void setLocked(boolean set) {
            if(set == true) {
                this.locked = true;
            }
            else {
                this.locked = false;
            }
        }

        public int getValue() {
            return value;
        }
/** **/
        public void setValue(int value) {
            this.value = value;
        }

    @Override
    public String toString() {
        return "squareData{" +
                "correctValue=" + taken +
                ", hidden=" + locked +
                ", currentValue=" + value +
                '}';
    }
}
