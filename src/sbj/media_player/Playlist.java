package sbj.media_player;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Playlist {
    
    private static Playlist instance;
    private List<File> playlist;
    private Set<File> songSet;
    private int currentTrackIndex;

    public Playlist() {
        playlist = new ArrayList<File>();
        songSet = new HashSet<>();
    }

    protected void add(File file) {
        if (!songSet.contains(file)) {
            songSet.add(file);
            playlist.add(file);
        }
    }
    
    public String toString() {
        String s = "[";
        for (File file : playlist) {
            s += file + ", ";
        }
        s += "]";
        return s;
    }

    protected List<File> getPlaylist() {
        return this.playlist;
    }

    protected int getCurrentTrackIndex() {
        return this.currentTrackIndex;
    }

    protected void setCurrentTrackIndex(int index) {
        this.currentTrackIndex = index;
    }

    protected void encrementTrackIndex() {
        this.currentTrackIndex++;
    }

    protected void decrementTrackindex() {
        this.currentTrackIndex--;
    }

    protected static Playlist getInstance() {
        if (instance == null) {
            instance = new Playlist();
        }
        return instance;
    }
}
