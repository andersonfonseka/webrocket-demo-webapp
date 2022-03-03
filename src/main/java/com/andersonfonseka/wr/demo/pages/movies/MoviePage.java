package com.andersonfonseka.wr.demo.pages.movies;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import com.andersonfonseka.wr.action.WebAction;
import com.andersonfonseka.wr.components.Button;
import com.andersonfonseka.wr.components.Card;
import com.andersonfonseka.wr.components.Container;
import com.andersonfonseka.wr.components.FormGroup;
import com.andersonfonseka.wr.components.SelectItem;
import com.andersonfonseka.wr.components.SelectList;
import com.andersonfonseka.wr.components.WebForm;
import com.andersonfonseka.wr.components.WebPage;
import com.andersonfonseka.wr.demo.DemoNavBar;
import com.andersonfonseka.wr.demo.model.Movie;
import com.andersonfonseka.wr.demo.model.MovieRepository;
import com.andersonfonseka.wr.navigation.WebNavigation;
import com.andersonfonseka.wr.util.BeantUtil;
import com.andersonfonseka.wr.validator.RequiredValidator;

public class MoviePage extends WebPage {
	
	private WebForm webForm;

	public MoviePage() {
		super("Movies");
		setNavBar(new DemoNavBar());
	}

	@Override
	public void onLoad(Map<String, String[]> params) {
		
		SelectList selectList = (SelectList) this.webForm.getComponentById("selMovies");
		selectList.setValue(BeantUtil.getComponentValue(params, "selMovies"));
		
		Container container = new Container();
		container.setId("Container123");
		
		MovieRepository movieRepository = MovieRepository.getInstance();
		
		Collection<Movie> movies = movieRepository.run(BeantUtil.getComponentValue(params, "selMovies"));
		Iterator<Movie> itMovies = movies.iterator();
		
		while(itMovies.hasNext()) {

			Movie movie = itMovies.next();
			
			FormGroup fg = new FormGroup();
			
			Card card = new Card();
			card.setTitle(movie.getTitle() + " (" + movie.getYear() + ")");
			card.setUrl(movie.getPosterurl());
			card.setSubTitle(movie.getGenres());
			
			
			Button btn = new Button(getMessageBundle().getMessage("button.details"), Button.SUBMIT, new MovieDetail(), false);
			btn.addParam("movieId", movie.getId());
			
			card.add(btn);
			
			
			fg.add(card);
			
			container.add(fg);
		}
		
		webForm.add(container);
	}

	@Override
	public WebForm createForm() {
		
		webForm = new WebForm(getMessageBundle().getMessage("main.availableMovies"));
		
		FormGroup fg0 = new FormGroup();
		SelectList movieCatalogList = new SelectList("selMovies", getMessageBundle().getMessage("movies.catalogs"));
		movieCatalogList.add(new SelectItem("Catalog 1", "https://raw.githubusercontent.com/FEND16/movie-json-data/master/json/movies-coming-soon.json"));
		movieCatalogList.add(new SelectItem("Catalog 2", "https://raw.githubusercontent.com/FEND16/movie-json-data/master/json/movies-in-theaters.json", true, false));
		movieCatalogList.addValidator(new RequiredValidator());
		
		fg0.add(movieCatalogList);
		webForm.add(fg0);
		
		webForm.add(new Button("btnSubmit", getMessageBundle().getMessage("button.update"), new CatalogSelection()));

		return webForm;
	}
	
	class CatalogSelection extends WebAction {

		@Override
		public WebPage execute(Map<String, String[]> params) {
			return WebNavigation.gotoPage(MoviePage.class, params);
		}
	}
	
	class MovieDetail extends WebAction {

		@Override
		public WebPage execute(Map<String, String[]> params) {

			String value = BeantUtil.getComponentValue(params, "movieId");
			System.out.println(value);
			
			return WebNavigation.gotoPage(MovieDetailsPage.class, params);
		}
	}


}
