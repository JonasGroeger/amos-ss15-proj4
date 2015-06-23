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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import de.fau.amos4.model.Client;
import de.fau.amos4.service.ClientRepository;
import de.fau.amos4.service.ClientService;
import de.fau.amos4.service.ClientServiceImpl;
import de.fau.amos4.test.BaseIntegrationTest;
import de.fau.amos4.web.LoginFormController;

public class ClientEditTest extends BaseIntegrationTest {
	@Mock
    private final ClientService clientService = Mockito.spy(new ClientServiceImpl(null));
	
	@Mock
    private final ClientRepository clientRepository = Mockito.spy(ClientRepository.class);
	
    @InjectMocks
    private LoginFormController loginFormController;
	
	@Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
 
        mockMvc = MockMvcBuilders.standaloneSetup(loginFormController).build();
    }
	
    @Test
    @WithUserDetails("datev@example.com")
    public void testThatClientEditSubmitChangesPassword() throws Exception
    {
    	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    	Client client = new Client();
    	client.setPasswordHash(encoder.encode("datev"));
    	
    	Mockito.doReturn(client).when(clientService).getClientByEmail(Matchers.<String>any());
        Assert.assertEquals(client, clientService.getClientByEmail("dfjashf"));
    	
    	mockMvc.perform(post("/client/edit/submit")
        		.with(csrf())
        		.contentType(MediaType.APPLICATION_FORM_URLENCODED)
        		.param("NewPassword", "test")
        		.param("ConfirmPassword", "test")
        		.param("OldPassword", "datev")
        		.sessionAttr("client", client))
        		.andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/client/dashboard?m=profileChanged"));
    }
}
