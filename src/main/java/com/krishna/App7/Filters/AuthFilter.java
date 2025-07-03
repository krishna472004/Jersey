package com.krishna.App7.Filters;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;

public class AuthFilter implements ContainerRequestFilter
{

	private static final Logger logger=LoggerFactory.getLogger(AuthFilter.class);
	
	@Override
	public void filter(ContainerRequestContext rc) throws IOException 
	{
		
//		String user=rc.getHeaderString("username");
//		String token=rc.getHeaderString("Authorization");
//		
//		if(token!=null&&token.equals("secret"))
//		{
//			if(user!=null&&user.equals("admin1"))
//			{
//				rc.setProperty("role", "admin");
//			}
//			else
//			{
//				rc.setProperty("role", "user");
//			}
//		}
//		else
//		{
//			logger.error("Invalid token");
//			rc.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("Invalid token").build());
//		}
		
	}
	
}
