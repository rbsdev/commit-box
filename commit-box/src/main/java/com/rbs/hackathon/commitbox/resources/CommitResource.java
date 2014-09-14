package com.rbs.hackathon.commitbox.resources;
import java.util.Base64;
import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.client.filter.HttpBasicAuthFilter;


@Path("/")
public class CommitResource {
	
	private static final String REPOSITORY_URL = "https://api.github.com/repos/rbsdev/commit-box/events";
	private String user;
	private String password;
	
	@GET
	@Path("/commit")
    @Produces(MediaType.APPLICATION_JSON)
    public Response commit() {
		Response response = Response.notModified().build();
		
		Client client = ClientBuilder.newClient();
		
		HttpBasicAuthFilter filter = new HttpBasicAuthFilter(user, password);
		
		Response githubResponse = client.register(filter)
				.target(REPOSITORY_URL)
				.request("application/json")
				.get();
		
		Date lastModified = githubResponse.getLastModified();
		
		if (Storage.getInstance().getLastModified() == null) {
			Storage.getInstance().setLastModified(lastModified);
		}
		
		System.out.println(githubResponse.getStatus());
		
		if (lastModified != null && lastModified.after(Storage.getInstance().getLastModified())) {
			
			System.out.println("Modified");
			
			Storage.getInstance().setLastModified(lastModified);
				
			response = Response.status(Status.FOUND).build();
		} else {
			System.out.println("Not Modified");
		}
		
		return response;
	}
	
	public CommitResource() {
		this.user = "henriqueso";
		this.password = new String(Base64.getDecoder().decode("IUdpdEh1Yi4x".getBytes()));
	}
	
}
