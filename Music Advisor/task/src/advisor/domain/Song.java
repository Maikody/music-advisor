package advisor.domain;

import advisor.domain.Album;

import java.util.List;

public class Song {
    private final String name;
    private final List<String> artists;
    private final Album albumName;

    public Song(String name, List<String> artists, Album albumName) {
        this.name = name;
        this.artists = artists;
        this.albumName = albumName;
    }

    public String getName() {
        return name;
    }

    public List<String> getArtists() {
        return artists;
    }

    public Album getAlbumName() {
        return albumName;
    }
}
