/*

Author: Multiple
Creation Date: 4/10/2024

Music_Player: Music player built for CSS 360 final project
Features:	Play, Stop, Pause, Skip, Repeat songs


*/

package sbj.media_player;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import javafx.fxml.FXML;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
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
        composerLabel.setText(composer);
        genreLabel.setText(genre);
        artistLabel.setText(artist);
        albumLabel.setText(album);
        titleLabel.setText(title);
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
    	File currentFile = selectMusic.showOpenDialog(null); // Single File
    	if (currentFile != null) {
    		String singleFile = currentFile.toURI().toString();
    		Media media = new Media(singleFile);
    		MP = new MediaPlayer(media);
            // Getting metadata for mp3 file
            try {
                InputStream input = new FileInputStream(currentFile);
                ContentHandler handler = new BodyContentHandler();
                Metadata metadata = new Metadata();
                ParseContext parseCtx = new ParseContext();
                Mp3Parser mp3Parser = new Mp3Parser();
                mp3Parser.parse(input, handler, metadata, parseCtx);
                input.close();
                this.title = metadata.get("title");
                this.artist = metadata.get("xmpDM:artist");
                this.album = metadata.get("xmpDM:album");
                this.genre = metadata.get("xmpDM:genre");
                this.composer = metadata.get("xmpDM:composer");
            } catch (IOException | SAXException | TikaException e) {
                System.out.println("Error");
            }
            // Ends here
        	MP.setOnReady(() -> {
        		currentlyPlaying.setText("Currently Playing: " + currentFile.getName());
        	});
            MP.setAutoPlay(true);
            // bindVolumeSliderToMediaPlayer();
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