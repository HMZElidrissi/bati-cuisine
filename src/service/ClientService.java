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

    public Client createClient(String nom, String adresse, String telephone, boolean estProfessionnel) throws SQLException {
        Client client = new Client(nom, adresse, telephone, estProfessionnel);
        return clientRepository.createClient(client);
    }

    public Client getClientByNom(String nom) {
        return clientRepository.getClientByNom(nom);
    }
}
