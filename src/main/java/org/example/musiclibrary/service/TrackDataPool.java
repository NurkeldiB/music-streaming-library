package org.example.musiclibrary.service;

import org.example.musiclibrary.domain.Track;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TrackDataPool<T extends Track> {

    private final List<T> data = new ArrayList<>();

    public void add(T track) {
        data.add(track);
    }

    public List<T> filterByDuration(int minSec) {
        return data.stream()
                .filter(t -> t.getDurationSec() >= minSec) // lambda
                .collect(Collectors.toList());
    }

    public List<T> sortByTitle() {
        return data.stream()
                .sorted(Comparator.comparing(Track::getTitle)) // lambda
                .collect(Collectors.toList());
    }
}
