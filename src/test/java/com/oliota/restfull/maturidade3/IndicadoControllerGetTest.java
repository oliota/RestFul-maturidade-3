package com.oliota.restfull.maturidade3;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

public class IndicadoControllerGetTest {
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

		Indicado request = new Indicado(1990, "Titulo", "Studio", "Produtor", true, null);
		request.setLinks(Business.adicionarLinks(request));
		ObjectMapper objectMapper = new ObjectMapper();

		MvcResult result = mockMvc
				.perform(post("/indicados").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request)).characterEncoding("utf-8"))
				.andExpect(status().isCreated()).andReturn();
  
		result = mockMvc.perform(get("/indicados/" + request.getTitle()).contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8")).andExpect(status().isOk()).andReturn();
		 
    	Indicado response = objectMapper.readValue(result.getResponse().getContentAsString(), Indicado.class);  
    	assertEquals(request.getTitle(),response.getTitle());

	}
}
