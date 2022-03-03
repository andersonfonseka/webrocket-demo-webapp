package com.andersonfonseka.wr.demo.model;

import java.util.UUID;

public class Movie {

	private String id = UUID.randomUUID().toString();
	
	private String title;
	
	private String storyline;
	
	private String posterurl;
	
	private int year;
	
	private double price;
	
	private String genres[];
	
	private String actors[];

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStoryline() {
		return storyline;
	}

	public void setStoryline(String storyline) {
		this.storyline = storyline;
	}

	public String getPosterurl() {
		return posterurl;
	}

	public void setPosterurl(String posterurl) {
		this.posterurl = posterurl;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getGenres() {
		return genres[0];
	}

	public void setGenres(String[] genres) {
		this.genres = genres;
	}

	public String[] getActors() {
		return actors;
	}

	public void setActors(String[] actors) {
		this.actors = actors;
	}	
	
}
