package com.oliota.restfull.maturidade3.upload;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oliota.restfull.maturidade3.controller.IndicadoController;
import com.oliota.restfull.maturidade3.model.Indicado;

public class IndicadoControllerUploadCsvTest {
	private MockMvc mockMvc;

	@InjectMocks
	private IndicadoController controller;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	public void test() throws Exception {

		String conteudo = "year;title;studios;producers;winner\n" + "1980;title 1;studios 1;Rubem;yes;\n"
				+ "1980;title 2;studios 1;Rubem;yes;\n" + "1981;title 3;studios 1;Rubem;yes;\n"
				+ "1978;title 4;studios 1;Eliane;yes;\n" + "1989;title 5;studios 1;Eliane;yes; \n"
				+ "1980;title 6;studios 1;Eliane;yes;\n" + "1985;title 7;studios 1;Eliane;yes; ";

		MockMultipartFile file = new MockMultipartFile("file", "import.csv", MediaType.TEXT_PLAIN_VALUE,
				conteudo.getBytes());

		mockMvc.perform(multipart("/indicados/upload").file(file)).andExpect(status().isCreated());

		MvcResult result = mockMvc.perform(get("/indicados").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		ObjectMapper objectMapper = new ObjectMapper();

		ArrayList<Indicado> importados = objectMapper.readValue(result.getResponse().getContentAsString(),
				new TypeReference<ArrayList<Indicado>>() {
				});

		assertTrue(importados.size() == 7);

	}
}
