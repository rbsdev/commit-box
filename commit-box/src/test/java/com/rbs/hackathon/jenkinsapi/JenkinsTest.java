package com.rbs.hackathon.jenkinsapi;

import java.util.Base64;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.glassfish.jersey.client.filter.HttpBasicAuthFilter;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

public class JenkinsTest {
	private String user;
	private String password;
	private static String JENKINS_API = "http://jenkins.rbs.com.br/job/DEV_lib-rbs-kissmetrics-api/lastBuild/api/json?pretty=true";
	
	@Test
	public void test() throws JSONException {
		Client client = ClientBuilder.newClient();
		
		HttpBasicAuthFilter filter = new HttpBasicAuthFilter(user, password);
		client.register(filter);
		
		String response = client.target(JENKINS_API).request("application/json").get(String.class);
		
		JSONObject obj = new JSONObject(response);
		String result = obj.getString("result");
		
		System.out.println(result);
	}

	@Before
	public void setup() {
		this.user = "henrique_oliveira";
		this.password = new String(Base64.getDecoder().decode("IWxpbnV4LjI=".getBytes()));
	}
}
