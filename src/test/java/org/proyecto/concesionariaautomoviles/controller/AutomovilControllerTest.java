package org.proyecto.concesionariaautomoviles.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.proyecto.concesionariaautomoviles.dto.AutomovilDTOReq;
import org.proyecto.concesionariaautomoviles.dto.AutomovilDTORes;
import org.proyecto.concesionariaautomoviles.exception.CustomNotFoundException;
import org.proyecto.concesionariaautomoviles.service.AutomovilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

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
                .cantPuertas(4)
                .build();
        this.automovilDTORes = AutomovilDTORes.builder()
                .id(1L)
                .modelo(automovilDTOReq.getModelo())
                .marca(automovilDTOReq.getMarca())
                .motor(automovilDTOReq.getMotor())
                .color(automovilDTOReq.getColor())
                .patente(automovilDTOReq.getPatente())
                .cantPuertas(automovilDTOReq.getCantPuertas())
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

        response.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("No se encontró ningun automovil con el id " + automovilDTORes.getId() + "!"));
    }

    @Test
    public void automovilController_editar_returnsEditedAutomovil() throws Exception{
        given(automovilService.editar(BDDMockito.eq(automovilDTORes.getId()), BDDMockito.any(AutomovilDTOReq.class)))
                .willReturn(automovilDTORes);

        ResultActions response = mockMvc.perform(put("/api/automoviles/" + automovilDTORes.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(automovilDTOReq))
                .accept(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.modelo").value(automovilDTOReq.getModelo()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.marca").value(automovilDTOReq.getMarca()));
    }

    @Test
    public void automovilController_editar_throwsDataIntegrityViolationException() throws Exception{
        given(automovilService.editar(BDDMockito.eq(automovilDTORes.getId()), BDDMockito.any(AutomovilDTOReq.class)))
                .willThrow(new DataIntegrityViolationException("Ya existe un automovil con la patente " + automovilDTOReq.getPatente()));

        ResultActions response = mockMvc.perform(put("/api/automoviles/" + automovilDTORes.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(automovilDTOReq)));

        response.andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Ya existe un automovil con la patente " + automovilDTOReq.getPatente()));
    }

    @Test
    public void automovilController_eliminar_deletesAutomovil() throws Exception {
        Long customId = 1L;

        doNothing().when(automovilService).eliminar(customId);

        ResultActions result = mockMvc.perform(delete("/api/automoviles/" + customId)
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void automovilController_eliminar_throwsCustomNotFoundException() throws Exception {
        Long customId = 1L;

        Mockito.doThrow(new CustomNotFoundException("No se encontró ningun automovil con el id " + customId + "!"))
                .when(automovilService).eliminar(customId);


        ResultActions result = mockMvc.perform(delete("/api/automoviles/" + customId)
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("No se encontró ningun automovil con el id " + customId + "!"));
    }
}
