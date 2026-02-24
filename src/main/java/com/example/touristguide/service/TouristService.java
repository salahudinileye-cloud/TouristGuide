package com.example.touristguide.service;

import com.example.touristguide.model.TouristAttraction;
import com.example.touristguide.repository.TouristRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TouristService {

    private final TouristRepository repository;

    public TouristService(TouristRepository repository) {
        this.repository = repository;
    }
    public List<TouristAttraction> getAllAttractions() {
        return repository.getAll();
    }
    public TouristAttraction getAttractionByName(String name) {
        return repository.findByName(name);
    }
    public void  saveAttraction(TouristAttraction attraction) {
        repository.add(attraction);
    }
    public List<String> getCities() {
        return repository.getCities();
    }
    public List<String> getTags() {
        return repository.getTags();

    }
}