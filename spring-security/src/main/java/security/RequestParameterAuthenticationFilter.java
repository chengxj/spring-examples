package security;

import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;

import javax.servlet.http.HttpServletRequest;


public class RequestParameterAuthenticationFilter extends AbstractPreAuthenticatedProcessingFilter {
    private String principalRequestKey = "user";
    private String credentialsRequestKey = "key";

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        String principal = request.getParameter(principalRequestKey);

        if (principal == null) {
            throw new PreAuthenticatedCredentialsNotFoundException(principalRequestKey
                    + " parameter not found in request.");
        }

        return principal;
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        String credential = null;
        if (credentialsRequestKey != null) {
            credential = request.getParameter(credentialsRequestKey);
        }

        if (credential != null) {
            String owner = TokenManager.getTokenOwner(credential);
            if (owner == null || !owner.equals(getPreAuthenticatedPrincipal(request))) {
                credential = null;
            }
        }

        if (credential == null) {
            throw new PreAuthenticatedCredentialsNotFoundException(credentialsRequestKey
                    + " parameter is not correct.");
        }

        return credential;
    }

    public void setPrincipalRequestKey(String principalRequestKey) {
        this.principalRequestKey = principalRequestKey;
    }

    public void setCredentialsRequestKey(String credentialsRequestKey) {
        this.credentialsRequestKey = credentialsRequestKey;
    }
}
