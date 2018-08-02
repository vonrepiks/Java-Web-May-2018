package org.softuni.kaizer_watches.service;

import org.softuni.kaizer_watches.domain.models.services.WatchServiceModel;

import java.util.List;

public interface WatchService {

    void createWatch(WatchServiceModel watchServiceModel);

    List<WatchServiceModel> getTop4Watches();

    List<WatchServiceModel> getAllWatches();

    WatchServiceModel getWatchById(String id);
}
