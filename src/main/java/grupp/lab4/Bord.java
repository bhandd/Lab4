package grupp.lab4;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Bord implements Serializable {

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

    public void handleButtonOfChoice(char button, int posX, int posY) {
        if(inGameBord[posX][posY].isLocked())
        {
            inGameBord[posX][posY].setValue(button-'0');
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

    public int handleCheckEndGame() {
        int amount = 0;
        for(int i=0; i < SudokuUtilities.GRID_SIZE; i++) {
            for (int j=0; j < SudokuUtilities.GRID_SIZE; i++) {
                if (inGameBord[i][j].isLocked()) {
                    if(inGameBord[i][i].getValue() == inGameBord[i][j].isTaken()) {
                        amount++;
                    }
                }
            }
        }
        return amount;
    }



    public int Hint(int posx, int posy) {
        return this.inGameBord[posx][posy].isTaken();
    }

    //public int handleHintBordPos(int )

    public int getCurrentValue(int x, int y){
        return inGameBord[x][y].getValue();
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

    public void removeCurrentValue(int posx, int posy) {
        if(inGameBord[posx][posy].isLocked()) {
            inGameBord[posx][posy].removeValue();
        }
    }

    public void GivepalceforHint() {
        Random pos = new Random();
    }

    //Kolla och lägga till andra variabler från classen
    @Override
    public String toString() {
        String info = "";
        for (int i = 0; i < Rows; i++) {
            for(int j = 0; j < Cols; i++) {
                info += inGameBord[i][j].getValue();
            }
        }
        return info;
    }
}
