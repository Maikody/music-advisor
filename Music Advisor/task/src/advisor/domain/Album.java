package advisor.domain;

import java.util.ArrayList;
import java.util.List;

public class Album {

    private final String albumName;
    private final List<String> artists = new ArrayList<>();

    public Album(String albumName, List<String> artists) {
        this.albumName = albumName;
        this.artists.addAll(artists);
    }

    public String getAlbumName() {
        return albumName;
    }

    public List<String> getArtists() {
        return artists;
    }
}