package org.example.musiclibrary.repository;

import org.example.musiclibrary.domain.Track;

import java.util.List;
import java.util.Optional;

public interface TrackRepository {
    Track save(Track track);               // insert
    List<Track> findAll();
    Optional<Track> findById(Long id);
    Track update(Long id, Track track);
    void deleteById(Long id);
}
