package org.example.musiclibrary.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Configuration
public class DatabaseCheckRunner {

    @Bean
    CommandLineRunner checkDatabase(DataSource dataSource) {
        return args -> {
            try (Connection conn = dataSource.getConnection()) {

                System.out.println("=== DATABASE CHECK ===");

                // 1) Проверка playlists
                PreparedStatement ps1 =
                        conn.prepareStatement("SELECT COUNT(*) FROM playlists");
                ResultSet rs1 = ps1.executeQuery();
                rs1.next();
                System.out.println("Playlists count: " + rs1.getInt(1));

                // 2) Проверка playlist_tracks
                PreparedStatement ps2 =
                        conn.prepareStatement("SELECT COUNT(*) FROM playlist_tracks");
                ResultSet rs2 = ps2.executeQuery();
                rs2.next();
                System.out.println("Playlist-Tracks links: " + rs2.getInt(1));

                System.out.println("=== DATABASE CHECK DONE ===");

            }
        };
    }
}
