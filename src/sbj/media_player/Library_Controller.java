package sbj.media_player;

import java.util.List;
import java.util.ArrayList;
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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

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

    public Library_Controller() {
        library = new ArrayList<File>();
        currentTrackIndex = 0;
        repeat = false;
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
    
            File file = library.get(currentTrackIndex);
            String singleFile = file.toURI().toString();
            Media media = new Media(singleFile);
            MP = new MediaPlayer(media);
            MP.play();
            System.out.println("Playing: " + media.getSource());
    
            MP.setOnEndOfMedia(() -> {
                MP.dispose();
                if (repeat) {
                    play();
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
    protected void repeat() {
        repeat = !repeat;
        System.out.println(repeat);
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
        // Ends here
    }
}
