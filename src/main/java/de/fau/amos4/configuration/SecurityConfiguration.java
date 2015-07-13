/**
 * Personalfragebogen 2.0. Revolutionize form data entry for taxation and
 * other purposes.
 * Copyright (C) 2015 Attila Bujaki, Werner Sembach, Jonas Gröger, Oswaldo
 *     Bejarano, Ardhi Sutadi, Nikitha Mohan, Benedikt Rauh
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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
                // Allow access to the forgot password page.
                .antMatchers("/client/forgotPassword").permitAll()
                //Allow access to FrontPage
                .antMatchers("/employee/token").permitAll()
                .antMatchers("/employee/token/submit").permitAll()
                .antMatchers("/employee/token/wrong").permitAll()
                .antMatchers("/employee/token/preview").permitAll()
                .antMatchers("/employee/confirm").permitAll()
                .antMatchers("/employee/edit/confirm").permitAll()
                .antMatchers("/employee/edit/submit").permitAll()
                .antMatchers("/employee/download/text").permitAll()
                .antMatchers("/employee/download/zip").permitAll()
                .antMatchers("/employee/edit").permitAll()
                .antMatchers("/employee/edit/submit").permitAll()
                .anyRequest().fullyAuthenticated()
                
                .and()
                
                // Login page at /login with email as username
                .formLogin().loginPage("/").loginProcessingUrl("/").defaultSuccessUrl("/client/dashboard")
                    .usernameParameter("email").failureUrl("/?m=invalid").permitAll()
                
                .and()
                
                // Logout page at /logout with redirect to home on logout and cookie removal
                .logout().logoutUrl("/client/logout").deleteCookies("remember-me").logoutSuccessUrl("/").permitAll()
                
                .and()
                
                // Enable the "remember me" functionality (using a cookie).
                .rememberMe();
    }
}
