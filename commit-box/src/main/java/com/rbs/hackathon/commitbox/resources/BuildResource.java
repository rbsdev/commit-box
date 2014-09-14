package com.rbs.hackathon.commitbox.resources;

import java.util.Base64;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.client.filter.HttpBasicAuthFilter;
import org.json.JSONException;
import org.json.JSONObject;

@Path("/")
public class BuildResource {
	private String user;
	private String password;
	private static String JENKINS_API = "http://jenkins.rbs.com.br/job/DEV_lib-rbs-kissmetrics-api/lastBuild/api/json?pretty=true";

	@GET
	@Path("/build")
    @Produces(MediaType.APPLICATION_JSON)
    public Response build() throws JSONException {
		Response response = null;
		
		Client client = ClientBuilder.newClient();
		
		HttpBasicAuthFilter filter = new HttpBasicAuthFilter(user, password);
		client.register(filter);
		
		String jenkinsResponse = client.target(JENKINS_API).request("application/json").get(String.class);
		
		JSONObject obj = new JSONObject(jenkinsResponse);
		String result = obj.getString("result");
		String lastBuildNumber = obj.getString("number");
		boolean building = "true".equals(obj.getString("building"));
		
		System.out.println(lastBuildNumber + " " + result);
		
		if (Storage.getInstance().getLastBuildNumber() == null) {
			Storage.getInstance().setLastBuildNumber(lastBuildNumber);
		}
		
		if (!building && lastBuildNumber != null && !lastBuildNumber.equals(Storage.getInstance().getLastBuildNumber())) {
			
			System.out.println("Modified");
			
			Storage.getInstance().setLastBuildNumber(lastBuildNumber);
				
			response = Response.ok(result).build();
		} else {
			System.out.println("Not Modified");
			
			response = Response.notModified(result).build();
		}
		
		return response;
	}
	
	public BuildResource() {
		this.user = "henrique_oliveira";
		this.password = new String(Base64.getDecoder().decode("IWxpbnV4LjI=".getBytes()));
	}
}
