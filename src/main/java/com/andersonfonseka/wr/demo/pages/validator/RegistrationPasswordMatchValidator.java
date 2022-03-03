package com.andersonfonseka.wr.demo.pages.validator;

import java.util.Map;

import com.andersonfonseka.wr.util.BeantUtil;
import com.andersonfonseka.wr.validator.FormValidator;
import com.andersonfonseka.wr.validator.ValidatorMessage;

public class RegistrationPasswordMatchValidator extends FormValidator {
	
	
	public RegistrationPasswordMatchValidator() {}


	@Override
	public ValidatorMessage doValidate(Map<String, String[]> params) {

		if (! BeantUtil.getComponentValue(params, "txPasswordRetype").equals(params.get("txPassword")[0])) {
			return new ValidatorMessage(getMessageBundle().getMessage("register.password.mismatch"), true);
		} else {
			return new ValidatorMessage(false);
		}
		
	}

}
