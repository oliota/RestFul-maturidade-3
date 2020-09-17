package com.oliota.restfull.maturidade3.model;

import java.util.ArrayList;

public class Response {
	private int status;
	private String mensagem;
	private ArrayList<?> detalhes;

	public Response() {

	}

	public Response(int status,String mensagem) {
		this.status = status;
		this.setMensagem(mensagem);
	}
	
	public Response(int status,String mensagem, ArrayList<?> detalhes) {
		this.status = status;
		this.setMensagem(mensagem);
		this.setDetalhes(detalhes);
	}


	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	 
	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public ArrayList<?> getDetalhes() {
		return detalhes;
	}

	public void setDetalhes(ArrayList<?> detalhes) {
		this.detalhes = detalhes;
	}
}
