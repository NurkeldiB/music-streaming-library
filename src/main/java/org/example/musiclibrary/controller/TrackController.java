package org.example.musiclibrary.controller;

import org.example.musiclibrary.domain.Track;
import org.example.musiclibrary.dto.TrackCreateRequest;
import org.example.musiclibrary.service.TrackService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tracks")
public class TrackController {

    private final TrackService service;

    public TrackController(TrackService service) {
        this.service = service;
    }

    @PostMapping
    public Track create(@RequestBody TrackCreateRequest req) {
        return service.create(req.title, req.durationSec);
    }

    @GetMapping
    public List<Track> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    public Track get(@PathVariable Long id) {
        return service.get(id);
    }

    @PutMapping("/{id}")
    public Track update(@PathVariable Long id, @RequestBody TrackCreateRequest req) {
        return service.update(id, req.title, req.durationSec);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
