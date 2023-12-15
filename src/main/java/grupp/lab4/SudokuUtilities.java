package grupp.lab4;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

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

        //Efter randomizeSudoku

        representationInt = convertStringToIntMatrix(representationString);
        //  return swapNumbers(representationInt);            //Todo: fixa den så den funkar om den behövs
        //representationInt = randomize(representationInt, level);
        //TODO: behövs ej tyvär
        //return randomize(representationInt, level);
       // swapNumbers(representationInt);
     //   printAllValues(representationInt, 2);
     //   randomizeVertically(representationInt);
       // printAllValues(representationInt, 2);

      //  shiftRows(representationInt); //TODO: fungerar inte riktigt
       // shiftSelectedRows(representationInt, 0,1);
        return representationInt;
    }

    //TODO: Test att flytta hint, ta bort om den inte ska vara här. Går att göra static men då förlorar man låg koppling och datainkapsling kan brytas
    public void getHint(Bord bord){
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
     *
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




    /**
     * Byter plats på två random nummer
     *
     * @param sudokuValues
     * @return
     */
    //TODO: Kolla om det finns någon färdig metod för att byta plats på två nummer i en array
    private static int[][][] swapNumbers(int[][][] sudokuValues) {


        int firstNumber;
        int secNumber;

        do {
            firstNumber = (int) (Math.random() * 9 + 1);
            secNumber = (int) (Math.random() * 9 + 1);
        } while (firstNumber == secNumber);
        System.out.println(Arrays.deepToString(sudokuValues));
        System.out.println("First number: " + firstNumber);               //Test
        System.out.println("Second number: " + secNumber);               //Test



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

        System.out.println(Arrays.deepToString(sudokuValues));
        System.out.println("randomIzeSudoku Finished!");
        return sudokuValues;

    }

    /**
     * Byter plats på första och sista raden
     * @param sudokuValues
     * @return
     */
    //TODO: lägg till funktionalitet så att sudokut skiftar rader, Byter just nu plats på första och sista raden, men det räcker inte för att randomisera, den måste kunna skifta
    //     * alla rader upp eller ner
    private static int[][][] randomizeVertically(int[][][] sudokuValues){
        System.out.println("RandomizeVertically initialized");
        System.out.println("Original Sudoku:");
        printAllValues(sudokuValues, 2);

        int[][][] copy = sudokuValues.clone(); // Create a copy of the Sudoku puzzle
        int[] row1 = new int[GRID_SIZE];
        int[] row2 = new int[GRID_SIZE];
        int firstRow = 0;
        int lastRow = 8;

        for (int boardIndex = 0; boardIndex < 2; boardIndex++){
            for(int col = 0; col < GRID_SIZE; col++){
                row1[col] = sudokuValues[firstRow][col][boardIndex];
                row2[col] = sudokuValues[lastRow][col][boardIndex];
            }

        for (int i = 0; i < 9; i++) {

            sudokuValues[firstRow][i][boardIndex] = row2[i];
            sudokuValues[lastRow][i][boardIndex] = row1[i];
        }
            System.out.println("Row 1: " + Arrays.toString(row1));
            System.out.println("Row 2: " + Arrays.toString(row2));
        }


        System.out.println("Horizontally mirrored Sudoku:");
        printAllValues(sudokuValues, 2);
        System.out.println();
        System.out.println("RandomizeVertically Done");
        return sudokuValues;
    }

    private static int[] copyRow(int[][][] sudokuValues, int row, int boardIndex){
        System.out.println("RowCopy initialized!");
        int[][][] copy = sudokuValues.clone(); // Create a copy of the Sudoku puzzle
        int[] rowCopy = new int[GRID_SIZE];

            for (int col = 0; col < GRID_SIZE; col++) {
                rowCopy[col] = sudokuValues[row][col][boardIndex];
            }

        System.out.println("RowCopy: " + Arrays.toString(rowCopy));
        System.out.println("RowCopy DONE!");
            return rowCopy;
    }


    //TODO: Börja Här, metoden anropar andra metoder för att skifta rader, men något är fel
    private static int[][][] shiftRows(int[][][] sudokuValues){
        System.out.println("ShiftRows initialized");
        System.out.println("Original Sudoku:");
        printAllValues(sudokuValues, 2);

        int[][][] copy = sudokuValues.clone(); // Create a copy of the Sudoku puzzle
        int[] boardRow ;
        int[] solutionRow;
        int firstRow = 0;
        int nextRow = 1;
        //copy row 1 and 2
           boardRow = copyRow(sudokuValues, 0,1);
           solutionRow = copyRow(sudokuValues, 0, 0);

        System.out.println("The board row copy: " + Arrays.toString(boardRow));
        System.out.println("The solution row copy: " + Arrays.toString(solutionRow));

            //copy row 1, shift row 2 to row 1, row2 to row3..., place row 1 on row 9
            for(int row = 0; row < 7; row++) {
                boardRow = copyRow(sudokuValues, row, 1);
                solutionRow = copyRow(sudokuValues, row, 0);
                //shiftrows
                shiftSelectedRows(sudokuValues, row, row+1);
                //place row 1 on row 9
            }
        for (int col = 0; col < 9; col++) {
            sudokuValues[8][col][0] = boardRow[col];
            sudokuValues[8][col][1] = solutionRow[col];
        }
            System.out.println("Row 1: " + Arrays.toString(boardRow));
            System.out.println("Row 2: " + Arrays.toString(solutionRow));



        System.out.println("Horizontally mirrored Sudoku:");
        printAllValues(sudokuValues, 2);
        System.out.println();
        System.out.println("RandomizeVertically Done");
        return sudokuValues;

    }

    //TODO: ta bort, fungerar men behövs ej
    private static int[][][] shiftFirstAndLast(int[][][] sudokuValues){
        System.out.println("RandomizeVertically initialized");
        System.out.println("Original Sudoku:");
        printAllValues(sudokuValues, 2);

        int[][][] copy = sudokuValues.clone(); // Create a copy of the Sudoku puzzle
        int[] rowCopy = new int[GRID_SIZE];
        int[] row2 = new int[GRID_SIZE];
        int firstRow = 0;
        int lastRow = 8;

        for (int boardIndex = 0; boardIndex < 2; boardIndex++){
            for(int col = 0; col < GRID_SIZE; col++){
                rowCopy[col] = sudokuValues[firstRow][col][boardIndex];
                row2[col] = sudokuValues[lastRow][col][boardIndex];
            }

            for (int i = 0; i < 9; i++) {

                sudokuValues[firstRow][i][boardIndex] = row2[i];
                sudokuValues[lastRow][i][boardIndex] = rowCopy[i];
            }
            System.out.println("Row 1: " + Arrays.toString(rowCopy));
            System.out.println("Row 2: " + Arrays.toString(row2));
        }


        System.out.println("Horizontally mirrored Sudoku:");
        printAllValues(sudokuValues, 2);
        System.out.println();
        System.out.println("RandomizeVertically Done");
        return sudokuValues;
    }

//TODO: För test eller inte om den funkar
    private static int[][][] shiftSelectedRows(int[][][] sudokuValues, int row1, int row2){
        System.out.println("Shift rows initialized");
        System.out.println("Original Sudoku:");
        printAllValues(sudokuValues, 2);


        for (int boardIndex = 0; boardIndex < 1; boardIndex++){
            for (int i = 0; i < 9; i++) {
                sudokuValues[row1][i][boardIndex] =  sudokuValues[row2][i][boardIndex];

            }
        }


        System.out.println("Shifted Sudoku:");
        printAllValues(sudokuValues, 2);
        System.out.println();
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
     * Help Method
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
//                    "030600000" +
//                    "000010070" +
//                    "080000000" +
//                    "000020000" +
//                    "340000800" +
//                    "500030094" +
//                    "000400000" +
//                    "150800200" +
//                    "700006050" +
            "000000000" +
                    "000000000" +
                    "000000000" +
                    "000000000" +
                    "000000000" +
                    "000000000" +
                    "000000000" +
                    "000000000" +
                    "000000000" +
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
