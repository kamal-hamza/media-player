package sbj.media_player;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeItem.TreeModificationEvent;
import javafx.scene.control.TreeView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

public class Library {
    @FXML
    private TreeView<File> treeView;
    private TreeItem<File> root;
    private Set<File> songSet;
    private Playlist playlist;
    

    @FXML
    private void initialize() {
        root = new TreeItem<>();
        songSet = new HashSet<>();
        treeView.setRoot(root);
        root.setExpanded(true);
        treeView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        treeView.setShowRoot(false);
        playlist = Playlist.getInstance();
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
                    if (!songSet.contains(file)) {
                        songSet.add(file);
                        System.out.println(file);
                        TreeItem<File> item = new TreeItem<>(file);
                        root.getChildren().add(item);
                        treeView.fireEvent(new TreeModificationEvent<>(TreeItem.valueChangedEvent(), item));
                    }
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
            if (!songSet.contains(song)) {
                TreeItem<File> item = new TreeItem<>(song);
                root.getChildren().add(item);
                treeView.fireEvent(new TreeModificationEvent<>(TreeItem.valueChangedEvent(), item));
            }
        }
    }

    @FXML
    protected void selectMusic() {
        ObservableList<TreeItem<File>> items = treeView.getSelectionModel().getSelectedItems();
        addToPlaylist(items);
    }

    @FXML
    protected void addToPlaylist(ObservableList<TreeItem<File>> items) {
        for (TreeItem<File> item : items) {
            File song = item.getValue();
            playlist.add(song);
        }
        System.out.println(playlist.toString());
    }

}  