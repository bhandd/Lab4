package grupp.lab4;

public class Controller {

    private GridView view;
    private Bord bord;

    public Controller(GridView view, Bord bord) {
        this.view = view;
        this.bord = bord;
    }

    public void MouseEvent(char button, int x, int y) {
        if (button == 'C') {
            bord.removeCurrentValue(x,y);
            view.updateTile(x,y);
        } else if (bord.getCurrentValue(x,y)==0) {
            bord.handleButtonOfChoice(button,x,y);
            view.updateTile(x,y);
        }
    }

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

    public void EventCheckGame() {

    }

    public void EventHint() {

    }

    public void eventRestartGame(Bord newbord) {
        this.bord = newbord;
    }

    public void EventLoadGame() {

    }

    public void EventSaveGame() {

    }
}
