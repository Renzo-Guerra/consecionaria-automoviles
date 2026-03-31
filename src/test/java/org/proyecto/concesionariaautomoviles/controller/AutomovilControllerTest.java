package org.proyecto.concesionariaautomoviles.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.proyecto.concesionariaautomoviles.dto.AutomovilDTOReq;
import org.proyecto.concesionariaautomoviles.dto.AutomovilDTORes;
import org.proyecto.concesionariaautomoviles.service.AutomovilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.BDDMockito.given;
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
}
