package repository;

import model.Client;

import java.util.List;

public interface ClientRepository {
    Client createClient(Client client);
    void updateClient(Client client);
    void deleteClient(Client client);
    Client getClientById(Long id);
    Client getClientByNom(String nom);
    List<Client> getAllClients();
}
