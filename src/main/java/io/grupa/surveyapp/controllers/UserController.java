package io.grupa.surveyapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.grupa.surveyapp.entities.User;
import io.grupa.surveyapp.pojo.GenericResponse;
import io.grupa.surveyapp.services.UserService;
import io.grupa.surveyapp.util.PasswordValidator;

@RestController
@RequestMapping(path = "api")
public class UserController {
	
	
	@Autowired
	private UserService userService;
	
	@Autowired
	@Lazy
	private PasswordEncoder passwordEncoder;

	private PasswordValidator validator;
	
	
	@Autowired
	MessageSource messageSource;
	
	@PostMapping("/v1/auth/register")
	public GenericResponse newUser(@RequestBody User newUser) {
		
		
		// generate uuid for user
		String userUuid = java.util.UUID.randomUUID().toString();
				
		if (userService.findByEmail(newUser.getEmail().trim()).isPresent())
			return new GenericResponse(messageSource.getMessage("email.exist", null, LocaleContextHolder.getLocale()),
					false, "101");
		

		// strip spaces from phone number
		newUser.setPhoneNumber(newUser.getPhoneNumber().trim());

		if (userService.findByPhoneNumber(newUser.getPhoneNumber()).isPresent())
			return new GenericResponse(messageSource.getMessage("phone.exist", null, LocaleContextHolder.getLocale()),
					false, "101");

		validator = PasswordValidator.buildValidator(true, true, true, 3, 20);
		if (!validator.validatePassword(newUser.getPassword().trim()))
				return new GenericResponse(
						messageSource.getMessage("registration.password.invalid", null, LocaleContextHolder.getLocale()),
						false, "101");
		
			
		// hash user password
	    newUser.setPassword(passwordEncoder.encode(newUser.getPassword().trim()));
		newUser.setUuid(userUuid);
		newUser.setEmail(newUser.getEmail().trim());

		userService.save(newUser);
		
		return new GenericResponse(messageSource.getMessage("000", null, LocaleContextHolder.getLocale()), true, "000");
	}
}
