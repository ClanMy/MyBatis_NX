package stage.main;

import javafx.application.Application;
import javafx.stage.Stage;
import stage.main.point.PointStage;


public class Mains extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

       new Register().register();

    }
}
