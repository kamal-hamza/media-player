/*

Author: Multiple
Creation Date: 4/10/2024

Music_Player: Music player built for CSS 360 final project
Features:	Play, Stop, Pause, Skip, Repeat songs


*/

package Music_Player_Main;

import java.awt.Label;
import java.io.File;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;

public class Music_Player {

    @FXML
    private Label nowPlaying;
    private Button openFile;
    private MediaPlayer MP;

    public Music_Player(Media media) {
		// TODO Auto-generated constructor stub
	}

	@FXML
    void openFile(MouseEvent event) {
    	FileChooser selectMusic = new FileChooser();
    	selectMusic.setTitle("Select *.mp3 files.");
    	
    	
    	// Selects multiple files, sorts them alphabetically, needs a data structure for library
    	// Maybe a library class?
    	/*
    	List<File> files = selectMusic.showOpenMultipleDialog(null); // multiple files
    	if (files!=null) {
    		// Add data structure for library
    	}
    	*/
    	File file = selectMusic.showOpenDialog(null); // Single File
    	if (file != null) {
    		String singleFile = file.toURI().toString();
    		Media media = new Media(singleFile);
    		MP = new MediaPlayer(media);
        	MP.setOnReady(() -> {
        		nowPlaying.setText("Playing: " + file.getName());
        	});
    	}
    	
    }

    @FXML
    void pauseMusic(MouseEvent event) {
    	MP.pause();
    }

    @FXML
    void playMusic(MouseEvent event) {
    	MP.play();
    }

    @FXML
    void skipBack(MouseEvent event) {

    }

    @FXML
    void skipForward(MouseEvent event) {

    }

    @FXML
    void stopMusic(MouseEvent event) {
    	MP.stop();
    }

    @FXML
    void toggleRepeat(MouseEvent event) {

    }

}
