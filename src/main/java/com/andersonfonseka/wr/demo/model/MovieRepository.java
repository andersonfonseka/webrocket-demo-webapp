package com.andersonfonseka.wr.demo.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MovieRepository {
	
	private static MovieRepository INSTANCE;
	
	private Map<String, Movie> movies = new HashMap<String, Movie>();
	
	private MovieRepository() {}
	
	public static MovieRepository getInstance() {

		if (INSTANCE == null) {
			INSTANCE = new MovieRepository();
		}

		return INSTANCE;
	}
	
	public Collection<Movie> run(String pUrl){
		
		movies.clear();
		
		try {

			URL url = new URL(pUrl);
			
			HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
	        
	        if (conexao.getResponseCode() != 200)
	            throw new RuntimeException("HTTP error code : " + conexao.getResponseCode());

	        BufferedReader buffer = new BufferedReader(new InputStreamReader((conexao.getInputStream())));

	        String resposta, jsonEmString = "";

	        while ((resposta = buffer.readLine()) != null) {
	            jsonEmString += resposta;
	        }
	        
	        Type listType = new TypeToken<ArrayList<Movie>>(){}.getType();
	        List<Movie> yourClassList = new Gson().fromJson(jsonEmString, listType);
	        
	        for (Movie movie : yourClassList) {
				movies.put(movie.getId(), movie);
			}
	        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return movies.values();
		
	}
	
	public void saveOrUpdate(Movie Movie) {
		this.movies.put(Movie.getId(), Movie);
	}
	

	public Movie getMovieById(String id) {
		return this.movies.get(id);
	}
	
	public Collection<Movie> getMovies(){
		return this.movies.values();
	}

}
