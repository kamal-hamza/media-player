/*

Author: Multiple
Creation Date: 4/10/2024

Music_Player: Music player built for CSS 360 final project
Features:	Play, Stop, Pause, Skip, Repeat songs


*/
package Music_Player_Main;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Main extends Application {

	@Override
	public void start(Stage userGui) throws Exception {
		URL url = Main.class.getResource("MusicPlayer.fxml");
		FXMLLoader fxmlLoader = new FXMLLoader(url);
		Scene scene = new Scene(fxmlLoader.load());
		userGui.setTitle("Music Player");
		userGui.setScene(scene);
		userGui.show();
	}
	public static void main(String[] args) {launch();}
}