/*

Author: Multiple
Creation Date: 4/10/2024

Music_Player: Music player built for CSS 360 final project
Features:	Play, Stop, Pause, Skip, Repeat songs


*/
package sbj.media_player;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

// Checking github

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Root.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Media Player");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}