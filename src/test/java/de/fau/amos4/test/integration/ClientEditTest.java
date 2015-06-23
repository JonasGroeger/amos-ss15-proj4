package de.fau.amos4.test.integration;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
import org.springframework.beans.factory.annotation.Autowired;
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
