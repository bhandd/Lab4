package grupp.lab4;

import java.io.Serializable;

public class Bord implements Serializable {

    public static final int Cols = 9;
    public static final int Rows = 9;
    private Square inGameBord[][] = new Square[Rows][Cols];
    private SudokuUtilities.SudokuLevel theDiffiulty;

    /**
     *
     * @param theDiffiulty
     */
    public Bord(SudokuUtilities.SudokuLevel theDiffiulty) {
        int tempBord[][][] = SudokuUtilities.generateSudokuMatrix(theDiffiulty);

        for (int i = 0; i < Rows; i++) {
            for (int j = 0; j < Cols; j++) {
                Square data = new Square(tempBord[i][j][1], false, tempBord[i][j][0]);
                this.inGameBord[i][j] = data;

                if (tempBord[i][j][0] == 0) {
                    inGameBord[i][j].setLocked(true);
                }
            }
        }
        this.theDiffiulty = theDiffiulty;
    }


    /**
     * Parameterlös konstruktor
     * Skapar en tom 9x9 matrix
     **/
    public Bord() {

        //Square square = inGameBord[Rows][Cols];
//        for(int i = 0;i<Rows;i++) {
//            for (int j = 0;j<Cols;j++) {
//                Square data = new Square(tempBord[i][j][1],false,tempBord[i][j][0]);
//                this.inGameBord[i][j] = data;
//
//                if (tempBord[i][j][0] == 0) {
//                    inGameBord[i][j].setLocked(true);
//                }
//            }
//        }

    }

    /**
     * Gets the info of the specific square
     * @param posX x position on the bord
     * @param posY y position on the bord
     * @return the info of given square
     */
    public Square getSquareInfoByPos(int posX, int posY) {
        return inGameBord[posX][posY];
    }

    /**
     * Handles button of choice and sets values to given square
     * @param button button of choice
     * @param posX x position on the bord
     * @param posY y position on the bord
     */
    public void handleButtonOfChoice(char button, int posX, int posY) {
        if (inGameBord[posX][posY].isLocked()) {
            inGameBord[posX][posY].setValue(button - '0');
        }
    }

    public int checkGame() {
        int count = 0;
        for (int row = 0; row < SudokuUtilities.GRID_SIZE; row++) {
            for (int col = 0; col < SudokuUtilities.GRID_SIZE; col++) {
                //If
            }
        }
        return count;
    }

    /**
     * Checks the amount of squares that are correct
     * @return the amount of correct squares
     */
    public int handleCheckGame() {
        int amount = 0;
        for (int i = 0; i < SudokuUtilities.GRID_SIZE; i++) {
            for (int j = 0; j < SudokuUtilities.GRID_SIZE; j++) {
                if (inGameBord[i][j].getValue() == inGameBord[i][j].isTaken()) {
                    amount++;
                }
            }
        }
        return amount;
    }

    /**
     * Check for amount of placed numbers on the game bord
     *
     * @return the amount of placed numbers
     */
    public int checkPlaced() {
        int count = 0;
        for (int i = 0; i < SudokuUtilities.GRID_SIZE; i++) {
            for (int j = 0; j < SudokuUtilities.GRID_SIZE; j++) {
                if (getSquareInfoByPos(i, j).getValue() != 0) {
                    count++;
                }
            }
        }
        return count;
    }


    public int Hint(int posx, int posy) {
        return this.inGameBord[posx][posy].isTaken();
    }

    //public int handleHintBordPos(int )
    /** Help method
     * Returns the current value at the specified position (x, y) on the Sudoku board.
     *
     * @param x The x-coordinate of the position to get the value for.
     * @param y The y-coordinate of the position to get the value for.
     * @return The current value at the specified position (x, y).
     */
    public int getCurrentValue(int x, int y) {
        return inGameBord[x][y].getValue();
    }

    /**
     * Help Method
     */
    public void printAllIntegers(){

        // System.out.println(Arrays.deepToString(inGameBord));

        for (int i = 0; i < 9; i++) {
            System.out.print("|");
            for(int j = 0; j < 9; j++) {
                if( j % 9 == 0){
                    System.out.println();
                }
                System.out.print("|");
                System.out.print(inGameBord[i][j].getValue());

            }

        }
        System.out.println("|");
    }

    public void setSquareValue(int val, int row, int col) {
        if (inGameBord[row][col].getValue() == 0) {
            inGameBord[row][col].setValue(val);
        }
    }

    /**
     * Gets the current game difficulty in an enum.
     * @return the game difficulty.
     */
    public SudokuUtilities.SudokuLevel getTheDiffiulty() {
        return theDiffiulty;
    }

    /**
     * Set the difficulty of a game.
     * @param theDiffiulty to set to.
     */
    public void setTheDiffiulty(SudokuUtilities.SudokuLevel theDiffiulty) {
        this.theDiffiulty = theDiffiulty;
    }

    /**
     * Gets the current in game bord
     * @return in game bord
     */
    public Square[][] getInGameBord() {

        return inGameBord;
    }

    /**
     * If a square has a value then remove it
     * @param posx x position on the bord
     * @param posy y position on the bord
     */
    public void removeCurrentValue(int posx, int posy) {
        if (inGameBord[posx][posy].isLocked()) {
            inGameBord[posx][posy].removeValue();
        }
    }


    //Kolla och lägga till andra variabler från classen
    @Override
    public String toString() {
        String info = "";
        for (int i = 0; i < Rows; i++) {
            for (int j = 0; j < Cols; i++) {
                info += inGameBord[i][j].getValue();
            }
        }
        return info;
    }
}
