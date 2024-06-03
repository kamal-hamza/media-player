package sbj.media_player;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.util.concurrent.TimeUnit;

import javafx.stage.FileChooser;
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
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Rectangle;
import javafx.stage.DirectoryChooser;
import javafx.util.Duration;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Music_Player {
    @FXML
    private Playlist playlist;

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

    @FXML
    private ImageView repeatImageView;

    private final Image repeatIcon = new Image("file:src/sbj/media_player/Assets/repeat_icon.png");
    private final Image repeatFalseIcon = new Image("file:src/sbj/media_player/Assets/repeat_false_icon.png");

    @FXML
    private ImageView shuffleImageView;

    private final Image shuffleIcon = new Image("file:src/sbj/media_player/Assets/shuffle_icon.png");
    private final Image shuffleFalseIcon = new Image("file:src/sbj/media_player/Assets/shuffle_false_icon.png");

    private double volume = 50.0;

    public Music_Player() {
        // playlist = Playlist.getInstance();
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
            if (Playlist.getInstance().getPlaylist().getItems() == null) {
                System.out.println("Library is empty");
                return;
            }
            if (currentTrackIndex >= Playlist.getInstance().getPlaylist().getItems().size()) {
                System.out.println("Reached end of library");
                currentTrackIndex = 0;
                Playlist.getInstance().setCurrentTrackIndex(currentTrackIndex);
                return;
            }

            if (repeat) {
                playLibrary(Playlist.getInstance().getPlaylist());
            }
            else {
                playLibrary(Playlist.getInstance().getPlaylist());
            }

        }
    }

    private void playLibrary(ListView<File> lib) {
        System.out.println("playing");
        File file = lib.getItems().get(currentTrackIndex);
        currentTrackIndex = lib.getItems().indexOf(file);
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
        togglePlayPauseImageView.setImage(pauseIcon);
        MP.play();
        System.out.println("Playing: " + media.getSource());
        MP.setOnEndOfMedia(() -> {
            double tempVolume = MP.getVolume();
            MP.dispose();
            if (!repeat) {
                currentTrackIndex++;
            }
            if (currentTrackIndex < lib.getItems().size()) {
                Playlist.getInstance().setCurrentTrackIndex(currentTrackIndex);
                viewInfo();
                togglePlayPause();
                MP.setVolume(volumeSlider.getValue() / 100);
            }
        });
    }
    
    // @FXML
    // protected void openFile() {
    //     playlist.addFile();
    // }

    @FXML
    protected void toggleRepeat() {
        repeat = !repeat;
        System.out.println(repeat);
        repeatImageView.setImage(repeat ? repeatIcon : repeatFalseIcon);
        if (MP != null && MP.getStatus() == MediaPlayer.Status.PLAYING) {
            MP.setVolume (volume / 100);
        }
    }

    @FXML
    protected void toggleShuffle() {
        shuffle = !shuffle;
        System.out.println(shuffle);
        shuffleImageView.setImage( shuffle ? shuffleIcon : shuffleFalseIcon);
    }

    @FXML
    protected void back() {
        if (MP != null) {
            if (currentTrackIndex - 1 >= 0) {
                currentTrackIndex--;
                Playlist.getInstance().setCurrentTrackIndex(currentTrackIndex);
                MP.dispose();
                viewInfo();
                togglePlayPause();
                MP.setVolume(volumeSlider.getValue() / 100);
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
            if (currentTrackIndex + 1 <= Playlist.getInstance().getPlaylist().getItems().size() - 1) {
                currentTrackIndex++;
                Playlist.getInstance().setCurrentTrackIndex(currentTrackIndex);
                MP.dispose();
                viewInfo();
                togglePlayPause();
                MP.setVolume(volumeSlider.getValue() / 100);
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
        MP.stop();
    }

    protected void viewInfo() {
        System.out.println(currentTrackIndex);
        File file = Playlist.getInstance().getPlaylist().getItems().get(Playlist.getInstance().getCurrentTrackIndex());
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
    @FXML
    protected void runTests() {
        System.out.println("Beginning Testing");
        this.loadTest();
        this.playTest();
        this.durationTest();
        //assert
    }

    private void loadTest() {
        File test = new File("./src/sbj/media_player/Assets/TestSiren.mp3");
        Playlist.getInstance().add(test);
        playLibrary(Playlist.getInstance().getPlaylist());

        String fileCheck = MP.getMedia().getSource().equals("./src/sbj/media_player/Assets/TestSiren.mp3") ? "Passed": "Failed";

        String albumCheck = album.equals("TestAlbum") ? "Passed": "Failed";
        String genreCheck = genre.equals("JUnitTesting") ? "Passed": "Failed";
        String artistCheck = artist.equals("Tester") ? "Passed": "Failed";

        System.out.println("===== Load Test =====");
        System.out.println("TEST: fileCheck " + fileCheck);
        System.out.println("TEST: albumCheck " + albumCheck);
        System.out.println("TEST: genreCheck " + genreCheck);
        System.out.println("TEST: artistCheck " + artistCheck);

        //System.out.println(test);
        //System.out.println(MP.getMedia().getSource());
    }

    private void playTest() {
        // By the time this test is reached, the system has not started playing yet, but the audio plays to the tester
        String statusCheck = (MP.getStatus() == MediaPlayer.Status.PLAYING) ? "Passed": "Failed";
        System.out.println("===== Play Test =====");
        System.out.println("TEST: statusCheck " + statusCheck);
    }

    private void durationTest() {
        // By the time this test is reached, the system has not started playing yet, but the audio plays to the tester
        Duration testdura = new Duration(0);
        int check = testdura.compareTo(MP.getCurrentTime());
        String durationCheck = (check > 0) ? "Passed": "Failed";
        System.out.println("===== Duration Test =====");
        System.out.println("TEST: durationCheck " + durationCheck + " - Test Value: " + String.valueOf(check));
    }
}

