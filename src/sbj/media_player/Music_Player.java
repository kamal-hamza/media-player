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

import javax.swing.*;

public class Music_Player {
    @FXML
    private Label buttomStatusText;

    @FXML
    private Label currentlyPlaying;

    @FXML
    private Label playerStatus;

    @FXML
    private MediaPlayer MP;

    @FXML
    protected void onStatusButtonClick() {
        // This function is for debugging purposes only but could be converted into a status display if desired
        playerStatus.setText("Media Player Status: " + MP.getStatus().toString());
    }

    @FXML
    protected void onPlayButtonClick() {
        // If paused, start playing from where it ended
        // If stopped, start playing from beginning (default behavior)
        // If playing, reset duration to beginning and start playing
        if (MP.getStatus() == MediaPlayer.Status.STOPPED || MP.getStatus() == MediaPlayer.Status.PAUSED){
            MP.play();
        } else {
            MP.seek(Duration.millis(0));
            MP.play();
        }

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
        } else if (MP.getStatus() == MediaPlayer.Status.PAUSED) {
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

    // File Menu Actions
    @FXML
    protected void onCloseMenuClick() {
        // Close app when clicked
        System.exit(0);
    }

    @FXML
    protected void onAboutMenuClick() {
        // Gives an about dialog box which contains the current version
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JOptionPane.showMessageDialog(frame, "Media Player - Version: 0.1.3");
    }
}