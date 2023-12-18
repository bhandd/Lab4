package grupp.lab4.view;

import grupp.lab4.model.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

public class Controller {
    private final GridView view;
    private Bord bord;

    private char buttonCheck = '0';

    public EventHandler<MouseEvent> tileCLickHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            for (int row = 0; row < SudokuUtilities.GRID_SIZE; row++) {
                for (int col = 0; col < SudokuUtilities.GRID_SIZE; col++) {
                    if (event.getSource() == view.getNumberByPos(row,col)) {
                        // we got the row and column - now call the appropriate controller method, e.g.
                        MouseEvent(buttonCheck, row, col, view.getNumberTiles());
                        view.FullBord();
                        return;
                    }
                }
            }
        }
    };

    public EventHandler<ActionEvent> buttonHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            buttonCheck = PressedButton(actionEvent.getSource());
        }
    };

    public void setBord(Bord bord){
        this.bord = bord;
    }
//original
//    public Controller(GridView view, Bord bord) {
//        this.view = view;
//        this.bord = bord;
//    }

    public Controller(GridView view){
        this.view = view;
        getNewBord();
    }

    public Controller() {
        this.view = new GridView();
        this.bord = new Bord(SudokuLevel.DEFAULT);
    }

    public int getGridSize(){
        return SudokuUtilities.GRID_SIZE;
    }
    public int getSectionsPerRow(){
        return SudokuUtilities.SECTIONS_PER_ROW;
    }

    public Bord getNewBord(){
        bord = new Bord(SudokuLevel.DEFAULT);
        return bord;
    }

    public Bord getNewBordWithDifficulty(SudokuLevel level){
        bord = new Bord(level);
        return bord;
    }

    public Boolean getSqareInfoByPosition(int row, int col){
      return bord.getSquareInfoByPos(row,col).isHidden();
    }

    public int getSqareInfo(int row, int col){
        return bord.getCurrentValue(row, col);
    }

    public GridView getView(){
        return view;
    }

    public Bord getBord(){
        return new Bord();

    }


    public int checkPlaced(){
        return bord.checkPlaced();
    }

    public SudokuLevel getCurrentDifficulty(){
       return bord.getTheDiffiulty();
    }

    /**
     * Takes button of choice ("1-9" or "C") and updateTile
     *
     * @param button button of choice
     * @param x      x position on bord
     * @param y      y position on bord
     */
    public void MouseEvent(char button, int x, int y, Label[][] numberTiles) {
        if (button == 'C') {
            bord.removeCurrentValue(x, y);
            updateTile(numberTiles, x, y);
        } else if (bord.getCurrentValue(x, y) == 0) {
            bord.handleButtonOfChoice(button, x, y);
            updateTile(numberTiles, x, y);
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
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     */
    //TODO: flyttat till SudokuLogic för MVC. Kolla med Noah innan utkommenterad kod tas bort
    public void eventHint() {
     SudokuUtilities.getHint(bord); //Todo: test att flytta rutinen till sudokuutilities
     //view.updateBord();
    }


    //TODO: borde den inte uppdatera view? kanske behöver föra över vissa rutiner från gridView till controller?
    /**
     * Call new bord for restart of game
     *
     * @param newbord
     */
//    public void restartGame(Bord newbord) {
//        bord = getNewBord();
//    }

    /**
     * Handles the event of clicking the "Save Game" button.
     *
     * Opens a file chooser dialog, allows the user to select a file, and then saves
     * the current Sudoku game data to the selected file.
     */
    //Kanske kan vara såhär, för flyttas den till view får man endå kalla på view i controller vilket känns onödigt, samma med loadGame
    public void saveGame() {
        //Ska dessa till view?
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save my files");
        FileChooser.ExtensionFilter ex1 = new FileChooser.ExtensionFilter("Sudoku", "*.sud");
        fileChooser.getExtensionFilters().addAll(ex1);
        fileChooser.setInitialDirectory(new File("/C:/temp"));

        //TODO: kontrollera exceptions, exceptions ska kastas hela vägen upp till där metoden kallades från
        try {
            File selectedFile = fileChooser.showSaveDialog(view.getScene().getWindow()); //TODO: här väljs vilket fönster som skall visa filechooser
            if (selectedFile != null) {
                SudokuFileIO.serializeToFile(selectedFile, bord);
            }

        } catch (IOException e) {//TODO:Alert:"gick inte att läsa in fil"
            throw new RuntimeException(e);
        }catch (NullPointerException e) {//TODO:Alert:"gick inte att läsa in fil"
            System.out.println("Nullpointer Exception hanteras i Controller");
       //     e.printStackTrace();
    }
    }


    /**
     * Loads a Sudoku game from a file.
     * Opens a file chooser dialog, allows the user to select a Sudoku save file, and then de-serializes
     * the Sudoku game data from the selected file.
     * @return The deserialized Sudoku game data.
     */
    public Bord loadGame(){
        File selectedFile = null;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open my files");
        FileChooser.ExtensionFilter ex1 = new FileChooser.ExtensionFilter("Sudoku", "*.sud");
        fileChooser.getExtensionFilters().addAll(ex1);
        fileChooser.setInitialDirectory(new File("/C:/temp"));
        selectedFile = fileChooser.showOpenDialog(this.view.getScene().getWindow());
        try {
            this.bord = SudokuFileIO.deSerializeFromFile(selectedFile);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }catch (NullPointerException e) {
            //TODO: Alertmeddelande
          //  e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }catch (RuntimeException e){
           // e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
           // e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
//        if (this.bord != null){
//            return this.bord;
//        }
        return this.bord;
    }

    public void updateTile(Label[][] numberTiles,int row, int col) {
        numberTiles[row][col].setText("");
        if (bord.getCurrentValue(row, col) == 0) {
            numberTiles[row][col].setText("");
        } else {
            numberTiles[row][col].setText(Integer.toString(bord.getCurrentValue(row, col)));
        }
    }
    public void updateBord(Label[][] numberTiles) {
        for(int row = 0; row < SudokuUtilities.GRID_SIZE; row++) {
            for(int col = 0; col < SudokuUtilities.GRID_SIZE; col++) {
                updateTile(numberTiles,row,col);
            }
        }
    }

    public Square getSquare(int row, int col) {
        return bord.getSquareInfoByPos(row,col);
    }

    public int getCurrentVale(int row, int col) {
        return bord.getCurrentValue(row,col);
    }
}
