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
