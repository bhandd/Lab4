package grupp.lab4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Bord {

    public static final int Cols = 9;
    public static final int Rows = 9;
    private Square inGameBord[][] = new Square[Rows][Cols];
    private SudokuUtilities.SudokuLevel theDiffiulty;

    public Bord(SudokuUtilities.SudokuLevel theDiffiulty) {
        int tempBord[][][] = SudokuUtilities.generateSudokuMatrix(theDiffiulty);
        for(int i = 0;i<Rows;i++) {
            for (int j = 0;j<Cols;j++) {
                Square data = new Square(tempBord[i][j][1],false,tempBord[i][j][0]);
                this.inGameBord[i][j] = data;

                if (tempBord[i][j][0] == 0) {
                    inGameBord[i][j].setLocked(true);
                }
            }
        }
        this.theDiffiulty = theDiffiulty;
    }


    /** Parameterlös konstruktor
     * Skapar en tom 9x9 matrix
     *
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

    public Square getPosOnBordByPos(int posX, int posY) {
        return inGameBord[posX][posY];
    }

    public void handleButtonOfChoice(int button, int posX, int posY) {
        if(inGameBord[posX][posY].isLocked())
        {
            inGameBord[posX][posY].setValue(button);
        }
    }

    public int handleCheckGame() {
        int count = 0;
        for(int row = 0;row < SudokuUtilities.GRID_SIZE;row++) {
            for(int col = 0;col < SudokuUtilities.GRID_SIZE;col++) {
                //If
            }
        }
        return count;
    }

    public boolean handleCheckEndGame() {
        return true;
    }



    public int Hint() {
        return 1;
    }

    /*public Square getSqareValue(int row, int col) {
       return inGameBord[row][col];
    }*/

    public int getCurrentValue(int x, int y){
        return this.inGameBord[x][y].getValue();
    }

    public void setSquareValue(int val, int row, int col) {
        inGameBord[row][col].setValue(val);
    }



    public SudokuUtilities.SudokuLevel getTheDiffiulty() {
        return theDiffiulty;
    }

    public void setTheDiffiulty(SudokuUtilities.SudokuLevel theDiffiulty) {
        this.theDiffiulty = theDiffiulty;
    }

    public Square[][] getInGameBord() {

        return inGameBord;
    }


    @Override
    public String toString() {
        return "Bord{" +
                "inGameBord=" + Arrays.toString(inGameBord) +
                ", theDiffiulty=" + theDiffiulty +
                '}';
    }
}
