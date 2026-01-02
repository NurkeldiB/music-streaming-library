import java.util.Objects;

public class Song extends MediaItem {

    private Artist artist;

    public Song(String title, Artist artist, int duration) {
        super(title, duration);
        this.artist = artist;
    }

    public Artist getArtist() {
        return artist;
    }


    public String getInfo() {
        return title + " - " + artist.getName();
    }


    public String toString() {
        return "Song{title='" + title +
                "', artist='" + artist.getName() +
                "', duration=" + duration + " sec}";
    }


    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Song)) return false;
        Song song = (Song) o;
        return Objects.equals(title, song.title) &&
                Objects.equals(artist.getName(), song.artist.getName());
    }


    public int hashCode() {
        return Objects.hash(title, artist.getName());
    }
}
