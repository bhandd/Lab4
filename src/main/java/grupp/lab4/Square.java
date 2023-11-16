package grupp.lab4;

public class Square {

        private boolean taken = false;
        private boolean locked = false;
        private int value = 0;


        public Square(boolean taken, boolean locked, int value) {
            this.taken = taken;
            this.locked = locked;
            this.value = value;

        }

        public Square() {
            this.taken = false;
            this.locked = false;
            this.value = 0;

        }

        public boolean isTaken() {
            return taken;
        }

        public void setTaken() {
            this.taken = true;
        }
        public void removeTaken() {
            this.taken = false;
        }

        public boolean isLocked() {
            return locked;
        }

        public void setLocked() {
            this.locked = true;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            if(value < 1 || value > 9) this.value = value;
        }




}
