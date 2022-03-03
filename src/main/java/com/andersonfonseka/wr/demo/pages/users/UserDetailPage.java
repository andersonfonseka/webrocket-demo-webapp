package com.andersonfonseka.wr.demo.pages.users;

import java.util.Map;

import com.andersonfonseka.wr.action.WebAction;
import com.andersonfonseka.wr.components.Button;
import com.andersonfonseka.wr.components.Container;
import com.andersonfonseka.wr.components.FormGroup;
import com.andersonfonseka.wr.components.InputText;
import com.andersonfonseka.wr.components.SelectItem;
import com.andersonfonseka.wr.components.SelectList;
import com.andersonfonseka.wr.components.WebForm;
import com.andersonfonseka.wr.components.WebPage;
import com.andersonfonseka.wr.demo.DemoNavBar;
import com.andersonfonseka.wr.demo.model.User;
import com.andersonfonseka.wr.demo.model.UserRepository;
import com.andersonfonseka.wr.demo.pages.validator.RegistrationPasswordMatchValidator;
import com.andersonfonseka.wr.navigation.WebNavigation;
import com.andersonfonseka.wr.style.Color;
import com.andersonfonseka.wr.validator.RequiredValidator;

public class UserDetailPage extends WebPage {

	private UserRepository userRepository;

	private InputText txId;

	private InputText txFirstName;

	private InputText txLastName;

	private InputText txEmail;

	private SelectList roleList;

	private InputText txUsername;

	private InputText txPassword;

	public UserDetailPage() {
		super("Users");
		setNavBar(new DemoNavBar());
		
		userRepository = UserRepository.getInstance();
	}

	public WebForm createForm() {

		WebForm webForm = new WebForm(getMessageBundle().getMessage("user.detail"));

		FormGroup[] formGroup = new FormGroup[7];
		for (int i = 0; i < formGroup.length; i++) {
			formGroup[i] = new FormGroup();
		}

		Container container = new Container();

		txId = new InputText("txId", InputText.HIDDEN);
		webForm.add(txId);

		txFirstName = new InputText("txFirstName", InputText.TEXT, getMessageBundle().getMessage("user.firstname"));
		txFirstName.addValidator(new RequiredValidator());
		formGroup[0].add(txFirstName);

		txLastName = new InputText("txLastName", InputText.TEXT, getMessageBundle().getMessage("user.lastname"));
		txLastName.addValidator(new RequiredValidator());
		formGroup[1].add(txLastName);

		container.add(formGroup[0]);
		container.add(formGroup[1]);

		webForm.add(container);

		roleList = new SelectList("selRole", getMessageBundle().getMessage("user.role"));
		roleList.add(new SelectItem("ADMIN", "1"));
		roleList.add(new SelectItem("GUEST", "2"));
		roleList.addValidator(new RequiredValidator());
		formGroup[2].add(roleList);
		webForm.add(formGroup[2]);

		txEmail = new InputText("txEmail", InputText.TEXT, getMessageBundle().getMessage("user.email"));
		txEmail.addValidator(new RequiredValidator());
		formGroup[3].add(txEmail);
		webForm.add(formGroup[3]);

		txUsername = new InputText("txUsername", InputText.TEXT, getMessageBundle().getMessage("login.username"));
		txUsername.addValidator(new RequiredValidator());
		formGroup[4].add(txUsername);
		webForm.add(formGroup[4]);

		txPassword = new InputText("txPassword", InputText.PASSWORD, getMessageBundle().getMessage("login.password"));
		txPassword.addValidator(new RequiredValidator());
		formGroup[5].add(txPassword);
		webForm.add(formGroup[5]);

		InputText txPasswordRetype = new InputText("txPasswordRetype", InputText.PASSWORD, getMessageBundle().getMessage("user.password.retype"));
		txPasswordRetype.addValidator(new RequiredValidator());
		formGroup[6].add(txPasswordRetype);
		webForm.add(formGroup[6]);

		Button btnUpdate = new Button("btnUpdate", getMessageBundle().getMessage("button.update"), Button.SUBMIT, new UpdateAction());
		webForm.add(btnUpdate);

		Button btnCancel = new Button("btnCancel", getMessageBundle().getMessage("button.back"), Button.SUBMIT, new UsersPageAction(), true, Color.SECONDARY);
		webForm.add(btnCancel);

		webForm.addValidator(new RegistrationPasswordMatchValidator());
		
		return webForm;
	}

	public class UpdateAction extends WebAction {

		@Override
		public WebPage execute(Map<String, String[]> params) {

			User user = new User();
			user.setId(params.get("txId")[0]);
			user.setFirstName(params.get("txFirstName")[0]);
			user.setLastName(params.get("txLastName")[0]);
			user.setUserName(params.get("txUsername")[0]);
			user.setPassword(params.get("txPassword")[0]);
			user.setRole(params.get("selRole")[0]);
			user.setEmail(params.get("txEmail")[0]);

			userRepository.saveOrUpdate(user);

			return WebNavigation.gotoPage(UsersPage.class);
		}
	}

	public class UsersPageAction extends WebAction {

		@Override
		public WebPage execute(Map<String, String[]> params) {
			return WebNavigation.gotoPage(UsersPage.class);
		}
	}

	@Override
	public void onLoad(Map<String, String[]> params) {

		User user = userRepository.getUserById(params.get("usersGrid")[0]);

		if (null != user) {
			this.txId.setValue(user.getId());
			this.txFirstName.setValue(user.getFirstName());
			this.txLastName.setValue(user.getLastName());
			this.roleList.setValue(user.getRole());
			this.txUsername.setValue(user.getUserName());
			this.txPassword.setValue(user.getPassword());
			this.txEmail.setValue(user.getEmail());
		}

	}

}
