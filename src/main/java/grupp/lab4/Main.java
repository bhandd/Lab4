package grupp.lab4;

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

        VBox root = new VBox(menuBar, gridView);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.setResizable(false);
        stage.setTitle("Sudoku");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    /*public static void main(String[] args) {
        //launch();
         Bord bord = new Bord();
         bord.setSquareValue(5,3,2);
        System.out.println(bord.toString());

    }*/

}