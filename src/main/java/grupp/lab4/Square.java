package grupp.lab4;

public class Square {

        private int taken = 0;
        private boolean locked = false;
        private int value = 0;


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

        public void setValue(int value) {
            if(value < 1 || value > 9) this.value = value;
        }




}
