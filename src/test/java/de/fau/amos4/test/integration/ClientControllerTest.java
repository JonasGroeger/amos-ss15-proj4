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
package de.fau.amos4.test.integration;

import de.fau.amos4.Application;
import de.fau.amos4.service.ClientService;
import de.fau.amos4.service.EmployeeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URL;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ActiveProfiles("test")
@WebIntegrationTest(randomPort = true)
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
        this.base = new URL("http://localhost:" + this.port);
        this.template = new TestRestTemplate();
    }

    @Test
    public void testThatMainPageHasLogin() throws Exception
    {
        String path = this.base.toString() + "/?lang=de";
        ResponseEntity<String> resp = template.getForEntity(path, String.class);

        assertTrue(resp.getStatusCode().is2xxSuccessful());
        assertThat(resp.getBody(), containsString("Anmelden"));
        assertThat(resp.getBody(), containsString("Passwort"));
        assertThat(resp.getBody(), containsString("Remember me"));
    }

    @Test
    public void testThatRegistrationPageIsAvailable()
    {
        String path = this.base.toString() + "/client/register?lang=de";
        ResponseEntity<String> resp = template.getForEntity(path, String.class);

        assertTrue(resp.getStatusCode().is2xxSuccessful());
        assertThat(resp.getBody(), containsString("Registrieren"));
        assertThat(resp.getBody(), containsString("Passwort"));
    }
}
