package sbj.media_player;

import java.io.File;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeItem.TreeModificationEvent;
import javafx.scene.control.TreeView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

public class Library {
    @FXML
    private TreeView<String> treeView;
    private TreeItem<String> root;
    private 

    @FXML
    private void initialize() {
        root = new TreeItem<>("Songs");
        treeView.setRoot(root);
        root.setExpanded(true);
        
    }

    // Add folder containing multiple files
    @FXML
    protected void selectFolder() {
        DirectoryChooser dir = new DirectoryChooser();
        File selectedDir = dir.showDialog(null);
        if (selectedDir != null) {
            File[] files = selectedDir.listFiles((d, name) -> name.endsWith(".mp3"));
            if (files != null) {
                // TODO: check to see if song is in the treeview
                for (File file : files) {
                    System.out.println(file);
                    TreeItem<String> item = new TreeItem<>(file.getName());
                    root.getChildren().add(item);
                    treeView.fireEvent(new TreeModificationEvent<>(TreeItem.valueChangedEvent(), item));
                }
            }
        }
    }

    // add single file
    @FXML
    protected void selectFile() {
        FileChooser file = new FileChooser();
        File song = file.showOpenDialog(null);
        if (song != null) {
            // TODO: check to see if song is in the treeview
            TreeItem<String> item = new TreeItem<>(song.getName());
            root.getChildren().add(item);
            treeView.fireEvent(new TreeModificationEvent<>(TreeItem.valueChangedEvent(), item));
        }
    }

    @FXML
    protected void selectMusic() {
        ObservableList<TreeItem<String>> items = treeView.getSelectionModel().getSelectedItems();
        addToPlaylist(items);
    }

    protected void addToPlaylist(ObservableList<TreeItem<String>> items) {

    }

}  
