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