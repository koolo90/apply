package com.brocode.apply.buissness.service;

import com.brocode.apply.entity.CelestialBody;
import com.brocode.apply.repository.CelestialBodyRepository;
import com.brocode.apply.controller.CelestialBodyController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("utest")
@WebMvcTest(controllers = { CelestialBodyController.class})
class CelestialBodyControllerRestTest {
    @Autowired MockMvc mockMvc;
    @MockitoBean CelestialBodyRepository celestialBodyRepositoryMock;
    private final String serviceUrl = "/celestialBody";

    @Test // Create
    void crateNewPlanet() throws Exception {
        String postedContent = """
                {"name":"Test","weight":"Test"}""";

        when(celestialBodyRepositoryMock.save(any(CelestialBody.class))).thenReturn( new CelestialBody(1L, "Saved", "Saved"));

        mockMvc.perform(
                post(serviceUrl + "/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postedContent)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                {"id":1,"name":"Saved","weight":"Saved"}"""));

        verify(celestialBodyRepositoryMock, times(1)).save(any(CelestialBody.class));
    }

    @Test @Disabled("Not implemented yet") // Create Many
    void crateNewPlanets() throws Exception {
        String postedContent = """
                [{"name":"Foo1","weight":"Bar"},
                {"name":"Foo2","weight":"Bar"}]""";

        when(celestialBodyRepositoryMock.save(any(CelestialBody.class))).thenReturn(new CelestialBody(1L, "Saved", "Saved"));

        mockMvc.perform(
                        post(serviceUrl + "/new")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(postedContent)
                                .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                {"id":1,"name":"Saved","weight":"Saved"}"""));
    }

    @Test @Disabled("Not implemented yet") // Create Negative test
    void errorWhenCrateNewPlanet() throws Exception {
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
    void findByName() throws Exception {
        Optional<CelestialBody> response = Optional.of(new CelestialBody("Test", "Test"));

        when(celestialBodyRepositoryMock.findByName(anyString())).thenReturn(response);

        mockMvc.perform(get("/celestialBody/Test"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                {"id":null,"name":"Test","weight":"Test"}"""));
    }

    @Test //Read NegativeTest
    void notFoundByName() throws Exception {
        Optional<CelestialBody> response = Optional.empty();

        when(celestialBodyRepositoryMock.findByName(anyString())).thenReturn(response);

        mockMvc.perform(get("/celestialBody/Test"))
                .andExpect(status().isNotFound());

        verify(celestialBodyRepositoryMock, times(1)).findByName(anyString());
    }

    @Test //Read Many
    void findAll() throws Exception {
        List<CelestialBody> response = Stream.of(
                new CelestialBody("Test1", "Test"),
                new CelestialBody("Test2", "Test")
        ).toList();
        when(celestialBodyRepositoryMock.findAll()).thenReturn(response);
        String expectedJsonContent = """
                [
                {"id":null,"name":"Test1","weight":"Test"},
                {"id":null,"name":"Test2","weight":"Test"}
                ]""";
        mockMvc.perform(get("/celestialBody/all"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJsonContent));
        verify(celestialBodyRepositoryMock, times(1)).findAll();
    }

    @Test //Read Many
    void findNone() throws Exception {
        when(celestialBodyRepositoryMock.findAll()).thenReturn(Collections.emptyList());

        String expectedJsonContent = """
                []""";
        mockMvc.perform(get("/celestialBody/all"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJsonContent));

        verify(celestialBodyRepositoryMock, times(1)).findAll();
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
                {
                "first":{"id":null,"name":"Test","weight":"Test"},
                "second":{"id":1,"name":"Patched","weight":"Patched"}}
                """));

        verify(celestialBodyRepositoryMock, times(1)).findByName(anyString());
        verify(celestialBodyRepositoryMock, times(1)).save(any(CelestialBody.class));
    }

    @Test // Update
    void updateError() throws Exception {
        String name = "Patched";
        ObjectMapper mapper = new ObjectMapper();
        CelestialBody celestialBody = new CelestialBody(name, name);
        when(celestialBodyRepositoryMock.findByName(name)).thenReturn(Optional.empty());

        CelestialBody mockedCelestialBodyResponse = new CelestialBody(1L, name, name);
        when(celestialBodyRepositoryMock.save(any(CelestialBody.class))).thenReturn(mockedCelestialBodyResponse);

        String postedContent = mapper.writeValueAsString(celestialBody);

        mockMvc.perform(patch(serviceUrl + "/edit/" + name)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postedContent)
                        .characterEncoding("utf-8")
                )
                .andExpect(status().isNotFound());

        verify(celestialBodyRepositoryMock, times(1)).findByName(anyString());
        verify(celestialBodyRepositoryMock, never()).save(any(CelestialBody.class));
    }

    @Test @Disabled("Not implemented yet")// Update Many
    void updateMany() throws Exception {
        String postedContent = """
                {"name":"Foo","weight":"Bar"}""";
        when(celestialBodyRepositoryMock.findByName(anyString())).thenReturn(Optional.of(new CelestialBody("Test", "Test")));
        when(celestialBodyRepositoryMock.save(any(CelestialBody.class))).thenReturn( new CelestialBody(1L, "Patched", "Patched"));

        mockMvc.perform(patch("/celestialBody/editMany")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postedContent)
                        .characterEncoding("utf-8")
                )
                .andExpect(status().isOk())
                .andExpect(content().json("""
                {"first":{"id":null,"name":"Test","weight":"Test"},"second":{"id":1,"name":"Patched","weight":"Patched"}}"""));
    }

    @Test @Disabled("Not implemented yet") // Update Many
    void updateManyError() throws Exception {
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

    @Test // Delete
    void drop() throws Exception {
        when(celestialBodyRepositoryMock.findByName(anyString())).thenReturn(Optional.of(new CelestialBody(1L,"Deleted", "Deleted")));
        when(celestialBodyRepositoryMock.deleteByName(anyString())).thenReturn(1L);
        mockMvc.perform(delete("/celestialBody/delete/Test"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                {"first":1,"second":{"id":1,"name":"Deleted","weight":"Deleted"}}"""));
    }

    @Test // Delete
    void dropError() throws Exception {
        String name = "Foo";
        when(celestialBodyRepositoryMock.findByName(name)).thenReturn(Optional.empty());
        String deleteUrl = serviceUrl + "/delete/";
        mockMvc.perform(delete(deleteUrl + name)).andExpect(status().isNotFound());
        verify(celestialBodyRepositoryMock, times(1)).findByName(name);
        verify(celestialBodyRepositoryMock, never()).deleteByName(name);
        verify(celestialBodyRepositoryMock, never()).deleteByName(name);
    }

    @Test // Delete
    void dropAll() throws Exception {
        CelestialBody celestialBody1 = new CelestialBody(1L, "Foo", "Bar");
        CelestialBody celestialBody2 = new CelestialBody(2L, "FooFoo", "BarBar");
        List<CelestialBody> celestialBodies = List.of(celestialBody1, celestialBody2);

        when(celestialBodyRepositoryMock.count()).thenReturn(Long.valueOf(celestialBodies.size()));
        when(celestialBodyRepositoryMock.findAll()).thenReturn(celestialBodies);

        mockMvc.perform(delete("/celestialBody/deleteAll"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                {"first":2,"second":[{"id":1,"name":"Foo","weight":"Bar"},{"id":2,"name":"FooFoo","weight":"BarBar"}]}"""));

        verify(celestialBodyRepositoryMock, times(1)).count();
        verify(celestialBodyRepositoryMock, times(1)).findAll();
        verify(celestialBodyRepositoryMock, times(1)).deleteAll();
    }

    @Test // Delete
    void dropAllError() throws Exception {
        List<CelestialBody> celestialBodies = Collections.emptyList();

        when(celestialBodyRepositoryMock.findAll()).thenReturn(celestialBodies);

        mockMvc.perform(delete("/celestialBody/deleteAll"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));

        verify(celestialBodyRepositoryMock, times(1)).count();
        verify(celestialBodyRepositoryMock, never()).findAll();
        verify(celestialBodyRepositoryMock, never()).deleteAll();
    }
}
