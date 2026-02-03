import java.util.Objects;

public class Artist {

    private Long id;      // для БД (может быть null, если объект еще не сохранён)
    private String name;
    private String genre;

    public Artist(String name, String genre) {
        this(null, name, genre);
    }

    public Artist(Long id, String name, String genre) {
        this.id = id;
        setName(name);
        setGenre(genre);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) { // обычно ставится после insert в БД
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("artist name must not be blank");
        this.name = name.trim();
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        if (genre == null || genre.isBlank()) throw new IllegalArgumentException("genre must not be blank");
        this.genre = genre.trim();
    }

    @Override
    public String toString() {
        return "Artist{id=" + id + ", name='" + name + "', genre='" + genre + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Artist)) return false;
        Artist artist = (Artist) o;
        return Objects.equals(name.toLowerCase(), artist.name.toLowerCase())
                && Objects.equals(genre.toLowerCase(), artist.genre.toLowerCase());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name.toLowerCase(), genre.toLowerCase());
    }
}