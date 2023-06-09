package vttp2023.batch3.ssf.frontcontroller.utility;

import java.util.Map;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpSession;

@Component
public class AuthenticationUtility {
    
    public static void incrementLoginAttempts(HttpSession session, Map<String, Integer> loginAttempts, String username) {
        loginAttempts.computeIfPresent(username, (k, v) -> v += 1);
        session.setAttribute("loginAttempts", loginAttempts);
    }

    public static void resetLoginAttempts(HttpSession session, Map<String, Integer> loginAttempts, String username) {
        loginAttempts.computeIfPresent(username, (k, v) -> v = 0);
        session.setAttribute("loginAttempts", loginAttempts);
    }
}
