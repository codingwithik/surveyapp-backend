package io.grupa.surveyapp.security;


import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import io.grupa.surveyapp.entities.User;
import io.grupa.surveyapp.util.Constant;


public class CustomUserDetails extends User implements UserDetails {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CustomUserDetails() {
		super();	
	}

	public CustomUserDetails(User user) {
		
        super(user);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

    	Collection<SimpleGrantedAuthority> c = new ArrayList<>();
    	c.add(new SimpleGrantedAuthority(Constant.ROLE_COORDINATOR));
    	return c;
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public String getUsername() {
        return super.getPhoneNumber();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return super.getEnable();
    }
   
    
}