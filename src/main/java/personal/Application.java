package personal;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

@SpringBootApplication
public class Application extends SpringBootServletInitializer
{


	/**
	 * Configure the application. Normally all you would need to do it add sources (e.g.
	 * config classes) because other settings have sensible defaults. You might choose
	 * (for instance) to add default command line arguments, or set an active Spring
	 * profile.
	 *
	 * @param builder a builder for the application context
	 * @return the application builder
	 * @see org.springframework.boot.builder.SpringApplicationBuilder
	 */
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder)
	{
		return builder.sources(Application.class);
	}


	public static void main(String[] args) {
	
		SpringApplication.run(Application.class, args);
	}
	


}
