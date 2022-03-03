package com.andersonfonseka.wr.demo.pages.users;

import java.util.ArrayList;
import java.util.List;
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
import com.andersonfonseka.wr.demo.pages.LoginPage;
import com.andersonfonseka.wr.demo.pages.validator.RegistrationPasswordMatchValidator;
import com.andersonfonseka.wr.message.MessageDialogConfirmation;
import com.andersonfonseka.wr.navigation.WebNavigation;
import com.andersonfonseka.wr.style.Color;
import com.andersonfonseka.wr.util.BeantUtil;
import com.andersonfonseka.wr.validator.RequiredValidator;
import com.andersonfonseka.wr.validator.ValidatorMessage;

public class UserRegistrationPage extends WebPage {

	private InputText txSourceHidden;

	private UserRepository userRepository;

	private WebForm webForm;

	public UserRegistrationPage() {
		super("Registration");
		setNavBar(new DemoNavBar());

		userRepository = UserRepository.getInstance();
	}

	public WebForm createForm() {

		webForm = new WebForm(getMessageBundle().getMessage("user.registration"));

		FormGroup[] formGroup = new FormGroup[6];
		for (int i = 0; i < formGroup.length; i++) {
			formGroup[i] = new FormGroup();
		}

		txSourceHidden = new InputText("txSourceHidden", InputText.HIDDEN);
		webForm.add(txSourceHidden);

		Container container = new Container();

		InputText txFirstName = new InputText("txFirstName", InputText.TEXT,
				getMessageBundle().getMessage("user.firstname"));
		txFirstName.addValidator(new RequiredValidator());
		formGroup[0].add(txFirstName);

		InputText txLastName = new InputText("txLastName", InputText.TEXT,
				getMessageBundle().getMessage("user.lastname"));
		txLastName.addValidator(new RequiredValidator());
		formGroup[1].add(txLastName);

		container.add(formGroup[0]);
		container.add(formGroup[1]);

		webForm.add(container);

		SelectList roleList = new SelectList("selRole", getMessageBundle().getMessage("user.role"));
		roleList.add(new SelectItem("ADMIN", "1"));
		roleList.add(new SelectItem("GUEST", "2"));
		roleList.addValidator(new RequiredValidator());
		formGroup[2].add(roleList);
		webForm.add(formGroup[2]);

		InputText txUsername = new InputText("txUsername", InputText.TEXT,
				getMessageBundle().getMessage("login.username"));
		txUsername.addValidator(new RequiredValidator());
		formGroup[3].add(txUsername);
		webForm.add(formGroup[3]);

		InputText txPassword = new InputText("txPassword", InputText.PASSWORD,
				getMessageBundle().getMessage("login.password"));
		txPassword.addValidator(new RequiredValidator());
		formGroup[4].add(txPassword);
		webForm.add(formGroup[4]);

		InputText txPasswordRetype = new InputText("txPasswordRetype", InputText.PASSWORD,
				getMessageBundle().getMessage("user.password.retype"));

		txPasswordRetype.addValidator(new RequiredValidator());

		formGroup[5].add(txPasswordRetype);
		webForm.add(formGroup[5]);

		Button btnSubmit = new Button("btnSubmit", getMessageBundle().getMessage("button.register"), Button.SUBMIT,
				new RegisterAction());
		Button btnCancel = new Button("btnCancel", getMessageBundle().getMessage("button.back"), Button.SUBMIT,
				new GridPageAction(), true, Color.SECONDARY);
		webForm.add(btnSubmit);
		webForm.add(btnCancel);

		webForm.addValidator(new RegistrationPasswordMatchValidator());

		return webForm;
	}

	public class RegisterAction extends WebAction {

		@Override
		public WebPage execute(Map<String, String[]> params) {

			List<ValidatorMessage> list = new ArrayList<ValidatorMessage>();
			ValidatorMessage message = new ValidatorMessage(getMessageBundle().getMessage("message.confirmation"), true);

			list.add(message);

			MessageDialogConfirmation confirmation = new MessageDialogConfirmation(list) {

				@Override
				public Button confirm() {
					return new Button("btnConfirmDialog", "Confirmar", new WebAction() {

						@Override
						public WebPage execute(Map<String, String[]> params) {

							User user = new User();
							user.setFirstName(BeantUtil.getComponentValue(params, "txFirstName"));
							user.setLastName(BeantUtil.getComponentValue(params, "txLastName"));
							user.setUserName(BeantUtil.getComponentValue(params, "txUsername"));
							user.setPassword(BeantUtil.getComponentValue(params, "txPassword"));
							user.setRole(BeantUtil.getComponentValue(params, "selRole"));

							userRepository.saveOrUpdate(user);

							WebPage result = null;

							if (txSourceHidden.getValue().equals("loginPage")) {
								result = WebNavigation.gotoPage(LoginPage.class);
							} else if (txSourceHidden.getValue().equals("usersPage")) {
								result = WebNavigation.gotoPage(UsersPage.class);
							}

							return result;
						}
					});
				}

				@Override
				public Button cancel() {
					// TODO Auto-generated method stub
					return new Button("btnCancelDialog", "Cancelar", new WebAction() {
						@Override
						public WebPage execute(Map<String, String[]> params) {

							WebPage result = WebNavigation.gotoPage(UserRegistrationPage.class);;
							return result;

						}
					});
				}

			};

			webForm.add(confirmation);
			return UserRegistrationPage.this;

		}
	}

	public class GridPageAction extends WebAction {

		@Override
		public WebPage execute(Map<String, String[]> params) {

			WebPage result = null;

			if (txSourceHidden.getValue().equals("loginPage")) {
				result = WebNavigation.gotoPage(LoginPage.class);
			} else if (txSourceHidden.getValue().equals("usersPage")) {
				result = WebNavigation.gotoPage(UsersPage.class);
			}

			return result;
		}
	}

	@Override
	public void onLoad(Map<String, String[]> params) {
		txSourceHidden.setValue(params.get("source")[0]);
	}

}
