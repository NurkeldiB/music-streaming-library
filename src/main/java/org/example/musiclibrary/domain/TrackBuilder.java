package org.example.musiclibrary.domain;

public class TrackBuilder {
    private Long id;
    private String title;
    private int durationSec;

    public TrackBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public TrackBuilder title(String title) {
        this.title = title;
        return this;
    }

    public TrackBuilder durationSec(int durationSec) {
        this.durationSec = durationSec;
        return this;
    }

    public Track build() {
        return new Track(id, title, durationSec);
    }
}
