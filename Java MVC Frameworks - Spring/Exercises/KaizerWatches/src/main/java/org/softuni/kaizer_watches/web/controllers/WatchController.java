package org.softuni.kaizer_watches.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.kaizer_watches.domain.models.bindings.CreateWatchBindingModel;
import org.softuni.kaizer_watches.domain.models.services.WatchServiceModel;
import org.softuni.kaizer_watches.domain.models.views.AllWatchesWatchViewModel;
import org.softuni.kaizer_watches.domain.models.views.DetailsWatchViewModel;
import org.softuni.kaizer_watches.domain.models.views.TopWatchesWatchViewModel;
import org.softuni.kaizer_watches.service.WatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/watches")
public class WatchController {

    private final WatchService watchService;

    private final ModelMapper modelMapper;

    @Autowired
    public WatchController(WatchService watchService, ModelMapper modelMapper) {
        this.watchService = watchService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/top")
    public List<TopWatchesWatchViewModel> findTop4Watches() {
        return this.watchService
                .getTop4Watches()
                .stream()
                .map(watchServiceModel -> this.modelMapper.map(watchServiceModel, TopWatchesWatchViewModel.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/all")
    public List<AllWatchesWatchViewModel> findAllWatches() {
        return this.watchService
                .getAllWatches()
                .stream()
                .map(watchServiceModel -> this.modelMapper.map(watchServiceModel, AllWatchesWatchViewModel.class))
                .collect(Collectors.toList());
    }


    @GetMapping(value = "/details")
    public DetailsWatchViewModel details(@RequestParam("id") String id) {
        return this.modelMapper
                .map(this.watchService.getWatchById(id), DetailsWatchViewModel.class);
    }

    @PostMapping("/add")
    public void create(CreateWatchBindingModel createWatchBindingModel) {
        WatchServiceModel watchServiceModel = this.modelMapper.map(createWatchBindingModel, WatchServiceModel.class);
        this.watchService.createWatch(watchServiceModel);
    }
}
