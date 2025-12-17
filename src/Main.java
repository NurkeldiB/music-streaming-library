import java.sql.SQLOutput;
import java.util.Scanner;
public class Main {

    public static void main(String[] args) {

        Artist artist1 = new Artist("Kairat Nurtas", "Classic");
        Artist artist2 = new Artist("Shiza", "Rock");

        Song song1 = new Song("Suigenym", artist1, 200);
        Song song2 = new Song("Atty oq", artist2, 210);
        Song song3 = new Song("Habar", artist1, 200);

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
