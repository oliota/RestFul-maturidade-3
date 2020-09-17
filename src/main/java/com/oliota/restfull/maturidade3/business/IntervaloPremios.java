package com.oliota.restfull.maturidade3.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.oliota.restfull.maturidade3.model.Indicado;
import com.oliota.restfull.maturidade3.model.Intervalo;
import com.oliota.restfull.maturidade3.model.IntervaloPremiados;

public class IntervaloPremios {

	public static IntervaloPremiados get(ArrayList<Indicado> indicados) {
		return new IntervaloPremiados(menores(indicados), maiores(indicados));
	}

	private static ArrayList<Intervalo> maiores(ArrayList<Indicado> indicados) {
		ArrayList<Intervalo> maiores = new ArrayList<Intervalo>();
		Map<String, List<Indicado>> grupos = indicados.stream().collect(Collectors.groupingBy(w -> w.getProducers()));
		int maiorDiferenca = 0;
		int anoAnterior = 0;
		for (Map.Entry<String, List<Indicado>> grupo : grupos.entrySet()) {

			List<Indicado> vencedores = (List<Indicado>) grupo.getValue();
			vencedores.sort(new YearSorter());
			anoAnterior = 0;
			for (Indicado i : vencedores) {
				if (anoAnterior != 0 && i.getWinner() && (i.getYear() - anoAnterior) >= maiorDiferenca) {
					if ((i.getYear() - anoAnterior) > maiorDiferenca) {
						maiores = new ArrayList<Intervalo>();
						maiorDiferenca = i.getYear() - anoAnterior;
					}
					maiores.add(new Intervalo(i.getProducers(), maiorDiferenca, anoAnterior, i.getYear()));
				}
				anoAnterior = i.getYear();
			}
		}
		return maiores;
	}

	private static ArrayList<Intervalo> menores(ArrayList<Indicado> indicados) {
		ArrayList<Intervalo> menores = new ArrayList<Intervalo>();
		Map<String, List<Indicado>> grupos = indicados.stream().collect(Collectors.groupingBy(w -> w.getProducers()));
		int menorDiferenca = 9999;
		int anoAnterior = 0;
		for (Map.Entry<String, List<Indicado>> grupo : grupos.entrySet()) {
			List<Indicado> vencedores = (List<Indicado>) grupo.getValue();
			vencedores.sort(new YearSorter());
			anoAnterior = 0;
			for (Indicado i : vencedores) {
				if (anoAnterior != 0 && i.getWinner() && (i.getYear() - anoAnterior) <= menorDiferenca) {
					if ((i.getYear() - anoAnterior) < menorDiferenca) {
						menores = new ArrayList<Intervalo>();
						menorDiferenca = i.getYear() - anoAnterior;
					}
					menores.add(new Intervalo(i.getProducers(), menorDiferenca, anoAnterior, i.getYear()));
				}
				anoAnterior = i.getYear();
			}

		}
		return menores;
	}

}
