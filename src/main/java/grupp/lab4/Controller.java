package grupp.lab4;

import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Controller {

    private GridView view;
    private Bord bord;
    private SudokuUtilities logic;

    /**
     * @param view
     * @param bord
     */
    public Controller(GridView view, Bord bord) {
        this.view = view;
        this.bord = bord;
    }

    /**
     * Takes button of choice ("1-9" or "C") and updateTile
     *
     * @param button button of choice
     * @param x      x position on bord
     * @param y      y position on bord
     */
    public void MouseEvent(char button, int x, int y) {
        if (button == 'C') {
            bord.removeCurrentValue(x, y);
            view.updateTile(x, y);
        } else if (bord.getCurrentValue(x, y) == 0) {
            bord.handleButtonOfChoice(button, x, y);
            view.updateTile(x, y);
        }
    }

    /**
     * @param button
     * @return
     */
    public char PressedButton(Object button) {
        return button.toString().charAt(button.toString().length() - 2);
    }

    /**
     * Clears bord when clear event is called
     */
    public void EventClearGame() {
        for (int i = 0; i < SudokuUtilities.GRID_SIZE; i++) {
            for (int j = 0; j < SudokuUtilities.GRID_SIZE; j++) {
                bord.removeCurrentValue(i, j);
            }
        }
    }

    /**
     * Check numbers of board
     */
    public boolean EventCheckGame() {
        if (bord.handleCheckGame() == bord.checkPlaced()) {
            System.out.println(bord.checkPlaced());
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     */
    //TODO: flyttat till SudokuLogic för MVC. Kolla med Noah innan utkommenterad kod tas bort
    public void EventHint() {
     //  logic.getHint(bord); //Todo: test att flytta rutinen till sudokuutilities
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
       // return bord;//TODO: delete
    }


    //TODO: borde den inte uppdatera view? kanske behöver föra över vissa rutiner från gridView till controller?
    /**
     * Call new bord for restart of game
     *
     * @param newbord
     */
    public void eventRestartGame(Bord newbord) {
        bord = newbord;
    }

    /**
     * Handles the event of clicking the "Save Game" button.
     *
     * Opens a file chooser dialog, allows the user to select a file, and then saves
     * the current Sudoku game data to the selected file.
     */
    public void EventSaveGame() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save my files");
        FileChooser.ExtensionFilter ex1 = new FileChooser.ExtensionFilter("Sudoku", "*.sud");
        fileChooser.getExtensionFilters().addAll(ex1);
        fileChooser.setInitialDirectory(new File("/C:/temp"));

        //TODO: kontrollera exceptions
        try {
            File selectedFile = fileChooser.showSaveDialog(view.getScene().getWindow()); //TODO: här väljs vilket fönster som skall visa filechooser
            if (selectedFile != null) {
                SudokuFileIO.serializeToFile(selectedFile, bord);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    /**
     * Loads a Sudoku game from a file.
     *
     * Opens a file chooser dialog, allows the user to select a Sudoku save file, and then de-serializes
     * the Sudoku game data from the selected file.
     * @return The deserialized Sudoku game data.
     */
    public Bord eventLoadGame() {
        File selectedFile = null;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open my files");
        FileChooser.ExtensionFilter ex1 = new FileChooser.ExtensionFilter("Sudoku", "*.sud");
        fileChooser.getExtensionFilters().addAll(ex1);
        fileChooser.setInitialDirectory(new File("/C:/temp"));

        try {
            selectedFile = fileChooser.showOpenDialog(this.view.getScene().getWindow());
            //   if(selectedFile != null) {
            this.bord = SudokuFileIO.deSerializeFromFile(selectedFile);
            return this.bord;
            //   }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}
