package io.grupa.surveyapp.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter{

	   @Autowired
	   private AuthenticationManager authenticationManager;
	   
	   @Autowired
	   private DataSource dataSource;
	    
	  
	   
	   @Override
	   public void configure (ClientDetailsServiceConfigurer clients) throws Exception {
	   clients.inMemory ()
	       .withClient ("web-client")
	               .authorizedGrantTypes ("password", "authorization_code", "refresh_token", "implicit")
	               .authorities ("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT", "USER")
	               .scopes ("read", "write")
	               .autoApprove (true)     
	               .secret (passwordEncoder (). encode ("password"));
	   }
	   
	    @Override
	    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
	        security.tokenKeyAccess("permitAll()")
	                .checkTokenAccess("isAuthenticated()");
	    }
	   
	   @Bean
	   public PasswordEncoder passwordEncoder () {
		   
	        return new BCryptPasswordEncoder();
	       
	   }
	   
	   @Bean
	   @Primary
	   public DefaultTokenServices tokenServices() {
	       DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
	       defaultTokenServices.setTokenStore(tokenStore());
	       defaultTokenServices.setSupportRefreshToken(true);
	       return defaultTokenServices;
	   }
	   
	   
	   
	    @Override
	    public void configure (AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
	    	
	        endpoints.authenticationManager (authenticationManager).tokenStore(tokenStore ());
	        
	       
	    }
	    
	    
	    @Bean
	    public TokenStore tokenStore () {
	    	 
	    	return new JdbcTokenStore(dataSource);
	     }
	    
	
}
