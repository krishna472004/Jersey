package com.krishna.App7.Filters;

import java.io.IOException;

import java.util.logging.Logger;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;

public class ResponseLoggingFilter implements ContainerResponseFilter
{
	
	private static final Logger logger=Logger.getLogger(ResponseLoggingFilter.class.getName());

	@Override
	public void filter(ContainerRequestContext reqc, ContainerResponseContext resc)
			throws IOException 
	{
		//Long startTime=(Long) reqc.getProperty("startTime");
		
		//long duration=System.currentTimeMillis()-startTime;
	    //logger.info("Response sent in "+duration+" ms for "+reqc.getMethod()+" "+reqc.getUriInfo().getRequestUri()+" ,Response Status: "+resc.getStatus());
	    
		
	}

}
