package com.oliota.restfull.maturidade3.business;

import java.util.Comparator;

import com.oliota.restfull.maturidade3.model.Indicado;

public class YearSorter implements Comparator<Indicado> {

	@Override
	public int compare(Indicado o1, Indicado o2) {
		return o1.getYear().compareTo(o2.getYear());
	}
}
