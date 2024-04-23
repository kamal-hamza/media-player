package sbj.media_player;

import java.util.List;
import java.util.ArrayList;
import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Library_Controller {
    @FXML
    private List<Media> library;

    @FXML
    private MediaPlayer MP;

    @FXML
    private int currentTrackIndex;

    public Library_Controller() {
        library = new ArrayList<Media>();
        currentTrackIndex = 0;
    }

    protected void addFile(File file) {
        String singleFile = file.toURI().toString();
        Media media = new Media(singleFile);
        library.add(media);
    }

    protected void play() {

    }
}
