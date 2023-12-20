package grupp.lab4.view;

import grupp.lab4.model.SudokuLevel;
import grupp.lab4.model.SudokuUtilities;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.util.Optional;


public class GridView extends BorderPane {
    private final Label[][] numberTiles;
    private final TilePane numberPane;
    private MenuBar menubar;
    private final Controller controller;

    /**
     * The Constructor creates menubar, the tiles and button choices.
     * And the intaials input is set to zero.
     */
    public GridView() {
        controller = new Controller(this);

        numberTiles = new Label[controller.getGridSize()][controller.getGridSize()];
        initNumberTiles();

        numberPane = makeNumberPane();

        this.setCenter(numberPane);
        this.setLeft(left());
        this.setRight(right());
        this.setBottom(bottom());
        creatMenu();
    }

    /**
     * Retrieves the 2D array of `Label` objects representing the Sudoku number tiles.
     *
     * @return The 2D array of `Label` objects.
     */
    public Label[][] getNumberTiles() {
        return numberTiles;
    }

    /**
     * Retrieves the `Label` object representing the number tile at the specified position.
     *
     * @param row The row index of the number tile.
     * @param col The column index of the number tile.
     * @return The `Label` object representing the number tile.
     */
    public Label getNumberByPos(int row, int col) {
        return numberTiles[row][col];
    }

    /**
     * Create the view tiles to the game, with what height, width and font they will have.
     */
    // called by constructor (only)
    private void initNumberTiles() {
        Font font = Font.font("Monospaced", FontWeight.NORMAL, 20);

        for (int row = 0; row < SudokuUtilities.GRID_SIZE; row++) {
            for (int col = 0; col < SudokuUtilities.GRID_SIZE; col++) {
                Label tile = new Label(""/* add number, or "", to display */); // data from model
                if (!controller.getSqareInfoByPosition(row,col)) {
                    font = Font.font("Monospaced", FontWeight.BOLD, 20);
                    tile = new Label(Integer.toString(controller.getSqareInfo(row,col)));
                    tile.setFont(font);
                } else {
                    font = Font.font("Monospaced", FontWeight.NORMAL, 20);
                    tile.setFont(font);
                }
                tile.setPrefWidth(32);
                tile.setPrefHeight(32);
                tile.setAlignment(Pos.CENTER);
                tile.setStyle("-fx-border-color: black; -fx-border-width: 0.5px;"); // css style
                tile.setOnMouseClicked(controller.tileCLickHandler); // add your custom event handler
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
    final TilePane makeNumberPane() {
        // create the root tile pane
        TilePane root = new TilePane();
        root.setPrefColumns(SudokuUtilities.SECTIONS_PER_ROW);
        root.setPrefRows(SudokuUtilities.SECTIONS_PER_ROW);
        root.setStyle("-fx-border-color: black; -fx-border-width: 1.0px; -fx-background-color: white;");

        // create the 3*3 sections and add the number tiles
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
        hint.addEventHandler(ActionEvent.ACTION, controller.hintHandler);
        check.addEventHandler(ActionEvent.ACTION, controller.checkHandler);
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
        one.addEventHandler(ActionEvent.ACTION, controller.buttonHandler);
        Button two = new Button("2");
        two.addEventHandler(ActionEvent.ACTION, controller.buttonHandler);
        Button three = new Button("3");
        three.addEventHandler(ActionEvent.ACTION, controller.buttonHandler);
        Button four = new Button("4");
        four.addEventHandler(ActionEvent.ACTION, controller.buttonHandler);
        Button five = new Button("5");
        five.addEventHandler(ActionEvent.ACTION, controller.buttonHandler);
        Button six = new Button("6");
        six.addEventHandler(ActionEvent.ACTION, controller.buttonHandler);
        Button seven = new Button("7");
        seven.addEventHandler(ActionEvent.ACTION, controller.buttonHandler);
        Button eight = new Button("8");
        eight.addEventHandler(ActionEvent.ACTION, controller.buttonHandler);
        Button nine = new Button("9");
        nine.addEventHandler(ActionEvent.ACTION, controller.buttonHandler);
        Button clear = new Button("C");
        clear.addEventHandler(ActionEvent.ACTION, controller.buttonHandler);
        l2.getChildren().addAll(one, two, three, four, five, six, seven, eight, nine, clear);
        l2.setPadding(new Insets(10));
        l2.setSpacing(1);
        this.setRight(l2);
        return l2;
    }

    /**
     * Creates and configures a vertical box (`VBox`) to be placed at the bottom of the main layout.
     *
     * @return The configured `VBox` object.
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
        loadGameItem.addEventHandler(ActionEvent.ACTION, controller.eventLoadGameHandler);
        MenuItem saveGameItem = new MenuItem("Save Game");
        saveGameItem.addEventHandler(ActionEvent.ACTION, controller.eventSaveGameHandler);
        MenuItem exitGame = new MenuItem("Exit");
        exitGame.addEventHandler(ActionEvent.ACTION, controller.eventExitHandler);
        file.getItems().addAll(loadGameItem, saveGameItem, exitGame);

        Menu gameMenu = new Menu("Game");
        MenuItem restartGame = new MenuItem("Restart");
        restartGame.addEventHandler(ActionEvent.ACTION, controller.restartHandler);
        MenuItem gameLevel = new MenuItem("Choose Level");
        gameLevel.addEventHandler(ActionEvent.ACTION, controller.levelHandler);
        gameMenu.getItems().addAll(restartGame, gameLevel);

        Menu helpMenu = new Menu("Help");
        MenuItem checkGame = new MenuItem("Check");
        checkGame.addEventHandler(ActionEvent.ACTION, controller.checkHandler);
        MenuItem clerGame = new MenuItem("Clear");
        clerGame.addEventHandler(ActionEvent.ACTION, controller.clearHandler);
        MenuItem gameRules = new MenuItem("Game Rulse");
        gameRules.addEventHandler(ActionEvent.ACTION, controller.rulesHandler);
        helpMenu.getItems().addAll(checkGame, clerGame, gameRules);

        menubar = new MenuBar();
        menubar.getMenus().addAll(file, gameMenu, helpMenu);
    }

    /**
     * Retrieves the `MenuBar` object associated with this class or object.
     *
     * @return The `MenuBar` object associated with this class or object.
     */
    public MenuBar getMenu() {
        return this.menubar;
    }

    /**
     * Checks if the Sudoku board is complete and displays a message if it is.
     * bord The Sudoku board to check.
     */
    public void FullBord() {
        if (controller.checkPlaced() == 81) {
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
                    controller.levelHandler.handle(actionEvent);
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
                    controller.levelHandler.handle(actionEvent);
                } else if (choice.get() == quit) {
                    controller.saveGame();
                    System.exit(0);
                }
            }
        }
    }
}


