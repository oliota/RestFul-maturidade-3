package com.oliota.restfull.maturidade3.model;

import java.util.ArrayList;

public class IntervaloPremiados {

	private ArrayList<Intervalo> min;
	private ArrayList<Intervalo> max;

	public IntervaloPremiados() {

	}

	public IntervaloPremiados(ArrayList<Intervalo> min, ArrayList<Intervalo> max) {
		super();
		this.min = min;
		this.max = max;
	}

	public ArrayList<Intervalo> getMin() {
		return min;
	}

	public void setMin(ArrayList<Intervalo> min) {
		this.min = min;
	}

	public ArrayList<Intervalo> getMax() {
		return max;
	}

	public void setMax(ArrayList<Intervalo> max) {
		this.max = max;
	}

}
