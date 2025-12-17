import java.sql.SQLOutput;
import java.util.Scanner;
public class Main {

    public static void main(String[] args) {

        Artist artist1 = new Artist("The Weeknd", "Pop");
        Artist artist2 = new Artist("Imagine Dragons", "Rock");

        Song song1 = new Song("Blinding Lights", artist1, 200);
        Song song2 = new Song("Believer", artist2, 210);
        Song song3 = new Song("Blinding Lights", artist1, 200);

        Playlist playlist = new Playlist("My Favorites");

        playlist.addSong(song1);
        playlist.addSong(song2);

        playlist.displayPlaylist();

        System.out.println();
        System.out.println("Comparing songs:");
        System.out.println("song1 equals song2: " + song1.equals(song2));
        System.out.println("song1 equals song3: " + song1.equals(song3));
    }
}
