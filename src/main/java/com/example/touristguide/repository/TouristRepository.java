package com.example.touristguide.repository;

import com.example.touristguide.model.TouristAttraction;
import com.example.touristguide.service.TouristService;
import com.example.touristguide.model.TouristAttraction;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository
public class TouristRepository {

    private final List<TouristAttraction> attractions = new ArrayList<>();

    public TouristRepository() {
        attractions.add(new TouristAttraction("Runde taarn", "Verdens højeste bygning", "København", List.of("Familie", "Seværdigheder", "Høj")));
        attractions.add(new TouristAttraction("Den Lille Havfrue", "En baddie lavet af sten", "København", List.of("Historie", "Seværdighed")));
                attractions.add(new TouristAttraction("Kongens Have", "Topgunn der mistede kærligheden", "København", List.of("Have", "Gåtur")));

    }

    public List<TouristAttraction> getAll() {
        return attractions;
    }

    public TouristAttraction findByName(String name) {
        return attractions.stream().filter(a -> a.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}