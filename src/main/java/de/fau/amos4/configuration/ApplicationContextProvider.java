package de.fau.amos4.configuration;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/*
 * Sets the context for AppContext. Needed for access of locale within java Applications, i.e. the drop down classes Disabled, Sex... 
 */
@Configuration
public class ApplicationContextProvider implements ApplicationContextAware { 
	

    public void setApplicationContext(ApplicationContext ctx) throws BeansException { 

        // Wiring the ApplicationContext into a static method 
        AppContext.setApplicationContext(ctx); 
    } 
}
