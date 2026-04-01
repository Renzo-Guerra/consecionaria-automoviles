package org.proyecto.concesionariaautomoviles.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.proyecto.concesionariaautomoviles.dto.AutomovilDTOReq;
import org.proyecto.concesionariaautomoviles.dto.AutomovilDTORes;
import org.proyecto.concesionariaautomoviles.exception.CustomNotFoundException;
import org.proyecto.concesionariaautomoviles.service.AutomovilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = AutomovilController.class)
public class AutomovilControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private AutomovilService automovilService;

    private AutomovilDTOReq automovilDTOReq;
    private AutomovilDTORes automovilDTORes;

    @BeforeEach
    public void init(){
        this.automovilDTOReq = AutomovilDTOReq.builder()
                .modelo("2017")
                .marca("ford")
                .motor("1.6L Sigma Ti-VCT")
                .color("negro")
                .patente("afr233")
                .build();
        this.automovilDTORes = AutomovilDTORes.builder()
                .id(1L)
                .modelo(automovilDTOReq.getModelo())
                .marca(automovilDTOReq.getMarca())
                .motor(automovilDTOReq.getMotor())
                .color(automovilDTOReq.getColor())
                .patente(automovilDTOReq.getPatente())
                .build();
    }

    @Test
    public void automovilController_crear_returnCreated() throws Exception{
        given(automovilService.crear(BDDMockito.any(AutomovilDTOReq.class)))
                .willReturn(automovilDTORes);

        ResultActions response = mockMvc.perform(post("/api/automoviles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(automovilDTOReq)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(automovilDTORes.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.patente").value(automovilDTORes.getPatente()));
    }

    @Test
    public void automovilController_crear_ThrowsMethodArgumentNotValidException() throws Exception{
        automovilDTOReq.setPatente("aaaaa1");

        given(automovilService.crear(BDDMockito.any(AutomovilDTOReq.class)))
                .willReturn(automovilDTORes);

        ResultActions response = mockMvc.perform(post("/api/automoviles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(automovilDTOReq)));

        response.andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.patente").value("Por favor ingrese una patente válida, por ej: xxx111 | xx111xx"));
    }

    @Test
    public void automovilController_traerTodos_returnsAllAutomoviles() throws Exception {
        given(automovilService.traerTodos())
                .willReturn(List.of(automovilDTORes));

        ResultActions response = mockMvc.perform(get("/api/automoviles")
                .accept(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1));
    }

    @Test
    public void automovilController_traerTodos_returnsEmptyList() throws Exception {
        given(automovilService.traerTodos())
                .willReturn(List.of());

        ResultActions response = mockMvc.perform(get("/api/automoviles")
                .accept(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(0));
    }

    @Test
    public void automovilController_traerPorId_returnsRequestedAutomovil() throws Exception{
        given(automovilService.traerPorId(automovilDTORes.getId()))
                .willReturn(automovilDTORes);

        ResultActions response = mockMvc.perform(get("/api/automoviles/" + automovilDTORes.getId())
                .accept(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(automovilDTORes.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.patente").value(automovilDTORes.getPatente()));
    }

    @Test
    public void automovilController_traerPorId_returnsNotFoundException() throws Exception {
        given(automovilService.traerPorId(automovilDTORes.getId()))
                .willThrow(new CustomNotFoundException("No se encontró ningun automovil con el id " + automovilDTORes.getId() + "!"));

        ResultActions response = mockMvc.perform(get("/api/automoviles/" + automovilDTORes.getId())
                .accept(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
