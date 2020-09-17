package com.oliota.restfull.maturidade3.all;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.oliota.restfull.maturidade3.controller.IndicadoController;

@RunWith(SpringRunner.class)
@SpringBootConfiguration

public class IndicadoControllerGetAllTest {

	private MockMvc mockMvc;

	@InjectMocks
	private IndicadoController controller;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	public void hello() throws Exception {
		mockMvc.perform(get("/indicados").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
}
