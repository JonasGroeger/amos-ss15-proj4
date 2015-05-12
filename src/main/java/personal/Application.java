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

@SpringBootApplication
public class Application extends WebMvcConfigurerAdapter {
	
	@Bean
	public LocaleResolver localeResolver() {
	    SessionLocaleResolver slr = new SessionLocaleResolver();
	    slr.setDefaultLocale(Locale.GERMANY);
	    return slr;
	}
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
	    LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
	    lci.setParamName("lang");
	    return lci;
	}
	public static void main(String[] args) {
		
		String lang;
		
		ApplicationContext context 
		= new ClassPathXmlApplicationContext("locale.xml");

	String name = context.getMessage("EmployeeForm.header", 
			null, Locale.UK);

	System.out.println("Customer name (English) : " + name);

	String namechinese = context.getMessage("EmployeeForm.header", 
			null, 
                                    Locale.SIMPLIFIED_CHINESE);
	System.out.println("Customer name (Chinese) : " + namechinese);
		SpringApplication.run(Application.class, args);
	}
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

}