package vttp2023.batch3.ssf.frontcontroller.services;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonObject;

@Service
public class AuthenticationService {

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	private String AUTH_URL = "https://authservice-production-e8b2.up.railway.app/api/authenticate";
	// DO NOT CHANGE THE METHOD'S SIGNATURE
	// Write the authentication method in here
	public ResponseEntity<String> authenticate(String username, String password) throws Exception {
		JsonObject json = Json.createObjectBuilder()
			.add("username", username)
			.add("password", password)
			.build();

		RequestEntity<String> req = RequestEntity
			.post(AUTH_URL)
			.contentType(MediaType.APPLICATION_JSON)
			.header("Accept", MediaType.APPLICATION_JSON_VALUE)
			.body(json.toString(), String.class);

		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<String> resp;
		//System.out.println(resp.getStatusCode());
		//System.out.println(resp.getBody());
		try {
			resp = restTemplate.exchange(req, String.class);
		} catch (HttpStatusCodeException e) {
			resp = ResponseEntity.status(e.getStatusCode()).build();
		}

		return resp;
	}

	// TODO: Task 3
	// DO NOT CHANGE THE METHOD'S SIGNATURE
	// Write an implementation to disable a user account for 30 mins
	public void disableUser(String username) {
		redisTemplate.opsForValue().setIfAbsent(username, "disabled", 30L, TimeUnit.MINUTES);
	}

	// TODO: Task 5
	// DO NOT CHANGE THE METHOD'S SIGNATURE
	// Write an implementation to check if a given user's login has been disabled
	public boolean isLocked(String username) {
		if(Optional.of(redisTemplate.opsForValue().get(username)).equals("disabled")) {
			return true;
		}
		return false;
	}
}
