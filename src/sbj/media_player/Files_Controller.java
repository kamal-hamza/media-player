package sbj.media_player;

import java.io.File;

import javafx.fxml.FXML;
import javafx.stage.DirectoryChooser;

public class Files_Controller {

    @FXML
    protected Library_Controller lib = new Library_Controller();

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

    @FXML
    protected void play() {
       lib.play();
    }

}
