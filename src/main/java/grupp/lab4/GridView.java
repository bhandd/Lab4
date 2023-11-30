package grupp.lab4;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.Optional;

public class GridView extends BorderPane { //Check if using BorderPane is the right way to go?
    private Label[][] numberTiles; // the tiles/squares to show in the ui grid
    private TilePane numberPane;
    //private EventHandler<? super MouseEvent> tileClickHandler;
    private MenuBar menubar;
    private Bord bord;
    private Controller controller;
    private char buttonCheck;

    /**
     *
     */
    private EventHandler<MouseEvent> tileCLickHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            for(int row = 0; row < SudokuUtilities.GRID_SIZE; row++) {
                for(int col = 0; col < SudokuUtilities.GRID_SIZE; col++) {
                    if(event.getSource() == numberTiles[row][col]) {
                        // we got the row and column - now call the appropriate controller method, e.g.
                        controller.MouseEvent(buttonCheck,row,col);
                        System.out.println(buttonCheck);
                        return;
                    }
                }
            }
        }
    };

    /**
     *
     */
    public GridView() {
        this.bord = new Bord(SudokuUtilities.SudokuLevel.EASY);
        buttonCheck = '0';
        numberTiles = new Label[SudokuUtilities.GRID_SIZE][SudokuUtilities.GRID_SIZE];
        initNumberTiles();
        numberPane = makeNumberPane();
        controller = new Controller(this , bord);
        this.setCenter(numberPane);
        this.setLeft(left());
        this.setRight(right());
        creatMenu();
    }

    // use this method to get a reference to the number (called by some other class)
    public TilePane getNumberPane() {
        return numberPane;
    }

    // called by constructor (only)
    private final void initNumberTiles() {
        Font font = Font.font("Monospaced", FontWeight.NORMAL, 20);

        for (int row = 0; row < SudokuUtilities.GRID_SIZE; row++) {
            for (int col = 0; col < SudokuUtilities.GRID_SIZE; col++) {
                Label tile = new Label(""/* add number, or "", to display */); // data from model
                if(!bord.getPosOnBordByPos(row,col).isLocked()) {
                    tile = new Label(Integer.toString(bord.getCurrentValue(row,col)));
                }
                tile.setPrefWidth(32);
                tile.setPrefHeight(32);
                tile.setFont(font);
                tile.setAlignment(Pos.CENTER);
                tile.setStyle("-fx-border-color: black; -fx-border-width: 0.5px;"); // css style
                tile.setOnMouseClicked(tileCLickHandler); // add your custom event handler
                // add new tile to grid
                numberTiles[row][col] = tile;
            }
        }
    }

    /**
     *
     * @return
     */
    private final TilePane makeNumberPane() {
        // create the root tile pane
        TilePane root = new TilePane();
        root.setPrefColumns(SudokuUtilities.SECTIONS_PER_ROW);
        root.setPrefRows(SudokuUtilities.SECTIONS_PER_ROW);
        root.setStyle(
                "-fx-border-color: black; -fx-border-width: 1.0px; -fx-background-color: white;");

        // create the 3*3 sections and add the number tiles
        TilePane[][] sections = new TilePane[SudokuUtilities.SECTIONS_PER_ROW][SudokuUtilities.SECTIONS_PER_ROW];
        int i = 0;
        for (int srow = 0; srow < SudokuUtilities.SECTIONS_PER_ROW; srow++) {
            for (int scol = 0; scol < SudokuUtilities.SECTIONS_PER_ROW; scol++) {
                TilePane section = new TilePane();
                section.setPrefColumns(SudokuUtilities.SECTION_SIZE);
                section.setPrefRows(SudokuUtilities.SECTION_SIZE);
                section.setStyle( "-fx-border-color: black; -fx-border-width: 0.5px;");

                // add number tiles to this section
                for (int row = 0; row < SudokuUtilities.SECTION_SIZE; row++) {
                    for (int col = 0; col < SudokuUtilities.SECTION_SIZE; col++) {
                        // calculate which tile and add
                        section.getChildren().add(
                                numberTiles[srow * SudokuUtilities.SECTION_SIZE + row][scol * SudokuUtilities.SECTION_SIZE + col]);
                    }
                }
                // add the section to the root tile pane
                root.getChildren().add(section);
            }
        }

        return root;
    }

    /**
     *
     * @return
     */
    public VBox left() {
        VBox l1 = new VBox();

        Button check = new Button("Check");
        Button hint = new Button("Hint");
        l1.setAlignment(Pos.CENTER);
        l1.getChildren().add(check);
        l1.getChildren().add(hint);
        l1.setPadding(new Insets(10));
        l1.setSpacing(10);
        this.setLeft(l1);
        hint.addEventHandler(ActionEvent.ACTION, hintHandler);
        //check.addEventHandler(ActionEvent.ACTION, EventCheckGame);
        return l1;
    }

    /**
     *
     * @return
     */
    public VBox right() {
        VBox l2 = new VBox();

        l2.setAlignment(Pos.CENTER);
        Button one = new Button("1");
        one.addEventHandler(ActionEvent.ACTION, buttonHandler);
        Button two = new Button("2");
        two.addEventHandler(ActionEvent.ACTION, buttonHandler);
        Button three = new Button("3");
        three.addEventHandler(ActionEvent.ACTION, buttonHandler);
        Button four = new Button("4");
        four.addEventHandler(ActionEvent.ACTION, buttonHandler);
        Button five = new Button("5");
        five.addEventHandler(ActionEvent.ACTION, buttonHandler);
        Button six = new Button("6");
        six.addEventHandler(ActionEvent.ACTION, buttonHandler);
        Button seven = new Button("7");
        seven.addEventHandler(ActionEvent.ACTION, buttonHandler);
        Button eight = new Button("8");
        eight.addEventHandler(ActionEvent.ACTION, buttonHandler);
        Button nine = new Button("9");
        nine.addEventHandler(ActionEvent.ACTION, buttonHandler);
        Button clear = new Button("C");
        clear.addEventHandler(ActionEvent.ACTION, buttonHandler);
        l2.getChildren().addAll(one,two,three,four,five,six,seven,eight,nine,clear);
        l2.setPadding(new Insets(10));
        l2.setSpacing(1);
        this.setRight(l2);

        return l2;
    }

    /**
     *
     */
    public void creatMenu()  {
        Menu file = new Menu("File");
        MenuItem loadGameItem = new MenuItem("Load Game");
        loadGameItem.addEventHandler(ActionEvent.ACTION, eventLoadGameHandler); //Add load game eventhandler
        MenuItem saveGameItem = new MenuItem("Save Game");
        saveGameItem.addEventHandler(ActionEvent.ACTION, eventSaveGameHandler); //Add save game eventhandler
        MenuItem exitGame = new MenuItem("Exit");
        exitGame.addEventHandler(ActionEvent.ACTION, eventExitHandler);
        file.getItems().addAll(loadGameItem,saveGameItem,exitGame);

        Menu gameMenu = new Menu("Game");
        MenuItem restartGame = new MenuItem("Restart");
        restartGame.addEventHandler(ActionEvent.ACTION, restartHandler);
        MenuItem gameLevel = new MenuItem("Choose Level");
        gameLevel.addEventHandler(ActionEvent.ACTION, levelHandler);
        gameMenu.getItems().addAll(restartGame,gameLevel);

        Menu helpMenu = new Menu("Help");
        MenuItem checkGame = new MenuItem("Check/end game");
        checkGame.addEventHandler(ActionEvent.ACTION, eventExitHandler); //Add check gmae stat eventhandler
        MenuItem clerGame = new MenuItem("Clear");
        clerGame.addEventHandler(ActionEvent.ACTION, clearHandler); //Add clear stat eventhandler
        MenuItem gameRules = new MenuItem("Game Rulse");
        gameRules.addEventHandler(ActionEvent.ACTION, rulesHandler); //Add Game Rulse eventhandler
        helpMenu.getItems().addAll(checkGame, clerGame, gameRules);

        menubar = new MenuBar();
        menubar.getMenus().addAll(file, gameMenu, helpMenu);
    }

    /**
     *
     * @return
     */
    public MenuBar getMenu() {
        return this.menubar;
    }


    //TODO:metod för att generera ett nytt spel med random bräde
    public void newGame(){


    }

    /**
     *
     */
    private EventHandler<ActionEvent> eventExitHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            System.exit(0);
        }
    };

    private EventHandler<ActionEvent> eventSaveGameHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("Save");
            alert.setHeaderText("Location: ");
            alert.setContentText("Fixa rutin för att spara spel");
            alert.showAndWait();
        }
//Todo: rutin för spara spel
            };

    private EventHandler<ActionEvent> eventLoadGameHandler = new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent actionEvent) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("Save");
            alert.setHeaderText("Location: ");
            alert.setContentText("Fixa rutin för att ladda spel");
            alert.showAndWait();
        }
//Todo: rutin för ladda spel
    };

    /**
     *
     */
    private EventHandler<ActionEvent> buttonHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            buttonCheck=controller.PressedButton(actionEvent.getSource());
        }
    };

    /**
     *
     * @param row
     * @param col
     */
    public void updateTile(int row, int col) {
        numberTiles[row][col].setText("");
        if(bord.getCurrentValue(row,col) == 0) {
            numberTiles[row][col].setText("");
        }
        else {
            numberTiles[row][col].setText(Integer.toString(bord.getCurrentValue(row,col)));
        }
    }

    /**
     *
     */
    public void updateBord() {
        for(int row = 0; row < SudokuUtilities.GRID_SIZE; row++) {
            for (int col = 0; col < SudokuUtilities.GRID_SIZE; col++) {
                updateTile(row,col);
            }
        }
    }

    /**
     *
     */
    private EventHandler<ActionEvent> restartHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            bord = new Bord(bord.getTheDiffiulty());
            controller.eventRestartGame(bord);
            updateBord();
        }
    };

    /**
     *
     */
    public EventHandler<ActionEvent> clearHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            controller.EventClearGame();
            updateBord();
        }
    };

    /**
     *  Gives you four choose, three where you can start a new game
     *  with a diffrent level or a cancel an go back to game.
     *  This choose is made with an alert
     */
    public EventHandler<ActionEvent> levelHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            ButtonType easy = new ButtonType("Easy");
            ButtonType medium = new ButtonType("Medium");
            ButtonType hard = new ButtonType("Hard");
            ButtonType cancel = new ButtonType("Cancel");
            alert.getButtonTypes().setAll(easy,medium,hard,cancel);
            alert.setTitle("Difficulty");
            alert.setHeaderText(null);
            alert.setContentText("Choose the difficulty");
            Optional<ButtonType> choice = alert.showAndWait();
            if (choice.get() == easy){
                bord = new Bord(SudokuUtilities.SudokuLevel.EASY);
                controller.eventRestartGame(bord);
                updateBord();
            } else if (choice.get() == medium){
                bord = new Bord(SudokuUtilities.SudokuLevel.MEDIUM);
                controller.eventRestartGame(bord);
                updateBord();
            } else if (choice.get() == hard) {
                bord = new Bord(SudokuUtilities.SudokuLevel.HARD);
                controller.eventRestartGame(bord);
                updateBord();
            }
        }
    };

    /**
     * Shows the rulses of a sudoku game in the form of an alert popup
     */
    public EventHandler<ActionEvent> rulesHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("Rulse");
            alert.setHeaderText("Sudoku Rules");
            alert.setContentText("Every square needs contain a number\n" +
                    "The numbers 1-9 can be used\n" +
                    "In each of the 3x3 boxes the numbers 1-9 can only appare once\n" +
                    "The same numbers can only appare once in each row and column\n");
            alert.showAndWait();
        }
    };

    public EventHandler<ActionEvent> checkHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            if(controller.EventCheckGame()) {

            }
        }
    };

    public EventHandler<ActionEvent> hintHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            controller.EventHint();
            updateBord();
        }
    };
}


