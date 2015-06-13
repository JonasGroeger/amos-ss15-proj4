package de.fau.amos4.test.integration.helper.security;

import de.fau.amos4.model.Client;
import de.fau.amos4.model.CurrentClient;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

/**
 * A factory that knows how to return security contexts. This is used with the {@link WithMockCustomUser} annotation
 * to mock an authenticated user in unit and integration tests.
 */
public class WithMockCustomUserSecurityContextFactory
        implements WithSecurityContextFactory<WithMockCustomUser>
{
    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser customUser)
    {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        // A empty user that is NOT in the database.
        Client mockClient = createClient(customUser);

        CurrentClient principal = new CurrentClient(mockClient);
        Authentication auth = new UsernamePasswordAuthenticationToken(
                principal, principal.getPassword(), principal.getAuthorities());
        context.setAuthentication(auth);
        return context;
    }

    /**
     * Creates a client that has the details from the customUser.
     *
     * @param customUser
     * @return A (otherwise empty) client.
     */
    private Client createClient(WithMockCustomUser customUser)
    {
        // Create an empty client that is NOT in the database and populate it.
        Client client = new Client();
        client.setEmail(customUser.email());
        client.setPasswordHash(new BCryptPasswordEncoder().encode(customUser.password()));
        client.setRole(customUser.role());
        return client;
    }
}