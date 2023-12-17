package grupp.lab4;

import java.io.Serializable;

public class SudokuUtilities implements Serializable {

    public enum SudokuLevel {EASY, MEDIUM, HARD}
//TODO: kolla variabelnamn och vart vi ska använda SECTIONS_PER_ROW och SECTION_SIZE
    public static final int GRID_SIZE = 9;
    public static final int SECTIONS_PER_ROW = 3;
    public static final int SECTION_SIZE = 3;

    /**
     * Create a 3-dimensional matrix with initial values and solution in Sudoku.
     *
     * @param level The level, i.e. the difficulty, of the initial standing.
     * @return A 3-dimensional int matrix.
     * [row][col][0] represents the initial values, zero representing an empty cell.
     * [row][col][1] represents the solution.
     * @throws IllegalArgumentException if the length of stringRepresentation is not 2*81 characters and
     *                                  for characters other than '0'-'9'.
     */
    public static int[][][] generateSudokuMatrix(SudokuLevel level) {
        String representationString;
        int[][][] representationInt;
        switch (level) {
            case EASY:
                representationString = easy;
                break;
            case MEDIUM:
                representationString = medium;
                break;
            case HARD:
                representationString = hard;
                break;
            default:
                representationString = medium;
        }
        //Original
        //return convertStringToIntMatrix(representationString);
        representationInt = convertStringToIntMatrix(representationString);
        return  randomizeSudoku(representationInt);
    }

    /**
     * Provides a hint for the given Sudoku puzzle.
     * This method takes a Sudoku puzzle represented as a SudokuBoard object and provides a hint by placing a value in a random empty cell.
     * @param bord The Sudoku puzzle to be checked for available hints.
     */
    public static void getHint(Bord bord){
        if (bord.checkPlaced() == 81) {
            throw new IllegalArgumentException("Bord is filled");
        }

        int RowToPlaceHint = 0;
        int ColToPlaceHint = 0;
        int hintValue = 0;
        while (hintValue == 0) { //look for place to place hint
            RowToPlaceHint = (int) (Math.random() * 9);
            ColToPlaceHint = (int) (Math.random() * 9);
            if (bord.getCurrentValue(RowToPlaceHint, ColToPlaceHint) == 0) {
                hintValue = bord.Hint(RowToPlaceHint, ColToPlaceHint);
            }
        }
        bord.setSquareValue(hintValue, RowToPlaceHint, ColToPlaceHint);
    }

    /**
     * Create a 3-dimerepresentationIntnsional matrix with initial values and solution in Sudoku.
     * @param stringRepresentation A string of 2*81 characters, 0-9. The first 81 characters represents
     *                             the initial values, '0' representing an empty cell.
     *                             The following 81 characters represents the solution.
     * @return A 3-dimensional int matrix.
     * [row][col][0] represents the initial values, zero representing an empty cell.
     * [row][col][1] represents the solution.
     * @throws IllegalArgumentException if the length of stringRepresentation is not 2*81 characters and
     *                                  for characters other than '0'-'9'.
     */
    /*package private*/
    static int[][][] convertStringToIntMatrix(String stringRepresentation) {
        if (stringRepresentation.length() != GRID_SIZE * GRID_SIZE * 2)
            throw new IllegalArgumentException("representation length " + stringRepresentation.length());

        int[][][] values = new int[GRID_SIZE][GRID_SIZE][2];
        char[] charRepresentation = stringRepresentation.toCharArray();

        int charIndex = 0;
        // initial values
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                values[row][col][0] =

                        convertCharToSudokuInt(charRepresentation[charIndex++]);
            }
        }
        // solution values
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                values[row][col][1] =
                        convertCharToSudokuInt(charRepresentation[charIndex++]);
            }
        }
        return values;
    }
    private SudokuUtilities() {
    }

    private static int[][][] randomizeSudoku(int[][][] sudokuValues){
        int timesLeft = (int) (Math.random() * 10 + 1);
        int timesUp = (int) (Math.random() * 8 + 1);
        swapTwoRandomNumbers(sudokuValues);
        do{
            shiftAllColsLeft(sudokuValues);
           timesLeft--;
        }while (timesLeft > 0);
        do{
            shiftAllRowsUp(sudokuValues);
            timesUp--;
        }while (timesUp > 0);

        return sudokuValues;
    }

    /**
     * Swaps the values of two randomly selected numbers in the Sudoku puzzle.
          * This method randomly selects two numbers from 1 to 9 and then swaps their values in the Sudoku puzzle.
     * It ensures that the numbers are not the same and that they are swapped in both boards of the Sudoku puzzle.
     *
     * @param sudokuValues The Sudoku puzzle to be modified.
     * @return The modified Sudoku puzzle.
     */
    private static int[][][] swapTwoRandomNumbers(int[][][] sudokuValues) {

        int firstNumber;
        int secNumber;
        //Two random numbers
        do {
            firstNumber = (int) (Math.random() * 9 + 1);
            secNumber = (int) (Math.random() * 9 + 1);
        } while (firstNumber == secNumber);
        //
        for (int row1 = 0; row1 < GRID_SIZE; row1++) {
            for (int col1 = 0; col1 < GRID_SIZE; col1++) {
                if (firstNumber == sudokuValues[row1][col1][1]) {
                    sudokuValues[row1][col1][1] = secNumber;
                    if(sudokuValues[row1][col1][0] != 0){
                        sudokuValues[row1][col1][0] = secNumber;
                    }
                } else if (secNumber == sudokuValues[row1][col1][1]) {
                    sudokuValues[row1][col1][1] = firstNumber;
                    if(sudokuValues[row1][col1][0] != 0){
                        sudokuValues[row1][col1][0] = firstNumber;
                    }
                }
            }
        }
          System.out.println("swapNumbers Finished!");            //För testning
        return sudokuValues;
    }



    /**
     * Shifts all columns one position to the left in the Sudoku puzzle.
     * This method modifies the Sudoku puzzle by moving all numbers in each column one position to the left.
     * It ensures that the numbers are not overwritten by using temporary arrays to store the values of the rows before shifting them.
     *
     * @param sudokuValues The Sudoku puzzle to be shifted.
     * @return The shifted Sudoku puzzle.
     */
    //man kan göra en kopia av första kolumnen och sedan skriva över alla kolumner åt vänster
    //för att sedan sätta in första kolumnen på den sista, men det kommer då bli mer kod
    private static int[][][] shiftAllColsLeft(int[][][] sudokuValues){
         System.out.println("Shift cols initialized");
        int[] colCopy1 = new int[GRID_SIZE];
        int[] colCopy2 = new int[GRID_SIZE];

        //switch places of col 1-9 to the left
        for(int col = 0; col < GRID_SIZE-1; col++) {
            //Do for each board; index[0] and index[1]
            for (int boardIndex = 0; boardIndex < 2; boardIndex++) {
                //make a copy of 2 cols
                for (int row = 0; row < GRID_SIZE; row++) {
                    colCopy1[row] = sudokuValues[row][col][boardIndex];
                    colCopy2[row] = sudokuValues[row][col+1][boardIndex];
                }
                //switch place of col1 and col2 to the left
                for (int i = 0; i < 9; i++) {
                    sudokuValues[i][col][boardIndex] = colCopy2[i];
                    sudokuValues[i][col+1][boardIndex] = colCopy1[i];//Todo: skriv ut sudokuValues[i][col+1][boardIndex] blir col+1= col[8]?
                }
            }
        }
        System.out.println("Shift Cols Done");
        return sudokuValues;
    }

    /**
     * Shifts all rows one position upwards in the Sudoku puzzle.
     *
     * This method modifies the Sudoku puzzle by moving all rows one position upwards.
     * It ensures that the rows are not overwritten by using temporary arrays to store the values of the rows before shifting them.
     *
     * @param sudokuValues The Sudoku puzzle to be shifted.
     * @return The shifted Sudoku puzzle.
     */
    private static int[][][] shiftAllRowsUp(int[][][] sudokuValues){
        System.out.println("Shift rows initialized");
        int[] rowCopy = new int[GRID_SIZE];
        int[] rowCopy2 = new int[GRID_SIZE];
            //switch places of row 1-9 from the bottom up
        for(int row1 = 0; row1 < GRID_SIZE-1; row1++) {
            //For each board (index[0] and index[1])
            for (int boardIndex = 0; boardIndex < 2; boardIndex++) {
                //copy row and row+1
                for (int col = 0; col < GRID_SIZE; col++) {
                    rowCopy[col] = sudokuValues[row1][col][boardIndex];
                    rowCopy2[col] = sudokuValues[row1+1][col][boardIndex];
                }
                //swap places of row and row+1
                for (int i = 0; i < 9; i++) {
                    sudokuValues[row1][i][boardIndex] = rowCopy2[i];
                    sudokuValues[row1+1][i][boardIndex] = rowCopy[i];
                }
            }
        }
        System.out.println("Shift Rows Done");
        return sudokuValues;
    }
    private static int convertCharToSudokuInt(char ch) {
        if (ch < '0' || ch > '9') throw new IllegalArgumentException("character " + ch);
        return ch - '0';
    }


    /** Help method
     * Returns the current value at the specified position (x, y, z) on the Sudoku board.
     *
     */
    public int getCurrentValue(int[][][] sudokuValues, int x, int y,int z) {
        return sudokuValues[x][y][z];
    }

    /**
     * Prints the Sudoku puzzle to the console.
     *
     * This method takes a Sudoku puzzle represented as a 3D array and the number of boards to display, and prints the puzzle to the console.
     *
     * @param sudokuValues The Sudoku puzzle to be printed.
     * @param numberOfBoardsToDisplay The number of boards to display.
     */
    public static void printAllValues(int[][][] sudokuValues, int numberOfBoardsToDisplay){
        int boardIndex = 0;
        // System.out.println(Arrays.deepToString(inGameBord));

        for(int z = 0; z < numberOfBoardsToDisplay; z++ ){
            System.out.println();
            System.out.println("Board index: " + boardIndex);
            for (int i = 0; i < 9; i++) {
                System.out.print("|");
                for(int j = 0; j < 9; j++) {
                    if( j % 9 == 0){
                        System.out.println();
                    }
                    System.out.print("|");
                    System.out.print(sudokuValues[i][j][z]);
                }
            }
            boardIndex += 1;
        }
        System.out.println("|");
    }

    private static final String easy =
                    "000914070" +
                    "010000054" +
                    "040002000" +
                    "007569001" +
                    "401000500" +
                    "300100000" +
                    "039000408" +
                    "650800030" +
                    "000403260" +
//            "000000000" + //30
//                    "000000000" +
//                    "000000000" +
//                    "000000000" +
//                    "000000000" +
//                    "000000000" +
//                    "000000000" +
//                    "000000000" +
//                    "000000000" + //solution values after this substring
                    "583914672" +
                    "712386954" +
                    "946752183" +
                    "827569341" +
                    "461238597" +
                    "395147826" +
                    "239675418" +
                    "654821739" +
                    "178493265";

    private static final String medium =
                    "300000010" +
                    "000050906" +
                    "050401200" +
                    "030000080" +
                    "002069400" +
                    "000000002" +
                    "900610000" +
                    "200300058" +
                    "100800090" +
//            "000000000" +
//                    "000000000" +
//                    "000000000" +
//                    "000000000" +
//                    "000000000" +
//                    "000000000" +
//                    "000000000" +
//                    "000000000" +
//                    "000000000" +
                    "324976815" +
                    "718253946" +
                    "659481273" +
                    "536142789" +
                    "872569431" +
                    "491738562" +
                    "985617324" +
                    "267394158" +
                    "143825697";

    private static final String hard =
                    "030600000" +
                    "000010070" +
                    "080000000" +
                    "000020000" +
                    "340000800" +
                    "500030094" +
                    "000400000" +
                    "150800200" +
                    "700006050" +
//            "000000000" +
//                    "000000000" +
//                    "000000000" +
//                    "000000000" +
//                    "000000000" +
//                    "000000000" +
//                    "000000000" +
//                    "000000000" +
//                    "000000000" +
                    "931687542" +
                    "465219378" +
                    "287345916" +
                    "876924135" +
                    "349561827" +
                    "512738694" +
                    "693452781" +
                    "154873269" +
                    "728196453";
}
