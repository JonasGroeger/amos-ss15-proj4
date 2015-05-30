package de.fau.amos4.model;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

/**
 * This class handles the mapping between the Spring Security concept of a {@link User} and the application specific
 * concept of a user ({@link Client}).
 */
public class CurrentClient extends User
{
    private Client client;

    public CurrentClient(Client client)
    {
        super(client.getEmail(), client.getPasswordHash(), AuthorityUtils.createAuthorityList(client.getRole().toString()));
        this.client = client;
    }

    public Client getClient()
    {
        return this.client;
    }

    public Long getId()
    {
        return client.getId();
    }

    public ClientRole getRole()
    {
        return client.getRole();
    }

    // TODO: Override isAccountNonLocked delegating to client.isActivated()
}
