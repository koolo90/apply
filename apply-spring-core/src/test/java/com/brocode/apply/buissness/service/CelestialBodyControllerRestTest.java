package com.brocode.apply.buissness.service;

import com.brocode.apply.buissness.model.CelestialBody;
import com.brocode.apply.repositories.CelestialBodyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("utest")
@WebMvcTest(controllers = { CelestialBodyController.class})
class CelestialBodyControllerRestTest {
    @Autowired MockMvc mockMvc;
    @MockitoBean CelestialBodyRepository celestialBodyRepositoryMock;

    //Create
    @Test
    void crateNewPlanet() throws Exception {
        String postedContent = """
                {"name":"Test","weight":"Test"}""";

        when(celestialBodyRepositoryMock.save(any(CelestialBody.class))).thenReturn( new CelestialBody(1L, "Saved", "Saved"));

        mockMvc.perform(
                post("/celestialBody/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postedContent)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                {"id":1,"name":"Saved","weight":"Saved"}"""));
    }

    @Test //Read
    void findAll() throws Exception {
        List<CelestialBody> response = Stream.of(new CelestialBody("Test", "Test")).toList();
        when(celestialBodyRepositoryMock.findAll()).thenReturn(response);
        String expectedJsonContent = """
                [{"id":null,"name":"Test","weight":"Test"}]""";
        mockMvc.perform(get("/celestialBody/all"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJsonContent));
    }

    @Test //Read
    void findByName() throws Exception {
        Optional<CelestialBody> response = Optional.of(new CelestialBody("Test", "Test"));

        when(celestialBodyRepositoryMock.findByName(anyString())).thenReturn(response);

        mockMvc.perform(get("/celestialBody/Test"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                {"id":null,"name":"Test","weight":"Test"}"""));
    }

    @Test // Update
    void update() throws Exception {
        String postedContent = """
                {"name":"Foo","weight":"Bar"}""";
        when(celestialBodyRepositoryMock.findByName(anyString())).thenReturn(Optional.of(new CelestialBody("Test", "Test")));
        when(celestialBodyRepositoryMock.save(any(CelestialBody.class))).thenReturn( new CelestialBody(1L, "Patched", "Patched"));

        mockMvc.perform(patch("/celestialBody/edit/Test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postedContent)
                        .characterEncoding("utf-8")
                )
                .andExpect(status().isOk())
                .andExpect(content().json("""
                {"first":{"id":null,"name":"Test","weight":"Test"},"second":{"id":1,"name":"Patched","weight":"Patched"}}"""));
    }

    @Test
    void drop() throws Exception {
        when(celestialBodyRepositoryMock.findByName(anyString())).thenReturn(Optional.of(new CelestialBody(1L,"Deleted", "Deleted")));
        when(celestialBodyRepositoryMock.deleteByName(anyString())).thenReturn(1L);
        mockMvc.perform(delete("/celestialBody/delete/Test"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                {"first":1,"second":{"id":1,"name":"Deleted","weight":"Deleted"}}"""));
    }
}
