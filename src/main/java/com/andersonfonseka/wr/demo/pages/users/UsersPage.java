package com.andersonfonseka.wr.demo.pages.users;

import java.util.HashMap;
import java.util.Map;

import com.andersonfonseka.wr.action.WebAction;
import com.andersonfonseka.wr.components.Button;
import com.andersonfonseka.wr.components.Column;
import com.andersonfonseka.wr.components.DataGrid;
import com.andersonfonseka.wr.components.WebForm;
import com.andersonfonseka.wr.components.WebPage;
import com.andersonfonseka.wr.demo.DemoNavBar;
import com.andersonfonseka.wr.demo.model.UserRepository;
import com.andersonfonseka.wr.demo.pages.movies.MoviePage;
import com.andersonfonseka.wr.navigation.WebNavigation;
import com.andersonfonseka.wr.style.Color;
import com.andersonfonseka.wr.validator.RequiredValidator;

public class UsersPage extends WebPage {

	private UserRepository userRepository;

	public UsersPage() {
		super("Users");
		setNavBar(new DemoNavBar());

		userRepository = UserRepository.getInstance();
	}

	public WebForm createForm() {

		WebForm wf = new WebForm(getMessageBundle().getMessage("user.users"));

		DataGrid dataGrid = new DataGrid("usersGrid", userRepository.getUsers());

		dataGrid.add(new Column("#", Column.SELECTION, "getId"));
		dataGrid.add(new Column(getMessageBundle().getMessage("user.firstname"), Column.TEXT, "getFirstName"));
		dataGrid.add(new Column(getMessageBundle().getMessage("user.lastname"), Column.TEXT, "getLastName"));
		dataGrid.add(new Column(getMessageBundle().getMessage("login.username"), Column.TEXT, "getUserName"));
		dataGrid.add(new Column(getMessageBundle().getMessage("login.password"), Column.TEXT, "getPassword"));
		dataGrid.add(new Column(getMessageBundle().getMessage("user.role"), Column.TEXT, "getRole"));
		dataGrid.add(new Column(getMessageBundle().getMessage("user.email"), Column.TEXT, "getEmail"));

		dataGrid.addValidator(new RequiredValidator(getMessageBundle().getMessage("users.grid.validator.required")));

		wf.add(dataGrid);

		Button btnSubmit = new Button("btnRegister",  getMessageBundle().getMessage("button.register"), Button.SUBMIT, new RegistrationAction(), true);
		wf.add(btnSubmit);

		Button btnUserDetails = new Button("btnUserDetails", getMessageBundle().getMessage("button.details"), Button.SUBMIT, new UserDetailAction());
		wf.add(btnUserDetails);
		
		wf.add(new Button("btnBack", getMessageBundle().getMessage("button.back"), Button.SUBMIT, new BackAction(), true, Color.SECONDARY));

		return wf;

	}

	public class RegistrationAction extends WebAction {

		@Override
		public WebPage execute(Map<String, String[]> params) {
			
			Map<String, String[]> methodParams = new HashMap<String, String[]>(params);
			methodParams.put("source", new String[]{"usersPage"});
			
			return WebNavigation.gotoPage(UserRegistrationPage.class, methodParams);
		}

	}

	public class UserDetailAction extends WebAction {

		@Override
		public WebPage execute(Map<String, String[]> params) {
			return WebNavigation.gotoPage(UserDetailPage.class, params);
		}

	}
	
	public class BackAction extends WebAction {

		@Override
		public WebPage execute(Map<String, String[]> params) {
			return WebNavigation.gotoPage(MoviePage.class);
		}

	}

	@Override
	public void onLoad(Map<String, String[]> params) {
	}

}
