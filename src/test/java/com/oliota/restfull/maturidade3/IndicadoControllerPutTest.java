package com.oliota.restfull.maturidade3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oliota.restfull.maturidade3.business.Business;
import com.oliota.restfull.maturidade3.controller.IndicadoController;
import com.oliota.restfull.maturidade3.model.Indicado;

public class IndicadoControllerPutTest {
	
	private MockMvc mockMvc;
	 
    @InjectMocks
    private IndicadoController controller;
 

    @MockBean
    public Indicado indicado;
    
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }
 
    @Test
    public void atualizarIndicado() throws Exception {
     
    	Indicado request = new Indicado(1990,"Titulo","Studio","Produtor",true,null);
    	request.setLinks(Business.adicionarLinks(request));		
        ObjectMapper objectMapper = new ObjectMapper(); 


        
        MvcResult result = mockMvc.perform(
            post("/indicados")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
            .characterEncoding("utf-8")
            )
	            .andExpect(status().isCreated())
            .andReturn();
        
    	Indicado novo = objectMapper.readValue(result.getResponse().getContentAsString(), Indicado.class); 
  
        request.setProducers("atualizado");
        
        result= mockMvc.perform(
	            put("/indicados/"+request.getTitle())
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(request))
	            .characterEncoding("utf-8")
	            )
		         .andExpect(status().isOk())
	            .andReturn();
        
    	Indicado atualizado = objectMapper.readValue(result.getResponse().getContentAsString(), Indicado.class); 

    	 

    	assertNotEquals(novo.getProducers(),atualizado.getProducers()); 
    	assertEquals(request.getProducers(),atualizado.getProducers()); 

        
        
    }

}
