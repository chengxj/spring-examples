package config;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.util.StringUtils;

public class StatelessRealm extends AuthorizingRealm {
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof StatelessToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        if (username.equals("admin")) {
            authorizationInfo.addRole("admin");
        } else if (username.equals("guest")) {
            authorizationInfo.addStringPermission("user:view");
        }

        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        StatelessToken statelessToken = (StatelessToken) token;

        String key = statelessToken.getKey();
        if (!StringUtils.isEmpty(key) && TokenManager.getTokenOwner(key) != null) {
            String username = TokenManager.getTokenOwner(key);
            return new SimpleAuthenticationInfo(username, key, getName());
        }

        return null;
    }
}
