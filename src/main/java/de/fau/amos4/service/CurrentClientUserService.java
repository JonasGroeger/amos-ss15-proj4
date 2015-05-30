package de.fau.amos4.service;

import de.fau.amos4.model.Client;
import de.fau.amos4.model.CurrentClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * This is used in the {@link de.fau.amos4.configuration.SecurityConfiguration} to retrieve a user.
 */
@Service
public class CurrentClientUserService implements UserDetailsService
{
    private final ClientService clientService;

    @Autowired
    public CurrentClientUserService(ClientService clientService)
    {
        this.clientService = clientService;
    }

    /**
     * Retrieve a user by its username (we use the email address for that).
     * @param email The username of the client.
     * @return Details about the user.
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException
    {
        Client client = clientService.getClientByEmail(email);
        if(client == null)
        {
            throw new UsernameNotFoundException(String.format("Client with email=%s was not found.", email));
        }
        return new CurrentClient(client);
    }
}
