package org.example.musiclibrary.domain;

import java.util.Objects;

public class Track {
    private Long id;
    private String title;
    private int durationSec;

    public Track() {}

    public Track(Long id, String title, int durationSec) {
        setId(id);
        setTitle(title);
        setDurationSec(durationSec);
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public int getDurationSec() { return durationSec; }

    public void setId(Long id) { this.id = id; }

    public void setTitle(String title) {
        if (title == null || title.isBlank()) throw new IllegalArgumentException("title is blank");
        this.title = title;
    }

    public void setDurationSec(int durationSec) {
        if (durationSec <= 0) throw new IllegalArgumentException("durationSec must be > 0");
        this.durationSec = durationSec;
    }

    @Override
    public String toString() {
        return "Track{id=" + id + ", title='" + title + "', durationSec=" + durationSec + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Track track)) return false;
        if (id != null && track.id != null) return Objects.equals(id, track.id);
        return Objects.equals(title, track.title) && durationSec == track.durationSec;
    }

    @Override
    public int hashCode() {
        return (id != null) ? Objects.hash(id) : Objects.hash(title, durationSec);
    }
}
