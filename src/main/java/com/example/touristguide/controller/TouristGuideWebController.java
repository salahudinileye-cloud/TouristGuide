package com.example.touristguide.controller;

import com.example.touristguide.model.TouristAttraction;
import com.example.touristguide.service.TouristService;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
}