package vttp2023.batch3.ssf.frontcontroller.respositories;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AuthenticationRepository {

	@Autowired
	RedisTemplate<String, String> redisTemplate;
	// TODO Task 5
	// Use this class to implement CRUD operations on Redis
	public void setUserStatusToDisabled(String username) {
		redisTemplate.opsForValue().setIfAbsent(username, "disabled", 30L, TimeUnit.MINUTES);
	}

	public boolean isUserLocked(String username) {
		if(Optional.ofNullable(redisTemplate.opsForValue().get(username)).isPresent()) {
			if(Optional.of(redisTemplate.opsForValue().get(username)).get().equals("disabled"))return true;
		}
		return false;
	}

	public void setUserToAuthenticated(String username) {
		redisTemplate.opsForValue().setIfAbsent(username, "authenticated", 30L, TimeUnit.MINUTES);
	}

	public boolean isUserAuthenticated(String username) {
		if(Optional.ofNullable(redisTemplate.opsForValue().get(username)).isPresent()) {
			if(Optional.of(redisTemplate.opsForValue().get(username)).get().equals("authenticated")) return true;
		}
		return false;
	}

	public void deleteUserRecord(String username) {
		redisTemplate.opsForValue().getAndExpire(username, 0, TimeUnit.SECONDS);
	}
}
