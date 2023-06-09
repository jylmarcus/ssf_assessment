package vttp2023.batch3.ssf.frontcontroller.respositories;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AuthenticationRepository {

	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	// TODO Task 5
	// Use this class to implement CRUD operations on Redis
	public void setUserStatusToDisabled(String username) {
		redisTemplate.opsForValue().setIfAbsent(username, "disabled", 30L, TimeUnit.MINUTES);
	}

	public boolean readUserStatus(String username) {
		if(Optional.of(redisTemplate.opsForValue().get(username)).equals("disabled")) {
			return true;
		}
		return false;
	}
}
