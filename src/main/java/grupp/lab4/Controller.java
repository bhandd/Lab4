package grupp.lab4;

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
     *
     * @param button
     * @param x
     * @param y
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

    public void EventClearGame() {
        for (int i = 0; i < SudokuUtilities.GRID_SIZE; i++) {
            for(int j = 0; j < SudokuUtilities.GRID_SIZE; j++) {
                bord.removeCurrentValue(i,j);
            }
        }
    }

    /**
     *
     */
    public void EventCheckGame() {

    }

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
            RowToPlaceHint = (int) (Math.random()*9)+0;
            ColToPlaceHint = (int) (Math.random()*9)+0;
            if(bord.getCurrentValue(RowToPlaceHint,ColToPlaceHint)==0) {
                hintValue = bord.Hint(RowToPlaceHint,ColToPlaceHint);
            }
        }
        bord.setSquareValue(hintValue,RowToPlaceHint,ColToPlaceHint);
    }

    /**
     *
     *
     * @param newbord
     */
    public void eventRestartGame(Bord newbord) {
        this.bord = newbord;
    }

    /**
     *
     */
    public void EventLoadGame() {

    }

    /**
     *
     */
    public void EventSaveGame() {

    }
}
