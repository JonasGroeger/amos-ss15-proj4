package de.fau.amos4.test;

import de.fau.amos4.Application;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.net.MalformedURLException;
import java.net.URL;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles("test")
public abstract class BaseIntegrationTest
{
    @Autowired
    protected WebApplicationContext wac;

    protected MockMvc mockMvc;



    protected String createUrl(String path) throws MalformedURLException
    {
        return new URL("http://localhost" + path).toString();
    }
}
