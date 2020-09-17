package com.oliota.restfull.maturidade3.model;

import java.util.ArrayList;

public class Indicado implements Cloneable {

	private Integer year;
	private String title;
	private String studios;
	private String producers;
	private Boolean winner;
	private ArrayList<Link> links;

	public Indicado() {

	}

	public Indicado clone() {
        try { 
            return (Indicado)super.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println("Cloning not allowed.");
            return this;
        }
    }

	public Indicado(Integer year, String title, String studios, String producers, Boolean winner,
			ArrayList<Link> links) {
		this.year = year;
		this.title = title;
		this.studios = studios;
		this.producers = producers;
		this.winner = winner;
		this.links = links;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStudios() {
		return studios;
	}

	public void setStudios(String studios) {
		this.studios = studios;
	}

	public String getProducers() {
		return producers;
	}

	public void setProducers(String producers) {
		this.producers = producers;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Boolean getWinner() {
		return winner;
	}

	public void setWinner(Boolean winner) {
		this.winner = winner;
	}

	public ArrayList<Link> getLinks() {
		return links;
	}

	public void setLinks(ArrayList<Link> links) {
		this.links = links;
	}

}
