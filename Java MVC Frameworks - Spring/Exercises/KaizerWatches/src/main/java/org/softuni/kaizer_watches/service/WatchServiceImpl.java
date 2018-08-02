package org.softuni.kaizer_watches.service;

import org.modelmapper.ModelMapper;
import org.softuni.kaizer_watches.domain.entities.Watch;
import org.softuni.kaizer_watches.domain.models.services.WatchServiceModel;
import org.softuni.kaizer_watches.repository.WatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WatchServiceImpl implements WatchService {

    private final WatchRepository watchRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public WatchServiceImpl(WatchRepository watchRepository, ModelMapper modelMapper) {
        this.watchRepository = watchRepository;
        this.modelMapper = modelMapper;
    }

    private void increaseWatchViews(String id) {
        Watch watch = this.watchRepository.findById(id).get();
        watch.setViews(watch.getViews() + 1);
        this.watchRepository.save(watch);
    }

    @Override
    public void createWatch(WatchServiceModel watchServiceModel) {
        Watch watchEntity = this.modelMapper.map(watchServiceModel, Watch.class);

        this.watchRepository.save(watchEntity);
    }

    @Override
    public List<WatchServiceModel> getTop4Watches() {
        return this.watchRepository
                .getTop4Watches()
                .stream()
                .map(watch -> this.modelMapper.map(watch, WatchServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<WatchServiceModel> getAllWatches() {
        return this.watchRepository
                .findAll()
                .stream()
                .map(watch -> this.modelMapper.map(watch, WatchServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public WatchServiceModel getWatchById(String id) {
        this.increaseWatchViews(id);
        Watch watch = this.watchRepository.findById(id).get();

        return this.modelMapper
                .map(watch, WatchServiceModel.class);
    }
}
