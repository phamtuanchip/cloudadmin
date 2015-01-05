package com.cloud.admin.rest;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.cloud.admin.security.TokenUtils;
import com.cloud.admin.tranfer.TokenTransfer;
import com.cloud.admin.tranfer.UserTransfer;


@Component
@Path("/user")
public class UserResource {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	@Qualifier("authenticationManager")
	private AuthenticationManager authManager;

/**
 * Retrieve currently logged-in user
 * Return a Transfer containing username and role
 * 
 * **/
	
	@GET
	@Path("info")
	@Produces(MediaType.APPLICATION_JSON)
	public UserTransfer getUser()
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object principal = authentication.getPrincipal();
		if (principal instanceof String && ((String) principal).equals("anonymousUser")) {
			throw new WebApplicationException(401);
		}
		UserDetails userDetails = (UserDetails) principal;
		
		return new UserTransfer(userDetails.getUsername(), this.getUserRole(userDetails));
	}
	
	/**
	 * Authenticates a user and creates an authentication token.
	 * 
	 * @param username
	 *            The name of the user.
	 * @param password
	 *            The password of the user.
	 * @return A transfer containing the authentication token.
	 */
	@Path("authenticate")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public TokenTransfer authenticate(@QueryParam("username") String username, @QueryParam("password") String password)
	{
		UsernamePasswordAuthenticationToken authenticationToken =
				new UsernamePasswordAuthenticationToken(username, password);
		Authentication authentication = this.authManager.authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		/*
		 * Reload user as password of authentication principal will be null after authorization and
		 * password is needed for token generation
		 */
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
		
		return new TokenTransfer(TokenUtils.createToken(userDetails));
	}
	@Path("logout")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public TokenTransfer logout(){
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object principal = authentication.getPrincipal();
		
		if (principal instanceof String && ((String) principal).equals("anonymousUser")) {
			throw new WebApplicationException(401);
		}
		SecurityContextHolder.clearContext();
		
		return new TokenTransfer(TokenUtils.createInvalidToken());
			
	}


	private String getUserRole(UserDetails userDetails)
	{
		String role = "";
		
		for (GrantedAuthority authority : userDetails.getAuthorities()) {
			role += authority.getAuthority();
		}

		return role;
	}


}
