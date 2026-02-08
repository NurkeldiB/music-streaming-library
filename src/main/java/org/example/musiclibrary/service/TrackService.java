package org.example.musiclibrary.service;

import org.example.musiclibrary.domain.Track;

import java.util.List;

public interface TrackService {
    Track create(String title, int durationSec);
    List<Track> list();
    Track get(Long id);
    Track update(Long id, String title, int durationSec);
    void delete(Long id);
}
