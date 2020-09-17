package com.oliota.restfull.maturidade3.business;

import com.oliota.restfull.maturidade3.model.Indicado;

public class Leitor {
	
	
	public static Indicado processarLinha(String linha) throws Exception {
		Indicado indicado= new Indicado();
		String[] campos; 
		try {
			campos = validaLinha(linha);
			indicado.setYear(validarAno(campos[0]));
			indicado.setTitle(validarCampo("Title", campos[1]));
			indicado.setStudios(validarCampo("Studios", campos[2]));
			indicado.setProducers(validarCampo("Producers", campos[3]));
			indicado.setWinner(validarVencedor(campos[4]));
			indicado.setLinks(Business.adicionarLinks(indicado));	
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
			
		return indicado;
	}
	
	public static String[] validaLinha(String valor) throws Exception {

		if (!valor.trim().contains(";"))
			throw new Exception("O delimitador (;) não foi encontrado no texto - " + valor);

		String[] linha;
		linha = valor.trim().split(";", 6);
		if (linha.length < 5)
			throw new Exception("Não foi possível separar os valores do texto - " + valor);
		return linha;
	}
	 
	

	public static Integer validarAno(String ano) throws Exception {
		try {
			return Integer.valueOf(ano);
		} catch (Exception e) {
			throw new Exception("'" + ano + "' não é um ano válido");
		}
	}

	public static String validarCampo(String campo, String valor) throws Exception {
		if (valor == null || valor.trim().isEmpty())
			throw new Exception("Não foi informado valor para o campo -  " + campo);
		return valor.trim();
	}

	public static Boolean validarVencedor(String vencedor) {

		switch (vencedor.trim().toLowerCase()) {
		case "sim":
		case "yes":
		case "true":
			return true;

		default:
			return false;
		}
	}

	

}
