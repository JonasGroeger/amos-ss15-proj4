package de.fau.amos4.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter
{
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * Authenticate using the the {@link UserDetailsService} and a hashed password.
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception
    {
        // /css/**, /js/** and /images/** is done by Spring Boot Security
        web.ignoring().antMatchers("/fonts/**");
    }

    /**
     * This is the generic security configuration. Further detailed configuration can be provided
     * using i.e. @PreAuthorize on request mappings.
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.authorizeRequests()

                // Allow access to the front page.
                .antMatchers("/").permitAll()
                // Allow access to the register page.
                .antMatchers("/client/register").permitAll()
                // Allow access to the submit registration page.
                .antMatchers("/client/submit").permitAll()
                // Allow access to the confirm page.
                .antMatchers("/client/confirm").permitAll()
                //Allow access to FrontPage
                .antMatchers("/FrontPage").permitAll()
                .antMatchers("/FrontPageSubmit").permitAll()
                .antMatchers("/WrongToken").permitAll()
                .anyRequest().fullyAuthenticated()

                .and()

                // Login page at /login with email as username
                .formLogin().loginPage("/").loginProcessingUrl("/").defaultSuccessUrl("/client/list")
                    .usernameParameter("email").failureUrl("/?error").permitAll()

                .and()

                // Logout page at /logout with redirect to home on logout and cookie removal
                .logout().logoutUrl("/client/logout").deleteCookies("remember-me").logoutSuccessUrl("/").permitAll()

                .and()

                // Enable the "remember me" functionality (using a cookie).
                .rememberMe();
    }
}
