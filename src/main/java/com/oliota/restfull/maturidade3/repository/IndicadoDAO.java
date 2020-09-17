package com.oliota.restfull.maturidade3.repository;

import java.util.ArrayList; 

import com.oliota.restfull.maturidade3.business.Business;
import com.oliota.restfull.maturidade3.model.Indicado;

public class IndicadoDAO {
	private ArrayList<Indicado> indicados = new ArrayList<Indicado>();

	public Indicado get(String title) {
		return indicados.stream().filter(s -> s.getTitle().equals(Business.decodeLink(title))).findAny().orElse(null);
	}

	public ArrayList<Indicado> getAll() {
		return indicados;
	}

	public Indicado insert(Indicado indicado) {
		indicado.setLinks(Business.adicionarLinks(indicado));
		indicados.add(indicado);
		return indicado;
	}

	public ArrayList<Indicado> insertAll(ArrayList<Indicado> novos) { 
		indicados.addAll(novos);
		return indicados;
	}

	public Indicado update(Indicado atual, Indicado novo) {
		novo.setLinks(Business.adicionarLinks(novo));
		indicados.set(indicados.indexOf(atual), novo);
		return novo;
	}

	public Boolean delete(Indicado indicado) {
		return indicados.remove(indicado);
	}

	public ArrayList<Indicado> deleteAll() {
		return indicados = new ArrayList<Indicado>();
	}

}
