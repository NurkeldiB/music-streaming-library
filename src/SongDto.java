public record SongDto(
        long id,
        String title,
        int duration,
        long artistId,
        String artistName,
        String artistGenre
) {}
