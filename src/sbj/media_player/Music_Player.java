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
import javafx.scene.control.Slider;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import javax.swing.*;

public class Music_Player {
    @FXML
    private String composer;

    @FXML
    private String genre;

    @FXML
    private String artist;

    @FXML
    private String album;

    @FXML
    private String title;

    @FXML
    private Label composerLabel;

    @FXML
    private Label genreLabel;

    @FXML
    private Label artistLabel;

    @FXML
    private Label albumLabel;

    @FXML
    private Label titleLabel;

    @FXML
    private File currentFile;
    
    @FXML
    private Label buttomStatusText;

    @FXML
    private Label currentlyPlaying;

    @FXML
    private Label playerStatus;

    @FXML
    private MediaPlayer MP;

    @FXML
    protected Library_Controller lib = new Library_Controller();

    @FXML
    protected void onStatusButtonClick() {
        if (MP != null) {
            // This function is for debugging purposes only but could be converted into a status display if desired
            playerStatus.setText("Media Player Status: " + MP.getStatus().toString());
        }
        else {
            playerStatus.setText("Media Player Status: No media file is selected");
        }
    }

    // Creating a new function to display the metadata for mp3 files, wil implement a panel for displaying info later
    @FXML
    protected void onInfoButtonClick() {
        
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
        DirectoryChooser directory = new DirectoryChooser();
        directory.setTitle("Select Folder with mp3 files");
        File currentDirectory = directory.showDialog(null);
        if (currentDirectory != null) {
            // Get the list of files inside the folder
            File[] files = currentDirectory.listFiles();
            // Add all mp3 files to the library
            for (File file : files) {
                if (file.getName().endsWith(".mp3")) {
                    System.out.println(file);
                    lib.addFile(file);
                }
            }
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

    //Implementation for the volume scroll bar but has bugs (volume mutes 
    // when slider is dragged all the way to the left, 
    // but does not appear to increase or decrease volume) will fix later

  /*  @FXML
    protected void bindVolumeSliderToMediaPlayer() {
        volumeSlider.valueProperty().bindBidirectional(MP.volumeProperty());
    }
   
    @FXML
    protected void onVolumeScrollDrag() {
        volumeSlider.valueProperty().addListener((ov, oldValue, newValue) -> {
               if (volumeSlider.isValueChanging()) {
                   MP.setVolume(volumeSlider.getValue());
               }
        });
    } */ 

}