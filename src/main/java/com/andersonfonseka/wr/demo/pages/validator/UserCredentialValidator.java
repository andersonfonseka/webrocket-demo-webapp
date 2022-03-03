package com.andersonfonseka.wr.demo.pages.validator;

import java.util.Map;

import com.andersonfonseka.wr.demo.model.User;
import com.andersonfonseka.wr.demo.model.UserRepository;
import com.andersonfonseka.wr.util.BeantUtil;
import com.andersonfonseka.wr.validator.FormValidator;
import com.andersonfonseka.wr.validator.ValidatorMessage;

public class UserCredentialValidator extends FormValidator {
	
	private UserRepository userRepository;
	
	public UserCredentialValidator() {
		userRepository = UserRepository.getInstance();	// TODO Auto-generated constructor stub
	}


	@Override
	public ValidatorMessage doValidate(Map<String, String[]> params) {

		User user = userRepository.getUser(BeantUtil.getComponentValue(params, "txUserName"));
		
		if (null != user) {
			if (!user.getPassword().equals(BeantUtil.getComponentValue(params, "txPassword"))) {
				return new ValidatorMessage(getMessageBundle().getMessage("login.validator.usercredentials.error"), true);
			} else if (user.getPassword().equals(BeantUtil.getComponentValue(params, "txPassword"))) {
				return new ValidatorMessage(false);
			}
		}
		
		return new ValidatorMessage(getMessageBundle().getMessage("login.validator.usernotfound.error"), true);
		
	}

}
