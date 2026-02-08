package org.example.musiclibrary.service.impl;

import org.example.musiclibrary.domain.Track;
import org.example.musiclibrary.repository.TrackRepository;
import org.example.musiclibrary.service.TrackService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrackServiceImpl implements TrackService {

    private final TrackRepository repo; // ✅ DIP: interface

    public TrackServiceImpl(TrackRepository repo) {
        this.repo = repo;
    }

    @Override
    public Track create(String title, int durationSec) {
        Track t = new Track(null, title, durationSec);
        return repo.save(t);
    }

    @Override
    public List<Track> list() {
        return repo.findAll();
    }

    @Override
    public Track get(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Track not found: " + id));
    }

    @Override
    public Track update(Long id, String title, int durationSec) {
        Track t = new Track(null, title, durationSec);
        return repo.update(id, t);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
