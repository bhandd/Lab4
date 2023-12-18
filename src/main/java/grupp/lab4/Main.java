package grupp.lab4;

import grupp.lab4.view.GridView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        GridView gridView = new GridView();
        MenuBar menuBar = gridView.getMenu();

        VBox root = new VBox(menuBar, gridView); //pane VBox kallad root

        Scene scene = new Scene(root);  //Lägger in pane root i scene
        stage.setScene(scene);
        stage.sizeToScene();
        stage.setResizable(false);
        stage.setTitle("Sudoku");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}