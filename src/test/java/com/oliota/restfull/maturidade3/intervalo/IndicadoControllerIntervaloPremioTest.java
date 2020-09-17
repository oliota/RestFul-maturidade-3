package com.oliota.restfull.maturidade3.intervalo;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oliota.restfull.maturidade3.business.Business;
import com.oliota.restfull.maturidade3.controller.IndicadoController;
import com.oliota.restfull.maturidade3.model.Indicado;
import com.oliota.restfull.maturidade3.model.IntervaloPremiados;

public class IndicadoControllerIntervaloPremioTest {
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
	public void novoIndicado() throws Exception {

		Indicado request = new Indicado(1990, "Titulo", "Studio", "Produtor", true, null);
		request.setLinks(Business.adicionarLinks(request));
		ObjectMapper objectMapper = new ObjectMapper();

		MvcResult result;

		gerarTitulosPrimeiroProdutor(objectMapper, request);
		gerarTitulosProximoProdutor(objectMapper, request);

		result = mockMvc
				.perform(get("/indicados/intervalo_premios").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request)).characterEncoding("utf-8"))
				.andExpect(status().isOk()).andReturn();

		IntervaloPremiados response = objectMapper.readValue(result.getResponse().getContentAsString(),
				IntervaloPremiados.class);

		assertEquals(response.getMin().get(0).getProducer(), "Produtor recordista");

		assertEquals(response.getMax().get(0).getProducer(), "Produtor");

	}

	private void gerarTitulosPrimeiroProdutor(ObjectMapper objectMapper, Indicado request)
			throws JsonProcessingException, Exception {
		mockMvc.perform(post("/indicados").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)).characterEncoding("utf-8"))
				.andExpect(status().isCreated()).andReturn();

		request.setTitle("Novo titulo");
		request.setYear(2000);

		mockMvc.perform(post("/indicados").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)).characterEncoding("utf-8"))
				.andExpect(status().isCreated()).andReturn();

		request.setTitle("Novissimo titulo");
		request.setYear(2005);

		mockMvc.perform(post("/indicados").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)).characterEncoding("utf-8"))
				.andExpect(status().isCreated()).andReturn();
	}

	private void gerarTitulosProximoProdutor(ObjectMapper objectMapper, Indicado request)
			throws JsonProcessingException, Exception {
		request.setProducers("Produtor recordista");
		request.setTitle("recente titulo");
		request.setYear(2000);

		mockMvc.perform(post("/indicados").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)).characterEncoding("utf-8"))
				.andExpect(status().isCreated()).andReturn();

		request.setTitle("Recentissimo titulo");
		request.setYear(2001);

		mockMvc.perform(post("/indicados").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)).characterEncoding("utf-8"))
				.andExpect(status().isCreated()).andReturn();
	}
}
