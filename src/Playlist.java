import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Playlist {

    private String name;
    private final ArrayList<Song> songs = new ArrayList<>();

    public Playlist(String name) {
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("playlist name must not be blank");
        this.name = name.trim();
    }

    public List<Song> getSongs() {
        return List.copyOf(songs); // инкапсуляция: наружу не отдаём изменяемую коллекцию
    }

    public void addSong(Song song) {
        if (song == null) throw new IllegalArgumentException("song must not be null");
        songs.add(song);
    }

    public List<Song> searchByTitle(String keyword) {
        if (keyword == null) keyword = "";
        String k = keyword.toLowerCase();
        ArrayList<Song> result = new ArrayList<>();
        for (Song s : songs) {
            if (s.getTitle().toLowerCase().contains(k)) result.add(s);
        }
        return result;
    }

    public List<Song> filterByArtist(String artistName) {
        if (artistName == null) artistName = "";
        ArrayList<Song> result = new ArrayList<>();
        for (Song s : songs) {
            if (s.getArtist().getName().equalsIgnoreCase(artistName)) result.add(s);
        }
        return result;
    }

    public void sortByDuration() {
        songs.sort(Comparator.comparingInt(Song::getDuration));
    }

    public void displayPlaylist() {
        System.out.println("Playlist: " + name);
        for (Song s : songs) System.out.println("  " + s);
    }

    @Override
    public String toString() {
        return "Playlist{name='" + name + "', songsCount=" + songs.size() + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Playlist)) return false;
        Playlist playlist = (Playlist) o;
        return Objects.equals(name.toLowerCase(), playlist.name.toLowerCase());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name.toLowerCase());
    }
}