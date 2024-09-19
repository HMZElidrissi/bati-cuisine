package service;

import model.Client;
import repository.ClientRepository;
import repository.impl.ClientRepositoryImpl;

import java.sql.SQLException;

public class ClientService {
    ClientRepository clientRepository;

    public ClientService() throws SQLException {
        this.clientRepository = new ClientRepositoryImpl();
    }

    public Client createClient(Client client) throws SQLException {
        return clientRepository.createClient(client);
    }

    public Client getClientByNom(String nom) {
        return clientRepository.getClientByNom(nom);
    }
}
