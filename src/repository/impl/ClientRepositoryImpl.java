package repository.impl;

import model.Client;
import repository.ClientRepository;
import repository.GenericJDBCRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ClientRepositoryImpl extends GenericJDBCRepository<Client> implements ClientRepository {
    public ClientRepositoryImpl() throws SQLException {
        super(Client.class, "clients");
    }

    @Override
    public Client createClient(Client client) {
        return this.save(mapModelData(client));
    }

    @Override
    public void updateClient(Client client) {
        this.update(mapModelData(client), client.getId());
    }

    @Override
    public void deleteClient(Client client) {
        this.deleteById(client.getId());
    }

    @Override
    public Client getClientById(Long id) {
        return this.findById(id).orElse(null);
    }

    @Override
    public Client getClientByNom(String nom) {
        return this.findBy("nom", nom).orElse(null);
    }

    @Override
    public List<Client> getAllClients() {
        return List.of();
    }

    @Override
    protected Optional<Client> mapResultSetToModel(ResultSet resultSet) throws SQLException {
        return Optional.empty();
    }

    @Override
    protected Map<String, Object> mapModelData(Client model) {
        Map<String, Object> data = new HashMap<>();
        data.put("nom", model.getNom());
        data.put("adresse", model.getAdresse());
        data.put("telephone", model.getTelephone());
        data.put("estProfessionnel", model.isEstProfessionnel());
        return Map.of();
    }
}
