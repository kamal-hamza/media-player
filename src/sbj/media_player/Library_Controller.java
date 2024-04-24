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

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.DirectoryChooser;

public class Library_Controller {
    @FXML
    private List<File> library;

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
    private boolean repeat;

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
    private boolean shuffle;

    @FXML
    private Slider volumeSlider;

    public Library_Controller() {
        library = new ArrayList<File>();
        currentTrackIndex = 0;
        repeat = false;
        shuffle = false;
    }

    protected void addFile(File file) {
        library.add(file);
    }

    @FXML
    protected void play() {
        if (MP != null && (MP.getStatus() == MediaPlayer.Status.STOPPED | MP.getStatus() == MediaPlayer.Status.PAUSED)) {
            MP.play();
        }
        else {
            if (library.isEmpty()) {
                System.out.println("Library is empty");
                return;
            }
            if (currentTrackIndex >= library.size()) {
                System.out.println("Reached end of library");
                currentTrackIndex = 0;
                return;
            }

            if (shuffle) {
                Collections.shuffle(library);
            }
    
            File file = library.get(currentTrackIndex);
            String singleFile = file.toURI().toString();
            Media media = new Media(singleFile);
            MP = new MediaPlayer(media);
            MP.play();
            System.out.println("Playing: " + media.getSource());
    
            MP.setOnEndOfMedia(() -> {
                MP.dispose();
                if (repeat) {
                    if (currentTrackIndex == library.size() - 1) {
                        currentTrackIndex = 0;
                        play();
                    }
                } else {
                    currentTrackIndex++;
                    play();
                }
            });
        }
    }


    @FXML
    protected void pause() {
        MP.pause();
    }

    @FXML
    protected void toggleRepeat() {
        repeat = !repeat;
        System.out.println(repeat);
    }

    @FXML
    protected void toggleShuffle() {
        shuffle = !shuffle;
        System.out.println(shuffle);
    }

    @FXML
    protected void back() {
        if (MP != null) {
            if (currentTrackIndex - 1 >= 0) {
                currentTrackIndex--;
                MP.dispose();
                play();
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
            if (currentTrackIndex + 1 <= library.size() - 1) {
                currentTrackIndex++;
                MP.dispose();
                play();
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

    @FXML
    protected void viewInfo() {
        File file = library.get(currentTrackIndex);
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
        titleLabel.setText(title);
        artistLabel.setText(artist);
        albumLabel.setText(album);
        genreLabel.setText(genre);
        composerLabel.setText(composer);
        // Ends here
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
                    addFile(file);
                }
            }
        }
    }

    @FXML
    protected void initialize() {
        volumeSlider.setValue(100.0);
        System.out.println("initializing");
        volumeSlider.valueChangingProperty().addListener((observable, wasChanging, isChanging) -> {
            System.out.println(volumeSlider.getValue());
            MP.setVolume(volumeSlider.getValue() / 100);
        });
        volumeSlider.setOnMouseClicked(event -> {
            double value = (event.getX() / volumeSlider.getWidth()) * volumeSlider.getMax();
            volumeSlider.setValue(value);
            MP.setVolume(value / 100);
        });
    }
}
