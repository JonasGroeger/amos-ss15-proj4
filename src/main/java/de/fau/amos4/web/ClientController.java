package de.fau.amos4.web;

import de.fau.amos4.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles client related requests.
 */
@Controller
public class ClientController
{
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService)
    {
        this.clientService = clientService;
    }

    /**
     * Show the client login page.
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/", "/client/login"}, method = RequestMethod.GET)
    public String getClientLoginPage() throws Exception
    {
        return "ClientLogin";
    }
}
