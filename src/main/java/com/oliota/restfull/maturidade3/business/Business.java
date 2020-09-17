package com.oliota.restfull.maturidade3.business;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.oliota.restfull.maturidade3.controller.IndicadoController;
import com.oliota.restfull.maturidade3.model.Indicado;
import com.oliota.restfull.maturidade3.model.IntervaloPremiados;
import com.oliota.restfull.maturidade3.model.Link;
import com.oliota.restfull.maturidade3.model.Request;

public class Business {
	public static String encodeLink(String link) {
		try {
			return URLEncoder.encode(link, StandardCharsets.UTF_8.name());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String decodeLink(String link) {
		try {
			return URLDecoder.decode(link, StandardCharsets.UTF_8.name());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}

	public static ArrayList<Link> adicionarLinks(Indicado indicado) {
		Indicado body = indicado.clone();
		ArrayList<Link> links = new ArrayList<Link>();
		String link = IndicadoController.path + encodeLink(indicado.getTitle());
		Request request = new Request(link, "application/json", body);

		links.add(new Link("self", new Request(link)));
		links.add(new Link("delete", new Request(link)));
		links.add(new Link("post", request));
		links.add(new Link("put", request));
		return links;
	}

	public static Indicado formataLinha(String linha) throws Exception {
		Indicado indicado;
		try {
			indicado = Leitor.processarLinha(linha);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return indicado;
	}

	public static ArrayList<Indicado> lerCSV(MultipartFile file, ArrayList<Indicado> indicados) {
		ArrayList<Indicado> novos = new ArrayList<Indicado>();
		ArrayList<String> erros = new ArrayList<String>();
		ArrayList<String> conflitos = new ArrayList<String>();
		ArrayList<String> redundancias = new ArrayList<String>();
		Indicado novo;
		Boolean conflito, redundancia;
		if (file.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,
					"Por favor selecione um arquivo para importar");
		byte[] bytes;
		try {
			bytes = file.getBytes();
			ByteArrayInputStream inputFilestream = new ByteArrayInputStream(bytes);
			BufferedReader br = new BufferedReader(new InputStreamReader(inputFilestream));
			String line = "";
			int i = 1;
			while ((line = br.readLine()) != null) {
				try {
					if (i != 1) {
						if (line.isEmpty())
							continue;
						novo = Business.formataLinha(line);
						conflito = false;
						redundancia = false;
						for (Indicado indicado : indicados) {
							if (novo.getTitle().equals(indicado.getTitle())) {
								conflito = true;
								break;
							}
						}

						for (Indicado indicado : novos) {
							if (novo.getTitle().equals(indicado.getTitle())) {
								redundancia = true;
								break;
							}
						}

						if (conflito)
							conflitos.add("Conflito na linha " + (i)
									+ " - Já existe na base um indicado com o Titulo = " + novo.getTitle());

						if (redundancia)
							redundancias.add("Conflito na linha " + (i)
									+ " - Já existe no arquivo um indicado com o Titulo = " + novo.getTitle());

						if (!conflito && !redundancia)
							novos.add(novo);
					}
				} catch (Exception e) {
					erros.add("Erro na linha " + (i) + " - " + e.getMessage());
				}
				i++;
			}
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Erro ao receber o arquivo:" + e.getMessage());
		}

		if (erros.size() != 0)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Upload cancelado, favor revisar as linhas do arquivo e tente o upload novamente:\n\n"
							+ String.join("\n", erros));
		if (conflitos.size() != 0)
			throw new ResponseStatusException(HttpStatus.CONFLICT,
					"Upload cancelado, favor revisar as linhas do arquivo e tente o upload novamente:\n\n"
							+ String.join("\n", conflitos));
		if (redundancias.size() != 0)
			throw new ResponseStatusException(HttpStatus.CONFLICT,
					"Upload cancelado, favor revisar as linhas do arquivo e tente o upload novamente:\n\n"
							+ String.join("\n", redundancias));
		else
			return novos;
	}

	public static IntervaloPremiados IntervaloPremiados(ArrayList<Indicado> indicados) {
		return IntervaloPremios.get(indicados);
	}

	public static void validaRequest(Indicado indicado) {

		ArrayList<String> erros = new ArrayList<String>();
		if (indicado.getYear() == null)
			erros.add("O campo Ano não foi informado");
		if (indicado.getTitle().trim().toString().isEmpty())
			erros.add("O campo Titulo não foi informado");
		if (indicado.getStudios().trim().toString().isEmpty())
			erros.add("O campo Studio não foi informado");
		if (indicado.getProducers().trim().toString().isEmpty())
			erros.add("O campo Produtor não foi informado");
		if (indicado.getWinner().toString().trim().isEmpty())
			erros.add("O campo Vencedor não foi informado");
		if (erros.size() != 0)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Não foi possivel concluir sua requisição, verifique os motivos:\n\n" + String.join("\n", erros));
	}

}
