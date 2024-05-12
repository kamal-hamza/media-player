package sbj.media_player;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.fxml.FXML;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

public class Library {
    public List<File> musicList;

    private int currentTrackIndex;

    public Library() {
        musicList = new ArrayList<>();
    }

    @FXML
    protected void onOpenButtonClick() {
        
    }

    @FXML
    protected void addFile() {
        FileChooser file = new FileChooser();
        file.setTitle("Select Folder with mp3 files");
        File currentFile = file.showOpenDialog(null);
        if (currentFile != null) {
            musicList.add(currentFile);
        }
        System.out.println(musicList);
    }

    private void removeFile(int index) {
        musicList.remove(index);
    }

    public int getCurrentTrackIndex() {
        return this.currentTrackIndex;
    }

    public void setCurrentTrackIndex(int index) {
        this.currentTrackIndex = index;
    }

    public List<File> getMusicLibrary() {
        return this.musicList;
    }
}
