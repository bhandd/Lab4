package grupp.lab4.view;

import grupp.lab4.model.Bord;
import grupp.lab4.model.SudokuUtilities;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class GridView extends BorderPane {
    private Label[][] numberTiles; // the tiles/squares to show in the ui grid
    private Canvas canvas; //TODO:behövs?
    private TilePane numberPane;
    private MenuBar menubar;
    private Bord bord;
    private Controller controller;
    private char buttonCheck;

    FileChooser fileChooser = new FileChooser(); //TODO:Check if this is right

    /**
     * The Constructor creates a bord, menubar, the tiles an butten choices.
     * And the intaials input is set to zero.
     */
    //TODO: GridView ska ta emot en Bord bord?
    public GridView() {
        this.bord = new Bord(SudokuUtilities.SudokuLevel.EASY);

        buttonCheck = '0';
        numberTiles = new Label[SudokuUtilities.GRID_SIZE][SudokuUtilities.GRID_SIZE];
        initNumberTiles();

        numberPane = makeNumberPane();
        controller = new Controller(this, bord);
        this.setCenter(numberPane);
        this.setLeft(left());
        this.setRight(right());
        this.setBottom(bottom());
        creatMenu();
    }

    /**
     *
     */
    private EventHandler<MouseEvent> tileCLickHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            for (int row = 0; row < SudokuUtilities.GRID_SIZE; row++) {
                for (int col = 0; col < SudokuUtilities.GRID_SIZE; col++) {
                    if (event.getSource() == numberTiles[row][col]) {
                        // we got the row and column - now call the appropriate controller method, e.g.
                        controller.MouseEvent(buttonCheck, row, col);
                        FullBord();
                        return;
                    }
                }
            }
        }
    };

    /**
     * A method to get a reference to the numberpane
     *
     * @return numberPane
     */
    // use this method to get a reference to the number (called by some other class)
    public TilePane getNumberPane() {
        return numberPane;
    }

    /**
     * Create the view tiles to the game, with what height, width and font they will have.
     */
    // called by constructor (only)
    private final void initNumberTiles() {
        Font font = Font.font("Monospaced", FontWeight.NORMAL, 20);

        for (int row = 0; row < SudokuUtilities.GRID_SIZE; row++) {
            for (int col = 0; col < SudokuUtilities.GRID_SIZE; col++) {
                Label tile = new Label(""/* add number, or "", to display */); // data from model
                if (!bord.getSquareInfoByPos(row, col).isLocked()) {
                    tile = new Label(Integer.toString(bord.getCurrentValue(row, col)));
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
     * Makes the tiles of the border (that the number rest in)
     *
     * @return Tilepane
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
                section.setStyle("-fx-border-color: black; -fx-border-width: 0.5px;");

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
     * Creates two buttones of the left side of window/box
     *
     * @return Left side boxes
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
        check.addEventHandler(ActionEvent.ACTION, checkHandler);
        return l1;
    }

    /**
     * Creates every number of choices and delete option on right side
     *
     * @return Right side boxes
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
        l2.getChildren().addAll(one, two, three, four, five, six, seven, eight, nine, clear);
        l2.setPadding(new Insets(10));
        l2.setSpacing(1);
        this.setRight(l2);

        return l2;
    }

    /**
     * Creates the bottom inset
     * @return
     */
    public VBox bottom() {
        VBox b1 = new VBox();

        b1.setAlignment(Pos.CENTER);
        b1.setPadding(new Insets(10));
        b1.setSpacing(10);
        this.setBottom(b1);
        return b1;
    }

    /**
     * Creates the menubar and all objects on top of the window
     */
    public void creatMenu() {
        Menu file = new Menu("File");
        MenuItem loadGameItem = new MenuItem("Load Game");
        loadGameItem.addEventHandler(ActionEvent.ACTION, eventLoadGameHandler); //Add load game eventhandler
        MenuItem saveGameItem = new MenuItem("Save Game");
        saveGameItem.addEventHandler(ActionEvent.ACTION, eventSaveGameHandler); //Add save game eventhandler
        MenuItem exitGame = new MenuItem("Exit");
        exitGame.addEventHandler(ActionEvent.ACTION, eventExitHandler);
        file.getItems().addAll(loadGameItem, saveGameItem, exitGame);

        Menu gameMenu = new Menu("Game");
        MenuItem restartGame = new MenuItem("Restart");
        restartGame.addEventHandler(ActionEvent.ACTION, restartHandler);
        MenuItem gameLevel = new MenuItem("Choose Level");
        gameLevel.addEventHandler(ActionEvent.ACTION, levelHandler);
        gameMenu.getItems().addAll(restartGame, gameLevel);

        Menu helpMenu = new Menu("Help");
        MenuItem checkGame = new MenuItem("Check");
        checkGame.addEventHandler(ActionEvent.ACTION, checkHandler); //Add check gmae stat eventhandler
        MenuItem clerGame = new MenuItem("Clear");
        clerGame.addEventHandler(ActionEvent.ACTION, clearHandler); //Add clear stat eventhandler
        MenuItem gameRules = new MenuItem("Game Rulse");
        gameRules.addEventHandler(ActionEvent.ACTION, rulesHandler); //Add Game Rulse eventhandler
        helpMenu.getItems().addAll(checkGame, clerGame, gameRules);

        menubar = new MenuBar();
        menubar.getMenus().addAll(file, gameMenu, helpMenu);
    }

    /**
     * Returns menubar
     *
     * @return menubar
     */
    public MenuBar getMenu() {
        return this.menubar;
    }

    /**
     * Handles exit option in menubar
     */
    private EventHandler<ActionEvent> eventExitHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            System.exit(0);
        }
    };

    //TODO: ta bort
    public void initializeDirectory(URL location, ResourceBundle resources) {

        //  fileChooser.setInitialDirectory(new File("C:\\temp"));

    }

    /**
     * handle for save game-button
     * actionEvent The event object representing the button press.
     */

    private EventHandler<ActionEvent> eventSaveGameHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
                controller.saveGame();
        }
    };

    /**
     * Handle for Loadgame
     */

    private EventHandler<ActionEvent> eventLoadGameHandler = new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent actionEvent) {
                bord = controller.loadGame();
            if (bord != null) {
                controller.updateBord(numberTiles);
            }

        }
    };

    /**
     * Event for button press
     */
    private EventHandler<ActionEvent> buttonHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            buttonCheck = controller.PressedButton(actionEvent.getSource());
        }
    };

    /**
     * Update a single tile on the bord
     *
     * @param row row on the board
     * @param col col on the board
     */
    public void updateTile(int row, int col) {
        numberTiles[row][col].setText("");
        if (bord.getCurrentValue(row, col) == 0) {
            numberTiles[row][col].setText("");
        } else {
            numberTiles[row][col].setText(Integer.toString(bord.getCurrentValue(row, col)));
        }
    }

    /**
     * Update the complet board
     */
    public void updateBord() {
        for (int row = 0; row < SudokuUtilities.GRID_SIZE; row++) {
            for (int col = 0; col < SudokuUtilities.GRID_SIZE; col++) {
                updateTile(row, col);
            }
        }
    }

    /**
     * Handler for restart of game
     */
    private EventHandler<ActionEvent> restartHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            bord = new Bord(bord.getTheDiffiulty());
            controller.restartGame(bord);
            controller.updateBord(numberTiles);
        }
    };

    /**
     * Handler for clearing the board
     */
    public EventHandler<ActionEvent> clearHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            controller.EventClearGame();
            controller.updateBord(numberTiles);
        }
    };

    /**
     * Gives you four choose, three where you can start a new game
     * with a diffrent level or a cancel an go back to game.
     * This choose is made with an alert
     */
    public EventHandler<ActionEvent> levelHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            ButtonType easy = new ButtonType("Easy");
            ButtonType medium = new ButtonType("Medium");
            ButtonType hard = new ButtonType("Hard");

            alert.getButtonTypes().setAll(easy, medium, hard);
            alert.setTitle("Difficulty");
            alert.setHeaderText(null);
            alert.setContentText("Choose the difficulty");
            Optional<ButtonType> choice = alert.showAndWait();
            if (choice.get() == easy) {
                bord = new Bord(SudokuUtilities.SudokuLevel.EASY);
                controller.restartGame(bord);
                controller.updateBord(numberTiles);
            } else if (choice.get() == medium) {
                bord = new Bord(SudokuUtilities.SudokuLevel.MEDIUM);
                controller.restartGame(bord);
                controller.updateBord(numberTiles);
            } else if (choice.get() == hard) {
                bord = new Bord(SudokuUtilities.SudokuLevel.HARD);
                controller.restartGame(bord);
                controller.updateBord(numberTiles);
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
            if (controller.EventCheckGame()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Check");
                alert.setHeaderText(null);
                alert.setContentText("All tiles are in the right place");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Check");
                alert.setHeaderText(null);
                alert.setContentText("Some tiles are not correct");
                alert.showAndWait();
            }
        }
    };

    /**
     * Handler for hint of number
     */

    public EventHandler<ActionEvent> hintHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            controller.eventHint();
            controller.updateBord(numberTiles);
            FullBord();
        }
    };

    /**
     * Checks if the Sudoku board is complete and displays a message if it is.
     *
     * bord The Sudoku board to check.
     */
    public void FullBord() {
        if (bord.checkPlaced() == 81) {
            if (controller.EventCheckGame()) {
                ButtonType newGame = new ButtonType("New game");
                ButtonType quit = new ButtonType("Quit");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.getButtonTypes().setAll(newGame, quit);
                alert.setTitle("Completed game");
                alert.setHeaderText("All tiles are in the right place :)");
                alert.setContentText("Do you want to quit and start a new game");
                Optional<ButtonType> choice = alert.showAndWait();
                if (choice.get() == newGame) {
                    ActionEvent actionEvent = new ActionEvent();
                    levelHandler.handle(actionEvent);
                } else {
                    System.exit(0);
                }
            } else {
                ButtonType continu = new ButtonType("Continue");
                ButtonType newGame = new ButtonType("New game");
                ButtonType quit = new ButtonType("Quit");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.getButtonTypes().setAll(continu,newGame,quit);
                alert.setTitle("Incompleted game");
                alert.setHeaderText("Not all tiles are in the correckt place");
                alert.setContentText("Do you want to Continue, start a new game or quit");
                Optional<ButtonType> choice = alert.showAndWait();
                if (choice.get() == newGame) {
                    ActionEvent actionEvent = new ActionEvent();
                    levelHandler.handle(actionEvent);
                } else if (choice.get() == quit) { //TODO kanske lägga till ett val att spara
                    controller.saveGame();
                    System.exit(0);
                }
            }
        }
    }
}


