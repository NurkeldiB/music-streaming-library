package org.example.musiclibrary.exception;

public class TrackNotFoundException extends RuntimeException {
    public TrackNotFoundException(Long id) {
        super("Track not found: " + id);
    }
}
