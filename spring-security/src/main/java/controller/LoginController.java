package controller;

import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import security.TokenManager;

@RestController
@RequestMapping("/login")
public class LoginController {
    @RequestMapping(method = RequestMethod.GET)
    public String login(@RequestParam(value = "username", required = true) String username,
                        @RequestParam(value = "password", required = true) String password) {
        String salt = KeyGenerators.string().generateKey();
        StandardPasswordEncoder encoder = new StandardPasswordEncoder(salt);
        String token = encoder.encode(password);

        TokenManager.addToken(token, username);

        return token;
    }
}
