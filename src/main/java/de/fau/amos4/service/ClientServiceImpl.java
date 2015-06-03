package de.fau.amos4.service;

import de.fau.amos4.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    // More business functionality without just proxying the repository.
}
