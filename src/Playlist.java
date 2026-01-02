import java.util.ArrayList;
import java.util.Comparator;

public class Playlist {

    private String name;
    private ArrayList<Song> songs = new ArrayList<>();

    public Playlist(String name) {
        this.name = name;
    }

    public void addSong(Song song) {
        songs.add(song);
    }

    
    public void searchByTitle(String keyword) {
        for (Song s : songs) {
            if (s.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println(s);
            }
        }
    }


    public void filterByArtist(String artistName) {
        for (Song s : songs) {
            if (s.getArtist().getName().equalsIgnoreCase(artistName)) {
                System.out.println(s);
            }
        }
    }


    public void sortByDuration() {
        songs.sort(Comparator.comparingInt(Song::getDuration));
    }

    public void displayPlaylist() {
        System.out.println("Playlist: " + name);
        for (Song s : songs) {
            System.out.println(s);
        }
    }
}
