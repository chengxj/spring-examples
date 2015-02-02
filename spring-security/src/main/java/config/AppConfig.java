package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import security.CustomUserDetailsService;
import security.RequestParameterAuthenticationFilter;

@ComponentScan({"controller", "config"})
@EnableAutoConfiguration
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class AppConfig {
    @Autowired
    private ObjectPostProcessor<Object> objectPostProcessor;

    @Bean(name="customUserDetailsService")
    public UserDetailsService customUserDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean(name="preauthAuthProvider")
    public AuthenticationProvider preauthAuthProvider() {
        PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
        provider.setPreAuthenticatedUserDetailsService(
                new UserDetailsByNameServiceWrapper<PreAuthenticatedAuthenticationToken>(customUserDetailsService()));
        return provider;
    }

    @Bean(name="authenticationManager")
    public AuthenticationManager authenticationManager() throws Exception {
        return new AuthenticationManagerBuilder(objectPostProcessor)
                .authenticationProvider(preauthAuthProvider()).build();
    }

    @Bean(name="customFilter")
    public RequestParameterAuthenticationFilter customFilter() throws Exception {
        RequestParameterAuthenticationFilter filter = new RequestParameterAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManager());
        return filter;
    }

    @Bean(name="customFilterReg")
    public FilterRegistrationBean customFilterReg() throws Exception {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.addUrlPatterns("/auth/*");
        registrationBean.setFilter(customFilter());

        return registrationBean;
    }

    @Bean(name="preAuthenticatedProcessingFilterEntryPoint")
    public AuthenticationEntryPoint preAuthenticatedProcessingFilterEntryPoint() {
        return new Http403ForbiddenEntryPoint();
    }

    @Bean(name="exceptionTranslationFilter")
    public ExceptionTranslationFilter exceptionTranslationFilter() {
        return new ExceptionTranslationFilter(preAuthenticatedProcessingFilterEntryPoint());
    }

    public static void main(String[] args) {
        SpringApplication.run(AppConfig.class, args);
    }
}
