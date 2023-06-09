package vttp2023.batch3.ssf.frontcontroller.services;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonObject;

@Service
public class AuthenticationService {

	private String AUTH_URL = "https://authservice-production-e8b2.up.railway.app/api/authenticate";
	// TODO: Task 2
	// DO NOT CHANGE THE METHOD'S SIGNATURE
	// Write the authentication method in here
	public boolean authenticate(String username, String password) throws Exception {
		JsonObject json = Json.createObjectBuilder()
			.add("username", username)
			.add("password", password)
			.build();

		RequestEntity<String> req = RequestEntity
			.post(AUTH_URL)
			.contentType(MediaType.APPLICATION_JSON)
			.header("Accept", MediaType.APPLICATION_JSON_VALUE)
			.body(json.toString(), String.class);

		RestTemplate template = new RestTemplate();

		ResponseEntity<String> resp = template.exchange(req, String.class);
		//System.out.println(resp.getStatusCode());
		//System.out.println(resp.getBody());
		
		if(resp.getStatusCode().is4xxClientError()) {
			return false;
		}

		return true;
	}

	// TODO: Task 3
	// DO NOT CHANGE THE METHOD'S SIGNATURE
	// Write an implementation to disable a user account for 30 mins
	public void disableUser(String username) {
	}

	// TODO: Task 5
	// DO NOT CHANGE THE METHOD'S SIGNATURE
	// Write an implementation to check if a given user's login has been disabled
	public boolean isLocked(String username) {
		return false;
	}
}
