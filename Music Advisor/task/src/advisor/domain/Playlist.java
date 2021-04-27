package advisor.domain;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
    private String name;
    private Category category;
    private List<Song> playlistSongs = new ArrayList<>();

    public Playlist(String name, Category category) {
        this.name = name;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Song> getPlaylistSongs() {
        return playlistSongs;
    }

    public void setPlaylistSongs(List<Song> playlistSongs) {
        this.playlistSongs = playlistSongs;
    }

    public void addSong(Song song) {
        playlistSongs.add(song);
    }

    public void removeSong(Song song) {
        playlistSongs.remove(song);
    }
}
