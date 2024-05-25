package sbj.media_player;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
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

import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Rectangle;
import javafx.stage.DirectoryChooser;
import javafx.util.Duration;

public class Music_Player {
    @FXML
    private Library library;

    @FXML
    private List<File> shuffledLibrary;

    @FXML
    private MediaPlayer MP;

    @FXML
    private int currentTrackIndex;

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
    private double duration;

    @FXML
    private boolean repeat;

    @FXML
    private Button repeatButton;

    @FXML
    private boolean shuffle;

    @FXML
    private Button shuffleButton;

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
    private Label durationLabel;

    @FXML
    private Label durationLabel2;

    @FXML
    private Slider volumeSlider;

    @FXML
    private Slider musicSlider;

    @FXML
    private Button togglePlayPauseButton;

    @FXML
    private ImageView togglePlayPauseImageView;

    private final Image playIcon = new Image("file:src/sbj/media_player/Assets/play_icon.png");
    private final Image pauseIcon = new Image("file:src/sbj/media_player/Assets/pause_icon.png");


    private double volume = 50.0;

    public Music_Player() {
        library = new Library();
        currentTrackIndex = 0;
        repeat = false;
        shuffle = false;
    }

    @FXML
    protected void togglePlayPause() {
        if (MP != null && MP.getStatus() == MediaPlayer.Status.PLAYING) {
            MP.pause();
            togglePlayPauseImageView.setImage(playIcon);
        } else if (MP != null && (MP.getStatus() == MediaPlayer.Status.PAUSED || MP.getStatus() == MediaPlayer.Status.STOPPED)) {
            MP.play();
            togglePlayPauseImageView.setImage(pauseIcon);
        } else {
            if (library.musicList.isEmpty()) {
                System.out.println("Library is empty");
                return;
            }
            if (currentTrackIndex >= library.musicList.size()) {
                System.out.println("Reached end of library");
                currentTrackIndex = 0;
                library.setCurrentTrackIndex(currentTrackIndex);
                return;
            }

            if (repeat) {
                playLibrary(library.musicList);
            } else {
                playLibrary(library.musicList);
            }
        }
    }

    private void playLibrary(List<File> lib) {
        System.out.println("playing");
        File file = lib.get(currentTrackIndex);
        currentTrackIndex = lib.indexOf(file);
        viewInfo();
        try {
            InputStream input = new FileInputStream(file);
            ContentHandler handler = new BodyContentHandler();
            Metadata metadata = new Metadata();
            ParseContext parseCtx = new ParseContext();
            Mp3Parser mp3Parser = new Mp3Parser();
            mp3Parser.parse(input, handler, metadata, parseCtx);
            input.close();
            String dur = metadata.get("xmpDM:duration");
            duration = Double.parseDouble(dur);
            System.out.println(dur);
            int minutes = (int) (duration / 60);
            int seconds = (int) (duration % 60);
            String songDuration = String.format("%d:%02d", minutes, seconds);
            durationLabel2.setText(songDuration);
        } catch (IOException | SAXException | TikaException e) {
            System.out.println("Error");
        }
        String singleFile = file.toURI().toString();
        Media media = new Media(singleFile);
        MP = new MediaPlayer(media);
        initializeVolumeSlider();
        initializeMusicSlider();
        System.out.println("MP initialize");
        MP.play();
        togglePlayPauseImageView.setImage(pauseIcon);
        System.out.println("Playing: " + media.getSource());
        MP.setOnEndOfMedia(() -> {
            MP.dispose();
            if (repeat) {
                if (currentTrackIndex == lib.size() - 1) {
                    currentTrackIndex = 0;
                }
            } else {
                currentTrackIndex++;
            }
            library.setCurrentTrackIndex(currentTrackIndex);
            viewInfo();
            if (repeat || currentTrackIndex < lib.size()) {
            togglePlayPause();
            }
        });
    }
    
    @FXML
    protected void openFile() {
        library.addFile();
    }

    @FXML
    protected void toggleRepeat() {
        repeat = !repeat;
        System.out.println(repeat);
        repeatButton.setText(repeat ? "Repeat:ON" : "Repeat:OFF");
        if (MP != null && MP.getStatus() == MediaPlayer.Status.PLAYING) {
            MP.setVolume (volume / 100);
        }
    }

    @FXML
    protected void toggleShuffle() {
        shuffle = !shuffle;
        System.out.println(shuffle);
        shuffleButton.setText(shuffle ? "Shuffle: ON" : "Shuffle: OFF");
    }

    @FXML
    protected void back() {
        if (MP != null) {
            if (currentTrackIndex - 1 >= 0) {
                currentTrackIndex--;
                library.setCurrentTrackIndex(currentTrackIndex);
                MP.dispose();
                viewInfo();
                togglePlayPause();
            }
            else {
                System.out.println("Cant go back");
            }
        }
        else {
            System.out.println("Nothing is Playing");
        }
    }

    @FXML
    protected void forward() {
        if (MP != null) {
            if (currentTrackIndex + 1 <= library.musicList.size() - 1) {
                currentTrackIndex++;
                library.setCurrentTrackIndex(currentTrackIndex);
                MP.dispose();
                viewInfo();
                togglePlayPause();
            }
            else {
                System.out.println("Cant go forward");
            }
        }
        else {
            System.out.println("Nothing is Playing");
        }
    }

    @FXML
    protected void stop() {
        if (MP != null)
        {
            MP.stop();
            togglePlayPauseImageView.setImage(playIcon);
        }
        else
        {
            System.out.println("Nothing is Playing");
        }
    }

    protected void viewInfo() {
        System.out.println(currentTrackIndex);
        File file = library.musicList.get(library.getCurrentTrackIndex());
        System.out.println(currentTrackIndex);
        // Getting metadata for mp3 file
        try {
            InputStream input = new FileInputStream(file);
            ContentHandler handler = new BodyContentHandler();
            Metadata metadata = new Metadata();
            ParseContext parseCtx = new ParseContext();
            Mp3Parser mp3Parser = new Mp3Parser();
            mp3Parser.parse(input, handler, metadata, parseCtx);
            input.close();
            title = metadata.get("title");
            artist = metadata.get("xmpDM:artist");
            album = metadata.get("xmpDM:album");
            genre = metadata.get("xmpDM:genre");
            composer = metadata.get("xmpDM:composer");
        } catch (IOException | SAXException | TikaException e) {
            System.out.println("Error");
        }
        titleLabel.setText((title != null ? title : "Unknown"));
        artistLabel.setText((artist != null ? artist : "Unknown"));
        albumLabel.setText((album != null ? album : "Unknown"));
        genreLabel.setText((genre != null ? genre : "Unknown"));
        composerLabel.setText((composer != null ? composer : "Unknown"));
        // Ends here
    }

    @FXML
    protected void initializeVolumeSlider() {
        System.out.println("initialize called");
        if (MP != null) {
            volumeSlider.setValue(volume);
            System.out.println("initializing");
            volumeSlider.valueChangingProperty().addListener((observable, wasChanging, isChanging) -> {
                System.out.println(volumeSlider.getValue());
                volume = volumeSlider.getValue();
                MP.setVolume(volumeSlider.getValue() / 100);
            });
            volumeSlider.setOnMouseClicked(event -> {
                double value = (event.getX() / volumeSlider.getWidth()) * volumeSlider.getMax();
                volumeSlider.setValue(value);
                volume = value;
                MP.setVolume(value / 100);
            });
        }
        else {
            System.out.println("No Media is Playing");
        }
    }

    protected void initializeMusicSlider() {
        if (MP != null) {
            musicSlider.setValue(0.0);
            musicSlider.setMax(duration);
            musicSlider.valueChangingProperty().addListener((observable, wasChanging, isChanging) -> {
                Duration duration = new Duration(musicSlider.getValue() * 1000);
                MP.seek(duration);
            });
            musicSlider.setOnMouseClicked(event -> {
                double value = (event.getX() / musicSlider.getWidth()) * musicSlider.getMax();
                musicSlider.setValue(value);
                Duration duration = new Duration(value * 1000);
                System.out.println(duration);
                MP.seek(duration);
            });
            MP.currentTimeProperty().addListener((observable, oldVal, newVal) -> {
                musicSlider.setValue(newVal.toMillis() / 1000);
                double time = newVal.toSeconds();
                int minutes = (int) (time / 60);
                int seconds = (int) (time % 60);
                String songDuration = String.format("%d:%02d", minutes, seconds);
                durationLabel.setText(songDuration);
            });
        }
        else {
            System.out.println("No media is Playing");
        }
    }
}