package com.example.touristguide.controller;

import com.example.touristguide.model.AttractionF;
import com.example.touristguide.model.TouristAttraction;
import com.example.touristguide.service.TouristService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TouristGuideWebController {

    private final TouristService service;

    public TouristGuideWebController(TouristService service) {
        this.service = service;
    }

    @GetMapping("/attractions")
    public String showAttractions(Model model) {
        model.addAttribute("attractions", service.getAllAttractions());
        return "attractionList";
    }


    @GetMapping("/{name}/tags")
    public String tags(@PathVariable String name, Model model) {
        TouristAttraction attraction = service.getAttractionByName(name);

        if (attraction == null) {
            model.addAttribute("message", "kunne ikke finde attraktionen:" + name);
            return "notFound";
        }
        model.addAttribute("attraction", attraction);
        return "tags";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("form", new AttractionF());

        model.addAttribute("cities", service.getCities());
        model.addAttribute("allTags", service.getTags());
        return "addAttraction";
    }

    @PostMapping("/save")
    public String saveAttraction(@ModelAttribute("form") AttractionF form) {

        TouristAttraction attraction = new TouristAttraction(
                form.getName(),
                form.getDescription(),
                form.getCity(),
                form.getTags());

        service.saveAttraction(attraction);
        return "redirect:/attractions";
    }

    @GetMapping("/{name}/edit")
    public String editAttraction(@PathVariable String name, Model model) {
        TouristAttraction attraction = service.getAttractionByName(name);

        if (attraction == null) {
            model.addAttribute("message", "Kunne ikke finde attraktionen: " + name);
            return "notFound";
        }

        model.addAttribute("attraction", attraction);


        model.addAttribute("cities", service.getCities());
        model.addAttribute("allTags", service.getTags());

        return "updateAttraction";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute TouristAttraction attraction) {
        service.updateAttraction(attraction);
        return "redirect:/attractions";

    }
}