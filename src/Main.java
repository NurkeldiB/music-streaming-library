public class Main {
    public static void main(String[] args) {

        Artist a1 = new Artist("Kairat Nurtas", "Classic");
        Artist a2 = new Artist("Shiza", "Rock");

        Song s1 = new Song("Suigenym", a1, 200);
        Song s2 = new Song("Atty oq", a2, 210);
        Song s3 = new Song("Habar", a1, 200);

        Playlist playlist = new Playlist("My Favorites");

        playlist.addSong(s1);
        playlist.addSong(s2);
        playlist.addSong(s3);

        playlist.displayPlaylist();

        System.out.println("\nSearch:");
        playlist.searchByTitle("ha");

        System.out.println("\nFilter:");
        playlist.filterByArtist("Kairat Nurtas");

        System.out.println("\nSort:");
        playlist.sortByDuration();
        playlist.displayPlaylist();

        System.out.println("\nEquals check:");
        System.out.println(s1.equals(s3));
    }
}
