package com.krishna.pikachu;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/tester")
public class Tester {

	
	@GET
	@Path("msg")
	@Produces(MediaType.TEXT_PLAIN)
	public String getmsg()
	{
		return "Hello";
	}
	
}
