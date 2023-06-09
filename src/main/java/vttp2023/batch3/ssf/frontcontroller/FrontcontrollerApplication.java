package vttp2023.batch3.ssf.frontcontroller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FrontcontrollerApplication {
	@Value("${spring.redis.host}")
    private static String redisHost;
    

    @Value("${spring.redis.port}")
    private static Optional<Integer> redisPort;
    

    @Value("${spring.redis.username}")
    private static String redisUsername;
    

    @Value("${spring.redis.password}")
    private static String redisPassword;

	public static void main(String[] args) {
		System.out.println(redisHost);
		System.out.println(redisPort);
		System.out.println(redisUsername);
		System.out.println(redisPassword);
		SpringApplication.run(FrontcontrollerApplication.class, args);
	}

}
