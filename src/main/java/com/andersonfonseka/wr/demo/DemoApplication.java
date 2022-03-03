package com.andersonfonseka.wr.demo;

import java.util.Locale;

import com.andersonfonseka.wr.application.WebApplication;
import com.andersonfonseka.wr.demo.pages.ErrorPage;
import com.andersonfonseka.wr.demo.pages.movies.MoviePage;
import com.andersonfonseka.wr.message.Message;
import com.andersonfonseka.wr.message.MessageBundle;

public class DemoApplication extends WebApplication {

	public DemoApplication() {

		super("demo", 8080, "Demo Web App");

		MessageBundle messageBundle = new MessageBundle();
		messageBundle.add("en_US", new Message("/message.properties"));
		messageBundle.add("pt_BR", new Message("/message_Pt_BR.properties"));
		
		setMessageBundle(messageBundle);
		setDefaultMessageBundle(Locale.getDefault().toString());
		
		setStartPage(MoviePage.class);
		setErrorPage(ErrorPage.class);
	}

}
