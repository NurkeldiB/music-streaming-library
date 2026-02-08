package org.example.musiclibrary.factory;

import org.example.musiclibrary.repository.TrackRepository;
import org.example.musiclibrary.service.TrackService;
import org.example.musiclibrary.service.impl.TrackServiceImpl;

public class TrackServiceFactory {

    public static TrackService create(TrackRepository repository) {
        return new TrackServiceImpl(repository);
    }
}
