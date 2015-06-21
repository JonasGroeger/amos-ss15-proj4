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
import de.fau.amos4.util.EmailSender;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
public class ClientServiceImpl implements ClientService
{
    private final ClientRepository clientRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository)
    {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client getClientById(Long id)
    {
        return this.clientRepository.findOne(id);
    }

    @Override
    public Client getClientByEmail(String email)
    {
        return this.clientRepository.findOneByEmail(email);
    }

    @Override
    public Iterable<Client> getAllClients()
    {
        return this.clientRepository.findAll();
    }

    @Override
    public Client create(Client client)
    {
        return clientRepository.save(client);
    }

    @Override
    public void generateNewPassword(Client client)
    {
        String newPassword = RandomStringUtils.random(8, "ABCDEFGHJKLMNPQRSTUVWXYZ23456789");
        String newPasswordHash = new BCryptPasswordEncoder().encode(newPassword);

        try
        {
            EmailSender postman = new EmailSender();
            postman.SendEmail(client.getEmail(), "PersonalFragebogen 2.0", "Your new password is: " + newPassword);
        } catch (MessagingException e) {
            e.printStackTrace();
            return;
        }

        // Having the return in the catch block, we only save changes when the E-Mail could have successfully been sent
        client.setPasswordHash(newPasswordHash);
        clientRepository.save(client);
    }
}
