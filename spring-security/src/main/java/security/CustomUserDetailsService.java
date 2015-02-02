package security;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;

public class CustomUserDetailsService implements UserDetailsService {
    private String USER_ADMIN = "admin";
    private String USER = "guest";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (USER_ADMIN.equals(username)) {
            return createuserDetails(username, AuthorityUtils.createAuthorityList("ROLE_ADMIN"));
        } else if (USER.equals(username)) {
            return createuserDetails(username, AuthorityUtils.createAuthorityList("ROLE_USER"));
        } else {
            throw new UsernameNotFoundException("User " + username + " is not found");
        }
    }

    protected UserDetails createuserDetails(String username, Collection<? extends GrantedAuthority> authorities) {
        return new User(username, "N/A", true, true, true, true, authorities);
    }
}
