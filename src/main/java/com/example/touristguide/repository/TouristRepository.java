package com.example.touristguide.repository;

import com.example.touristguide.model.TouristAttraction;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository
public class TouristRepository {

    private final List<TouristAttraction> attractions = new ArrayList<>();

    private final List<String> cities = List.of("København", "Odense", "Aarhus", "Aalborg");
    private final List<String> tags = List.of("Familie", "Historie", "Seværdighed", "Have", "Gåtur", "Mad", "Museum");

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
    public void add(TouristAttraction attraction) {
        attractions.add(attraction);
    }
    public List<String> getCities() {
        return cities;
    }
    public List<String> getTags() {
        return tags;

    }
}