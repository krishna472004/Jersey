package com.krishna.App7.Filters;

import java.io.IOException;


import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;

public class RequestLoggingFilter implements ContainerRequestFilter
{

	private static final Logger logger=Logger.getLogger(RequestLoggingFilter.class.getName());
	
	@Override
	public void filter(ContainerRequestContext reqc) throws IOException 
	{
		String user=reqc.getHeaderString("username");
		URI path=reqc.getUriInfo().getRequestUri();
		String method=reqc.getMethod();

		//reqc.setProperty("startTime",System.currentTimeMillis());
		
		System.out.println();
		logger.log(Level.INFO,"Incoming Request Method: "+method+", Path: "+path+", User: "+user);
		
	}
	
}


