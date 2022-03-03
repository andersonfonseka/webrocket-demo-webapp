package com.andersonfonseka.wr.demo.pages;

import java.util.Map;

import com.andersonfonseka.wr.action.WebAction;
import com.andersonfonseka.wr.components.Button;
import com.andersonfonseka.wr.components.WebForm;
import com.andersonfonseka.wr.components.WebPage;
import com.andersonfonseka.wr.demo.DemoNavBar;
import com.andersonfonseka.wr.demo.pages.movies.MoviePage;
import com.andersonfonseka.wr.navigation.WebNavigation;

public class ErrorPage extends WebPage {

	public ErrorPage() {
		super("Error Page");
		setNavBar(new DemoNavBar());
	}

	@Override
	public void onLoad(Map<String, String[]> params) {}

	@Override
	public WebForm createForm() {
		
		WebForm wf = new WebForm(getMessageBundle().getMessage("error.message"));
		
		Button btnReturn = new Button("btnReturn", getMessageBundle().getMessage("error.button"), new WebAction() {
			
			@Override
			public WebPage execute(Map<String, String[]> params) {
				return WebNavigation.gotoPage(MoviePage.class);
			}
		});
		
		wf.add(btnReturn);
		
		return wf;
	}

}
