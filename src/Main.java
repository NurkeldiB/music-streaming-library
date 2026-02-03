// ... existing imports ...
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Main {

    // ... existing code ...

    public static void main(String[] args) {
        // ... existing OOP demo code ...

        String url = envOrDefault("DB_URL", "jdbc:postgresql://localhost:5432/oop_assignment");
        String user = envOrDefault("DB_USER", "postgres");
        String password = envRequired("DB_PASSWORD");

        Connection con = null;
        try {
            con = DriverManager.getConnection(url, user, password);
            System.out.println("\nCONNECTED to: " + url + " as " + user);

            Db.createTables(con);

            // Seed + CRUD demo
            long artistId = Db.insertArtist(con, new Artist("Daft Punk", "Electronic"));
            long songId = Db.insertSong(con, "Harder Better Faster Stronger", 224, artistId);

            System.out.println("\n== DB READ (all songs) ==");
            for (SongDto dto : Db.getAllSongs(con)) {
                System.out.println(dto);
            }

            System.out.println("\n== DB UPDATE (song duration) ==");
            Db.updateSongDuration(con, songId, 230);
            System.out.println(Db.getSongById(con, songId));

            System.out.println("\n== DB DELETE (song) ==");
            Db.deleteSong(con, songId);
            System.out.println("Songs after delete: " + Db.getAllSongs(con).size());

            // REST server
            System.out.println("\nStarting REST server on http://localhost:8080 ...");
            System.out.println("Try: GET http://localhost:8080/songs");
            RestServer.start(con, 8080);

            System.out.println("Server is running. Stop with Ctrl+C.");
            Thread.currentThread().join();

        } catch (SQLException e) {
            System.err.println("DB ERROR: " + e.getMessage());
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ignored) {
                    // ignore
                }
            }
        }
    }

    private static String envRequired(String key) {
        String v = System.getenv(key);
        if (v == null || v.isBlank()) {
            throw new IllegalStateException(
                    "Missing environment variable: " + key + "\n" +
                            "Set it in Run Configuration (Environment variables)."
            );
        }
        return v;
    }

    private static String envOrDefault(String key, String defaultValue) {
        String v = System.getenv(key);
        return (v == null || v.isBlank()) ? defaultValue : v;
    }
}