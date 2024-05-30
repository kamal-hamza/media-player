package com.example.demo;

import java.io.File;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

public class Playlist {

    @FXML
    private ListView<File> playlistView;

    // private ObservableList<File> files;
    private static Playlist instance;
    private int currentTrackIndex;
    private Set<File> songSet;
    private ObservableList<File> obsPlayList = FXCollections.observableArrayList();

    public Playlist() {
        songSet = new HashSet<>();
        System.out.print(songSet.toString());
        // files = FXCollections.observableArrayList();
        // playlistView = new ListView<>(obsPlayList);
    }

    @FXML
    private void initialize() {
        playlistView.setItems(obsPlayList);
        instance = this;
    }

    protected void add(File file) {
        if (!songSet.contains(file)) {
            songSet.add(file);
            obsPlayList.add(file);
            System.out.println(printToString());
        }
    }

    public String printToString() {
        String s = "[";
        for (File file : playlistView.getItems()) {
            s += file + ", ";
        }
        s += "]";
        return s;
    }

    private void playlistViewRefresh() {
        playlistView.setItems(obsPlayList);
    }

    protected ListView<File> getPlaylist() {
        return this.playlistView;
    }

    protected int getCurrentTrackIndex() {
        return this.currentTrackIndex;
    }

    protected void setCurrentTrackIndex(int index) {
        this.currentTrackIndex = index;
    }

    protected static Playlist getInstance() {
        // if (instance == null) {
        //     instance = new Playlist();
        // }
        return instance;
    }
}
