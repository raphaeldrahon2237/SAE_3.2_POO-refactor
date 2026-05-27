package towerdef;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import towerdef.controller.GameController;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("game-view.fxml"));
        Scene scene = new Scene(loader.load(), 1184, 704);

        GameController controller = loader.getController();

        stage.setTitle("TowerDef");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(event -> {
            controller.onClose();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
