package com.krishna.App7.Filters;

import java.io.IOException;
import java.util.logging.Logger;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;

public class RoleFilter implements ContainerRequestFilter
{
	private static final Logger logger=Logger.getLogger(ResponseLoggingFilter.class.getName());

	@Override
	public void filter(ContainerRequestContext rc) throws IOException
	{
//		String method=rc.getMethod();
//		String role=(String)rc.getProperty("role");
//		
//		if(method.equals("PUT")||method.equals("POST")||method.equals("DELETE"))
//		{
//			System.out.println(role);
//			if(!"admin".equals(role))
//			{
//				logger.warning("Unauthorized action");
//				rc.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("Unauthorized action").build());
//			}
//		}
		
	}

}
