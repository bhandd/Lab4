package grupp.lab4;

import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Controller {

    private GridView view;
    private Bord bord;

    /**
     *
     * @param view
     * @param bord
     */
    public Controller(GridView view, Bord bord) {
        this.view = view;
        this.bord = bord;
    }

    /**
     * Takes button of choice ("1-9" or "C") and updateTile
     * @param button button of choice
     * @param x x position on bord
     * @param y y position on bord
     */
    public void MouseEvent(char button, int x, int y) {
        if (button == 'C') {
            bord.removeCurrentValue(x,y);
            view.updateTile(x,y);
        } else if (bord.getCurrentValue(x,y)==0) {
            bord.handleButtonOfChoice(button,x,y);
            view.updateTile(x,y);
        }
    }

    /**
     *
     * @param button
     * @return
     */
    public char PressedButton(Object button) {
        return button.toString().charAt(button.toString().length()-2);
    }

    /**
     * Clears bord when clear event is called
     */
    public void EventClearGame() {
        for (int i = 0; i < SudokuUtilities.GRID_SIZE; i++) {
            for(int j = 0; j < SudokuUtilities.GRID_SIZE; j++) {
                bord.removeCurrentValue(i,j);
            }
        }
    }

    /**
     * Check numbers of board
     */
    public boolean EventCheckGame() {
        if(bord.handleCheckGame() == bord.checkPlaced()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     */
    //TODO: Detta bör vara i antingen gridview eller sudokulogic för att uppnå MVC
    public void EventHint() {
        int amountof = 0;
        for(int row = 0; row < SudokuUtilities.GRID_SIZE; row++) {
            for(int col = 0; col < SudokuUtilities.GRID_SIZE; col++) {
                if(bord.getCurrentValue(row,col)!= 0) {
                    amountof++;
                }
            }
        }

        if (amountof==81) {
                throw new IllegalArgumentException("Bord is filled");
        }

        int RowToPlaceHint = 0;
        int ColToPlaceHint = 0;
        int hintValue = 0;
        while (hintValue == 0) { //look for place to place hint
            RowToPlaceHint = (int) (Math.random() * 9);
            ColToPlaceHint = (int) (Math.random() * 9);
            if(bord.getCurrentValue(RowToPlaceHint,ColToPlaceHint)==0) {
                hintValue = bord.Hint(RowToPlaceHint,ColToPlaceHint);
            }
        }
        bord.setSquareValue(hintValue,RowToPlaceHint,ColToPlaceHint);
    }

    /**
     * Call new bord for restart of game
     * @param newbord
     */
    public void eventRestartGame(Bord newbord) {
        bord = newbord;
    }


    /**
     * Load game from file
     */
    public void EventLoadGame() {

    }

    /**
     * Saves game in file
     */
    public void EventSaveGame() {
        FileChooser fileChooser = new FileChooser();
        File file = null;
        fileChooser.setTitle("Save Game");
        fileChooser.setInitialFileName("sudokuSave");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("bytecode", "*.sud"));

      //  serializeToFile(file, bord);

    }


    //TODO: flytta/ta bort, för test
//    public static void serializeToFile(File file, Bord sudokuBord) throws IOException {
//
//        ObjectOutputStream out = null;
//        try{
//            out = new ObjectOutputStream(new FileOutputStream(file));
//            out.writeObject(sudokuBord);
//        }
//        finally {
//            if(out != null){
//                out.close();
//                // ...
//                // and then, make sure the file always get closed
//            }
//        }
//    }

}
