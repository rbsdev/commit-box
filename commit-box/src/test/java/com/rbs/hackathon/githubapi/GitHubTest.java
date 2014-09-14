package com.rbs.hackathon.githubapi;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

import org.junit.Test;

public class GitHubTest {

	private static final String REPOSITORY_URL = "https://api.github.com/repos/henriqueso";
	private static final String EVENTS = "/Algorithms/events";

	@Test
	public void test() {
		Client client = ClientBuilder.newClient();
		String response = client.target(REPOSITORY_URL + EVENTS).request("application/json").get(String.class);
		
		System.out.println(response);
	}

}
