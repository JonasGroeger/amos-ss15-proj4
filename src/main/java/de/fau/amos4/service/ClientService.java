package de.fau.amos4.service;

import de.fau.amos4.model.Client;

public interface ClientService
{
    Client getClientById(Long id);

    Client getClientByEmail(String email);

    Iterable<Client> getAllClients();

    Client create(Client client);
}
