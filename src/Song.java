public class Song {

    private String title;
    private Artist artist;
    private int duration; // seconds

    public Song(String title, Artist artist, int duration) {
        this.title = title;
        this.artist = artist;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Song{title='" + title +
                "', artist=" + artist.getName() +
                ", duration=" + duration + " sec}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Song)) return false;

        Song song = (Song) obj;
        return title.equals(song.title) &&
                artist.getName().equals(song.artist.getName());
    }
}
