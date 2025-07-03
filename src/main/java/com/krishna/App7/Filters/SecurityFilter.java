package com.krishna.App7.Filters;

import java.io.IOException;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;

public class SecurityFilter implements ContainerResponseFilter{

	@Override
	public void filter(ContainerRequestContext reqc, ContainerResponseContext resc)
			throws IOException 
	{
		
//		 resc.getHeaders().add("Cache-Control","no-store");
		
	}

}
