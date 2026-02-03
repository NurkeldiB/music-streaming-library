import java.util.Objects;

public class Song extends MediaItem {

    private Long id; // для БД
    private Artist artist;

    public Song(String title, Artist artist, int duration) {
        this(null, title, artist, duration);
    }

    public Song(Long id, String title, Artist artist, int duration) {
        super(title, duration);
        this.id = id;
        setArtist(artist);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) { // обычно ставится после insert в БД
        this.id = id;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        if (artist == null) throw new IllegalArgumentException("artist must not be null");
        this.artist = artist;
    }

    @Override
    public String getInfo() {
        return getTitle() + " - " + artist.getName() + " (" + getDuration() + " sec)";
    }

    @Override
    public String toString() {
        return "Song{id=" + id +
                ", title='" + getTitle() +
                "', artist='" + artist.getName() +
                "', duration=" + getDuration() + " sec}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Song)) return false;
        Song song = (Song) o;
        return Objects.equals(getTitle().toLowerCase(), song.getTitle().toLowerCase())
                && Objects.equals(artist.getName().toLowerCase(), song.artist.getName().toLowerCase());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle().toLowerCase(), artist.getName().toLowerCase());
    }
}