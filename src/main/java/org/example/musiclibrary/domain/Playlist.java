package org.example.musiclibrary.domain;

import java.util.Objects;

public class Playlist {
    private Long id;
    private String name;

    public Playlist() {}

    public Playlist(Long id, String name) {
        setId(id);
        setName(name);
    }

    public Long getId() { return id; }
    public String getName() { return name; }

    public void setId(Long id) { this.id = id; }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Playlist name is blank");
        }
        this.name = name;
    }

    @Override
    public String toString() {
        return "Playlist{id=" + id + ", name='" + name + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Playlist p)) return false;
        if (id != null && p.id != null) return Objects.equals(id, p.id);
        return Objects.equals(name, p.name);
    }

    @Override
    public int hashCode() {
        return (id != null) ? Objects.hash(id) : Objects.hash(name);
    }
}
