package com.andersonfonseka.wr.demo.pages.movies;

import java.util.Map;

import com.andersonfonseka.wr.action.WebAction;
import com.andersonfonseka.wr.components.Button;
import com.andersonfonseka.wr.components.Container;
import com.andersonfonseka.wr.components.FormGroup;
import com.andersonfonseka.wr.components.Image;
import com.andersonfonseka.wr.components.Label;
import com.andersonfonseka.wr.components.WebForm;
import com.andersonfonseka.wr.components.WebPage;
import com.andersonfonseka.wr.demo.DemoNavBar;
import com.andersonfonseka.wr.demo.model.Movie;
import com.andersonfonseka.wr.demo.model.MovieRepository;
import com.andersonfonseka.wr.navigation.WebNavigation;
import com.andersonfonseka.wr.util.BeantUtil;

public class MovieDetailsPage extends WebPage {

	private WebForm wf;

	public MovieDetailsPage() {
		super("Movies", WebPage.CONTAINER);
		setNavBar(new DemoNavBar());
	}

	@Override
	public WebForm createForm() {
		
		wf = new WebForm(getMessageBundle().getMessage("movies.details"));
				
		// TODO Auto-generated method stub
		return wf;
	}
	
	@Override
	public void onLoad(Map<String, String[]> params) {
		
		MovieRepository movieRepository = MovieRepository.getInstance();
		
		Movie movie = movieRepository.getMovieById(BeantUtil.getComponentValue(params, "movieId"));
		
		FormGroup fg2 = new FormGroup();
		fg2.add(new Label("<h4>" + movie.getTitle() + "</h4>"));
		
		wf.add(fg2);
		
		Container c0 = new Container();
		
		FormGroup fg0 = new FormGroup();
		fg0.add(new Image(movie.getPosterurl()));

		FormGroup fg1 = new FormGroup();
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("<b>" + movie.getYear() + "</b><br/>");
		sb.append(movie.getGenres() + "<br>");
		
		sb.append("<hr/>");
		
		sb.append(movie.getStoryline() + "<br>");
		
		sb.append("<hr/>");
		
		for (int i = 0; i < movie.getActors().length; i++) {
			sb.append(movie.getActors()[i] + "<br/>");	
		}
		
		fg1.add(new Label(sb.toString()));
		
		c0.add(fg0);
		c0.add(fg1);
		
		c0.add(new Button("btnAddShop", getMessageBundle().getMessage("button.addToCart"), new WebAction() {
			
			@Override
			public WebPage execute(Map<String, String[]> params) {
				return MovieDetailsPage.this;
			}
		}));
		
		c0.add(new Button("btnBack", getMessageBundle().getMessage("button.back"), new WebAction() {
			
			@Override
			public WebPage execute(Map<String, String[]> params) {
				return WebNavigation.gotoPage(MoviePage.class);
			}
		}));
		
		
		wf.add(c0);

	}


}
