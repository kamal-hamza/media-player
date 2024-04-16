/*

Author: Multiple
Creation Date: 4/10/2024

Music_Player: Music player built for CSS 360 final project
Features:	Play, Stop, Pause, Skip, Repeat songs


*/

package sbj.media_player;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.util.Duration;

public class Music_Player {
    @FXML
    private Label buttomStatusText;

    @FXML
    private Label currentlyPlaying;

    @FXML
    private MediaPlayer MP;

    @FXML
    protected void onPlayButtonClick() {
        MP.play();
        buttomStatusText.setText("Test Button Functionality - Play");
    }

    @FXML
    protected void onStopButtonClick() {
        MP.stop();
        buttomStatusText.setText("Test Button Functionality - Stop");
    }

    @FXML
    protected void onPauseButtonClick() {
        if (MP.getStatus() == MediaPlayer.Status.PLAYING){
            MP.pause();
        } else {
            MP.play();
        }
        buttomStatusText.setText("Test Button Functionality - Pause");
    }

    @FXML
    protected void onBackButtonClick() {
        buttomStatusText.setText("Test Button Functionality - Back");
    }

    @FXML
    protected void onForwardButtonClick() {
        buttomStatusText.setText("Test Button Functionality - Forward");
    }

    @FXML
    protected void onRepeatButtonClick() {
        if(MP.getOnEndOfMedia() == null) {
            MP.setOnEndOfMedia(new Runnable() {
                public void run() {
                    MP.seek(Duration.millis(0));
                }
            });
        } else {
            MP.setOnEndOfMedia(null);
        }
        buttomStatusText.setText("Test Button Functionality - Repeat");
    }

    @FXML
    protected void onOpenButtonClick() {
        //currentlyPlaying.setText("Currently Playing: ");
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
        		currentlyPlaying.setText("Currently Playing: " + file.getName());
        	});
            MP.setAutoPlay(true);
    	}
    }
}