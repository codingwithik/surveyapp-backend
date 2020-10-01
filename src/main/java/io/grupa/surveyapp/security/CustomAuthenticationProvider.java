package io.grupa.surveyapp.security;


import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import io.grupa.surveyapp.services.UserService;

public class CustomAuthenticationProvider extends DaoAuthenticationProvider {

	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	private UserService userService;
	
	
	@Transactional
	@Override
	public Authentication authenticate(Authentication auth) throws AuthenticationException {

		final String authorizationHeaderValue = request.getHeader("Authorization");
		String clientId = getClientId(authorizationHeaderValue);

		// get user details from service
		CustomUserDetails userDetails = (CustomUserDetails) this.retrieveUser(auth.getName(),
				(UsernamePasswordAuthenticationToken) auth);
		
		
		additionalAuthenticationChecks(userDetails, (UsernamePasswordAuthenticationToken) auth);

		if (userDetails.getEnable() != null)
			if (userDetails.getEnable() != true) {
				throw new BadCredentialsException(
						messageSource.getMessage("account.disabled", null, LocaleContextHolder.getLocale()));
			}

		if (clientId != null) {
			if (!clientId.equalsIgnoreCase("web-client")) {

				@SuppressWarnings({ "unchecked" })
				Map<String, String> map = (Map<String, String>) auth.getDetails();
				String username = map.get("username");
				
				System.out.println("ddddddddd...."+map);
				

			}else if(clientId.equalsIgnoreCase("web-client")) {
				if (!userDetails.getRole().equalsIgnoreCase("ROLE_ADMIN")) {
					throw new BadCredentialsException(
							messageSource.getMessage("user.not.found", null, LocaleContextHolder.getLocale()));
				}
			}
		}

		
		Object principalToReturn = userDetails;
		return createSuccessAuthentication(principalToReturn, auth, userDetails);
	}
	
	
	

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

		if (authentication.getCredentials() == null) {
			logger.debug("Authentication failed: no credentials provided");

			throw new BadCredentialsException(messages
					.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Password cant be null"));
		}

		String presentedPassword = authentication.getCredentials().toString();

		if (!this.getPasswordEncoder().matches(presentedPassword, userDetails.getPassword())) {
			logger.debug("Authentication failed: password does not match stored value");

			throw new BadCredentialsException(
					messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials",
							messageSource.getMessage("username.invalid", null, LocaleContextHolder.getLocale())));
		}
	}

	private String getClientId(String authorizationHeaderValue) {
		final String base64AuthorizationHeader = Optional.ofNullable(authorizationHeaderValue)
				.map(headerValue -> headerValue.substring("Basic ".length())).orElse("");

		if (!base64AuthorizationHeader.isEmpty()) {
			String decodedAuthorizationHeader = new String(Base64.getDecoder().decode(base64AuthorizationHeader),
					Charset.forName("UTF-8"));
			return decodedAuthorizationHeader.split(":")[0];
		}
		return null;
	}

}