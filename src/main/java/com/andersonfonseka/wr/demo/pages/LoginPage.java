package com.andersonfonseka.wr.demo.pages;

import java.util.HashMap;
import java.util.Map;

import com.andersonfonseka.wr.action.WebAction;
import com.andersonfonseka.wr.components.Button;
import com.andersonfonseka.wr.components.FormGroup;
import com.andersonfonseka.wr.components.InputText;
import com.andersonfonseka.wr.components.WebForm;
import com.andersonfonseka.wr.components.WebPage;
import com.andersonfonseka.wr.demo.DemoNavBar;
import com.andersonfonseka.wr.demo.pages.movies.MoviePage;
import com.andersonfonseka.wr.demo.pages.users.UserRegistrationPage;
import com.andersonfonseka.wr.demo.pages.validator.UserCredentialValidator;
import com.andersonfonseka.wr.navigation.WebNavigation;
import com.andersonfonseka.wr.validator.RequiredValidator;

public class LoginPage extends WebPage {

	
	public LoginPage() {
		super("Login", CONTAINER);
		setNavBar(new DemoNavBar());
	}

	public WebForm createForm() {
		
		WebForm webForm = new WebForm(getMessageBundle().getMessage("login.title"));
		
		FormGroup[] formGroup = new FormGroup[2];
		for (int i = 0; i < formGroup.length; i++) {
			formGroup[i] = new FormGroup();
		}
		
		InputText txUserName = new InputText("txUserName", InputText.TEXT, getMessageBundle().getMessage("login.username"));
		txUserName.addValidator(new RequiredValidator());
		
		formGroup[0].add(txUserName);
		
		InputText txPassword = new InputText("txPassword", InputText.PASSWORD, getMessageBundle().getMessage("login.password"));
		txPassword.addValidator(new RequiredValidator());

		formGroup[1].add(txPassword);

		for (int i = 0; i < formGroup.length; i++) {
			webForm.add(formGroup[i]);
		}

		Button btnGrid = new Button("btnLogin", getMessageBundle().getMessage("button.login"), Button.SUBMIT, new LoginAction());
		webForm.add(btnGrid);
		
		Button btnSubmit = new Button("btnSubmit", getMessageBundle().getMessage("button.register"), Button.SUBMIT, new RegistrationAction(), true);
		webForm.add(btnSubmit);
		
		webForm.addValidator(new UserCredentialValidator());
				
		return webForm;
	}
	
	public class RegistrationAction extends WebAction {

		@Override
		public WebPage execute(Map<String, String[]> params) {
			
			Map<String, String[]> methodParams = new HashMap<String, String[]>(params);
			methodParams.put("source", new String[]{"loginPage"});
			
			return WebNavigation.gotoPage(UserRegistrationPage.class, methodParams);
		}
		
	}
	
	public class LoginAction extends WebAction {

		@Override
		public WebPage execute(Map<String, String[]> params) {
			return WebNavigation.gotoPage(MoviePage.class);
		}
	}

	@Override
	public void onLoad(Map<String, String[]> params) {}

}
