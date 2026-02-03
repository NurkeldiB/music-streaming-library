import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Db {

    public static void createTables(Connection con) throws SQLException {
        try (Statement st = con.createStatement()) {
            st.execute("""
                    CREATE TABLE IF NOT EXISTS artists (
                        id SERIAL PRIMARY KEY,
                        name  TEXT NOT NULL,
                        genre TEXT NOT NULL
                    )
                    """);

            st.execute("""
                    CREATE TABLE IF NOT EXISTS songs (
                        id SERIAL PRIMARY KEY,
                        title     TEXT NOT NULL,
                        duration  INT  NOT NULL,
                        artist_id INT  NOT NULL REFERENCES artists(id) ON DELETE CASCADE
                    )
                    """);
        }
    }

    public static long insertArtist(Connection con, Artist artist) throws SQLException {
        String sql = "INSERT INTO artists(name, genre) VALUES(?, ?) RETURNING id";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, artist.getName());
            ps.setString(2, artist.getGenre());
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                long id = rs.getLong(1);
                artist.setId(id);
                return id;
            }
        }
    }

    public static long insertSong(Connection con, String title, int duration, long artistId) throws SQLException {
        String sql = "INSERT INTO songs(title, duration, artist_id) VALUES(?, ?, ?) RETURNING id";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, title);
            ps.setInt(2, duration);
            ps.setLong(3, artistId);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getLong(1);
            }
        }
    }

    public static List<SongDto> getAllSongs(Connection con) throws SQLException {
        String sql = """
                SELECT s.id, s.title, s.duration, a.id as artist_id, a.name, a.genre
                FROM songs s
                JOIN artists a ON a.id = s.artist_id
                ORDER BY s.id
                """;
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            ArrayList<SongDto> list = new ArrayList<>();
            while (rs.next()) {
                list.add(new SongDto(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getInt("duration"),
                        rs.getLong("artist_id"),
                        rs.getString("name"),
                        rs.getString("genre")
                ));
            }
            return list;
        }
    }

    public static SongDto getSongById(Connection con, long id) throws SQLException {
        String sql = """
                SELECT s.id, s.title, s.duration, a.id as artist_id, a.name, a.genre
                FROM songs s
                JOIN artists a ON a.id = s.artist_id
                WHERE s.id = ?
                """;
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                return new SongDto(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getInt("duration"),
                        rs.getLong("artist_id"),
                        rs.getString("name"),
                        rs.getString("genre")
                );
            }
        }
    }

    public static boolean updateSongDuration(Connection con, long songId, int newDuration) throws SQLException {
        String sql = "UPDATE songs SET duration = ? WHERE id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, newDuration);
            ps.setLong(2, songId);
            return ps.executeUpdate() == 1;
        }
    }

    public static boolean updateSong(Connection con, long songId, String title, int duration, long artistId) throws SQLException {
        String sql = "UPDATE songs SET title = ?, duration = ?, artist_id = ? WHERE id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, title);
            ps.setInt(2, duration);
            ps.setLong(3, artistId);
            ps.setLong(4, songId);
            return ps.executeUpdate() == 1;
        }
    }

    public static boolean deleteSong(Connection con, long songId) throws SQLException {
        String sql = "DELETE FROM songs WHERE id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, songId);
            return ps.executeUpdate() == 1;
        }
    }
}
