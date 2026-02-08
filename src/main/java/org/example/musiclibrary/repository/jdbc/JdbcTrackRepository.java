package org.example.musiclibrary.repository.jdbc;

import org.example.musiclibrary.domain.Track;
import org.example.musiclibrary.repository.TrackRepository;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcTrackRepository implements TrackRepository {

    private final DataSource dataSource;

    public JdbcTrackRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Track save(Track t) {
        String sql = "INSERT INTO tracks(title, duration_sec) VALUES (?, ?) RETURNING id";
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, t.getTitle());
            ps.setInt(2, t.getDurationSec());

            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                t.setId(rs.getLong(1));
                return t;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Track> findAll() {
        String sql = "SELECT id, title, duration_sec FROM tracks ORDER BY id";
        List<Track> list = new ArrayList<>();

        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Track t = new Track(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getInt("duration_sec")
                );
                list.add(t);
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Track> findById(Long id) {
        String sql = "SELECT id, title, duration_sec FROM tracks WHERE id=?";
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return Optional.empty();
                Track t = new Track(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getInt("duration_sec")
                );
                return Optional.of(t);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Track update(Long id, Track t) {
        String sql = "UPDATE tracks SET title=?, duration_sec=? WHERE id=?";
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, t.getTitle());
            ps.setInt(2, t.getDurationSec());
            ps.setLong(3, id);

            int updated = ps.executeUpdate();
            if (updated == 0) throw new RuntimeException("Track not found: " + id);

            t.setId(id);
            return t;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM tracks WHERE id=?";
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
