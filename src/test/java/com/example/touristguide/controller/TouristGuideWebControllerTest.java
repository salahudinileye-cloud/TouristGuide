package com.example.touristguide.controller;

import com.example.touristguide.model.TouristAttraction;
import com.example.touristguide.service.TouristService;
import com.example.touristguide.repository.TouristRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = TouristGuideWebController.class)
class TouristGuideWebControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TouristService service;

    @MockitoBean
    private TouristRepository touristRepository;

    @Test
    void showAttractions() throws Exception {

        List<TouristAttraction> mockList = Arrays.asList(
                new TouristAttraction("Runde taarn", "Verdens højeste bygning", "København", Arrays.asList("Familie", "Seværdigheder", "Høj")),
                new TouristAttraction("Den Lille Havfrue", "En baddie lavet af sten", "København", Arrays.asList("Historie", "Seværdighed")),
                new TouristAttraction("Kongens Have", "Topgunn der mistede kærligheden", "København", Arrays.asList("Have", "Gåtur"))
        );
        when(service.getAllAttractions()).thenReturn(mockList);


        mockMvc.perform(get("/attractions"))
                .andExpect(status().isOk())
                .andExpect(view().name("attractionList"))
                .andExpect(model().attributeExists("attractions"))
                .andExpect(model().attribute("attractions", mockList));
    }

    @Test
    void tagsWhenAttractionFound() throws Exception {

        TouristAttraction mockAttraction = new TouristAttraction("Runde taarn", "Verdens højeste bygning", "København", Arrays.asList("Familie", "Seværdigheder", "Høj"));
        when(service.getAttractionByName("Runde taarn")).thenReturn(mockAttraction);


        mockMvc.perform(get("/{name}/tags", "Runde taarn"))
                .andExpect(status().isOk())
                .andExpect(view().name("tags"))
                .andExpect(model().attributeExists("attraction"))
                .andExpect(model().attribute("attraction", mockAttraction));
    }

//    void tagsWhenAttractionMissing() throws Exception {
//
//        when(service.getAttractionByName("Ukendt")).thenReturn(null);
//
//
//        mockMvc.perform(get("/{name}/tags", "Ukendt"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("notFound"))
//                .andExpect(model().attributeExists("message"));
//    }


    @Test
    void showAddForm() throws Exception {

        when(service.getCities()).thenReturn(Arrays.asList("København", "Odense", "Aarhus", "Aalborg"));
        when(service.getTags()).thenReturn(Arrays.asList("Familie", "Historie", "Seværdighed", "Have", "Gåtur", "Mad", "Museum"));


        mockMvc.perform(get("/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("addAttraction"))
                .andExpect(model().attributeExists("form"))
                .andExpect(model().attributeExists("cities"))
                .andExpect(model().attributeExists("allTags"));
    }

    @Test
    void saveAttraction() throws Exception {



        mockMvc.perform(post("/save")
                        .param("name", "Ny Attraktion")
                        .param("description", "Test beskrivelse")
                        .param("city", "Aarhus")
                        .param("tags", "Familie")
                        .param("tags", "Seværdighed"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/attractions"));
    }

    @Test
    void editAttraction() throws Exception {

        TouristAttraction mockAttraction = new TouristAttraction("Runde taarn", "Verdens højeste bygning", "København", Arrays.asList("Familie"));
        when(service.getAttractionByName("Runde taarn")).thenReturn(mockAttraction);
        when(service.getCities()).thenReturn(Arrays.asList("København", "Odense", "Aarhus", "Aalborg"));
        when(service.getTags()).thenReturn(Arrays.asList("Familie", "Historie", "Seværdighed"));


        mockMvc.perform(get("/{name}/edit", "Runde taarn"))
                .andExpect(status().isOk())
                .andExpect(view().name("updateAttraction"))
                .andExpect(model().attributeExists("attraction"))
                .andExpect(model().attribute("attraction", mockAttraction))
                .andExpect(model().attributeExists("cities"))
                .andExpect(model().attributeExists("allTags"));
    }

    @Test
    void updateAttraction() throws Exception {

        doNothing().when(service).updateAttraction(any(TouristAttraction.class));


        mockMvc.perform(post("/update")
                        .param("name", "Runde taarn")
                        .param("description", "Opdateret beskrivelse")
                        .param("city", "København")
                        .param("tags", "Familie")
                        .param("tags", "Udsigt"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/attractions"));
    }

    @Test
    void deleteAttraction() throws Exception {

        TouristAttraction mockAttraction = new TouristAttraction("Runde taarn", "Verdens højeste bygning", "København", Arrays.asList("Familie"));
        when(service.getAttractionByName("Runde taarn")).thenReturn(mockAttraction);


        mockMvc.perform(get("/attractions/delete/{name}", "Runde taarn"))
                .andExpect(status().isOk())
                .andExpect(view().name("deleteAttraction"))
                .andExpect(model().attributeExists("attraction"));
    }

    @Test
    void deleteAttraction2() throws Exception {

        doNothing().when(service).deleteAttraction(any(String.class));


        mockMvc.perform(post("/attractions/delete/{name}", "Runde taarn"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/attractions"));
    }
}