package grupp.lab4;

import java.io.Serializable;
import java.util.Random;

public class SudokuUtilities implements Serializable {

    public enum SudokuLevel {EASY, MEDIUM, HARD}

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

        return randomize(representationInt, level);
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
     * Randomiserar vilka värden som skall visas
     *
     * @param sudokuValues
     */
    //TODO: Fixa extra funktioner, kontroll att den inte lämnar en 3x3 utan siffror etc
    private static int[][][] randomize(int[][][] sudokuValues, SudokuLevel level) {
        Random random = new Random();
        double amount = 0;
        int count = 0;
        switch (level) {
            case EASY:
                amount = 0.3658519827543145;
                break;
            case MEDIUM:
                amount = 0.2665851982754314;
                break;
            case HARD:
                amount = 0.255851982754314;
                break;
            default:
                amount = 0.2665851982754314;
        }
//        while (count<amount) {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (sudokuValues[row][col][0] == 0) {
                    double probability = random.nextDouble();
                    //  System.out.println("probability: "+ probability); //TODO: Delete
                    if (probability <= amount) {
                        sudokuValues[row][col][0] = sudokuValues[row][col][1];
                        count++;
                    }
                }
            }
//                if(count==amount) {
//                    break;
//                }
        }
//        }
        return sudokuValues;
    }


    /**
     * Byter plats på två random nummer
     *
     * @param sudokuValues
     * @return
     */
    //TODO: Behövs? isåfall fixa så den funkar
    private static int[][][] swapNumbers(int[][][] sudokuValues) {
        System.out.println("randomIzeSudoku Initialized");               //Test
        int[][] position1;
        int[][] position2;
        int firstNumber;
        int secNumber;
        int counterOne = 0;
        int counterTwo = 0;
        do {
            firstNumber = (int) (Math.random() * 9 + 1);
            secNumber = (int) (Math.random() * 9 + 1);
        } while (firstNumber == secNumber);

        System.out.println("First number: " + firstNumber);               //Test
        System.out.println("Second number: " + secNumber);               //Test

        for (int row1 = 0; row1 < GRID_SIZE; row1++) {
            for (int col1 = 0; col1 < GRID_SIZE; col1++) {
                if (firstNumber == sudokuValues[row1][col1][1]) {

                }
            }
        }


        System.out.println("randomIzeSudoku Finished!");
        return sudokuValues;

    }

    private static int convertCharToSudokuInt(char ch) {
        if (ch < '0' || ch > '9') throw new IllegalArgumentException("character " + ch);
        return ch - '0';
    }

    private static final String easy =
//                    "000914070" +
//                    "010000054" +
//                    "040002000" +
//                    "007569001" +
//                    "401000500" +
//                    "300100000" +
//                    "039000408" +
//                    "650800030" +
//                    "000403260" +
            "000000000" + //30
                    "000000000" +
                    "000000000" +
                    "000000000" +
                    "000000000" +
                    "000000000" +
                    "000000000" +
                    "000000000" +
                    "000000000" + //solution values after this substring
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
//                    "300000010" +
//                    "000050906" +
//                    "050401200" +
//                    "030000080" +
//                    "002069400" +
//                    "000000002" +
//                    "900610000" +
//                    "200300058" +
//                    "100800090" +
            "000000000" +
                    "000000000" +
                    "000000000" +
                    "000000000" +
                    "000000000" +
                    "000000000" +
                    "000000000" +
                    "000000000" +
                    "000000000" +
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
