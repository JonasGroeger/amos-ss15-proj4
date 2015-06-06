package de.fau.amos4.test.integration;

import de.fau.amos4.Application;
import de.fau.amos4.service.ClientService;
import de.fau.amos4.service.EmployeeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URL;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ActiveProfiles("test")
@WebAppConfiguration
@IntegrationTest({"server.port=0"})
public class ClientControllerTest
{
    @Value("${local.server.port}")
    private int port;
    private URL base;
    private RestTemplate template;

    @Mock
    private ClientService clientService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Before
    public void setUp() throws MalformedURLException
    {
        this.base = new URL("http://localhost:" + this.port + "/");
        this.template = new TestRestTemplate();
    }

    @Test
    public void testThatMainPageHasLogin() throws Exception
    {
        String path = this.base.toString() + "?lang=de";
        ResponseEntity<String> resp = template.getForEntity(path, String.class);

        assertThat(resp.getBody(), containsString("Anmelden"));
        assertThat(resp.getBody(), containsString("Passwort"));
        assertThat(resp.getBody(), containsString("Remember me"));
    }
}
