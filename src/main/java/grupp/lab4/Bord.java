package grupp.lab4;

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

    public Square getPosOnBordByPos(int posX, int posY) {
        return inGameBord[posX][posY];
    }

    public void handleButtonOfChoice(int button, int posX, int posY) {
        if(inGameBord[posX][posY].isLocked()) {
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
}
