package com.oliota.restfull.maturidade3.controller;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.oliota.restfull.maturidade3.business.Business;
import com.oliota.restfull.maturidade3.model.Indicado;
import com.oliota.restfull.maturidade3.model.IntervaloPremiados;
import com.oliota.restfull.maturidade3.repository.IndicadoDAO;

@RestController
@CrossOrigin(origins = "*")
public class IndicadoController {

	public static String path = "http://localhost:8080/indicados/";

	public static IndicadoDAO indicadoDAO = new IndicadoDAO();
 

	@PostMapping("/indicados")
	@ResponseStatus(HttpStatus.CREATED)
	public Indicado criar(@Valid @RequestBody Indicado request) {

		Business.validaRequest(request); 
		
		if (indicadoDAO.get(request.getTitle().trim()) != null)
			throw new ResponseStatusException(HttpStatus.CONFLICT,
					"Já existe um indicado com o Title=" + request.getTitle().trim());
		else {
			return indicadoDAO.insert(request); 
		}

	}

	@GetMapping("/indicados/{title}")
	@ResponseStatus(HttpStatus.OK)
	public Indicado getIndicadoPorTitulo(@PathVariable String title) {
		Indicado indicado = indicadoDAO.get(title.trim());
		if (indicado == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Indicado não localizado com o Titulo = " + title.trim());
		else
			return indicado;
	}

	@PutMapping("/indicados/{title}")
	@ResponseStatus(HttpStatus.OK)
	public Indicado atualizar(@PathVariable String title, @Valid @RequestBody Indicado request) {
		Business.validaRequest(request); 

		Indicado indicado = indicadoDAO.get(title.trim());
		if (indicado == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Indicado não localizado com o Title=" + title.trim());
		else if(!request.getTitle().equals(Business.decodeLink(title.trim()))  && indicadoDAO.get(request.getTitle().trim())!=null){
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Indicado não pode ser atualizado pois já existe um indicado com o Titulo = " + request.getTitle().trim());

		}else
			return indicadoDAO.update(indicado, request);
	}

	@DeleteMapping("/indicados/{title}")
	@ResponseStatus(HttpStatus.OK)
	public Boolean deleteIndicadoPorTitulo(@PathVariable String title) {
		Indicado indicado = indicadoDAO.get(title.trim());
		if (indicado == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Indicado não localizado com o Titulo = " + title.trim());
		else {
			return indicadoDAO.delete(indicado);
 		}
	}

	@GetMapping("/indicados")
	@ResponseStatus(HttpStatus.OK)
	public ArrayList<Indicado> listaTodos() {
		ArrayList<Indicado> indicados = indicadoDAO.getAll(); 
			return indicados;
	}

	@DeleteMapping("/indicados")
	@ResponseStatus(HttpStatus.OK)
	public ArrayList<Indicado> deletaTodos() {
		return indicadoDAO.deleteAll();
	}

	@PostMapping("/indicados/upload")
	@ResponseStatus(HttpStatus.CREATED)
	public ArrayList<Indicado> singleFileUpload(@RequestParam("file") MultipartFile file) throws Exception {

		ArrayList<Indicado> novos = Business.lerCSV(file,indicadoDAO.getAll()); 
		return indicadoDAO.insertAll(novos); 
	}

	@GetMapping("/indicados/intervalo_premios")
	@ResponseStatus(HttpStatus.OK)
	public IntervaloPremiados premiados() {
		return Business.IntervaloPremiados(indicadoDAO.getAll());
	}

}