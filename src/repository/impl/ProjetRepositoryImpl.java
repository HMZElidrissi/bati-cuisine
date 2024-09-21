package repository.impl;

import model.Client;
import model.Composant;
import model.EtatProjet;
import model.Projet;
import repository.ProjetRepository;
import repository.GenericJDBCRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ProjetRepositoryImpl extends GenericJDBCRepository<Projet> implements ProjetRepository {
    public ProjetRepositoryImpl() throws SQLException {
        super(Projet.class, "projets");
    }

    @Override
    protected Optional<Projet> mapResultSetToModel(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String nom = resultSet.getString("nom_projet");
        Double margeBeneficiaire = resultSet.getDouble("marge_beneficiaire");
        Double coutTotal = resultSet.getDouble("cout_total");
        EtatProjet etatProjet = EtatProjet.valueOf(resultSet.getString("etat_projet"));
        Client client = new ClientRepositoryImpl().findById(resultSet.getLong("client_id")).orElse(null);
        return Optional.of(new Projet(id, nom, margeBeneficiaire, coutTotal, etatProjet, client));
    }

    @Override
    protected Map<String, Object> mapModelData(Projet model) {
        Map<String, Object> map = new HashMap<>();
        map.put("nom_projet", model.getNomProjet());
        map.put("marge_beneficiaire", model.getMargeBeneficiaire());
        map.put("cout_total", model.getCoutTotal());
        map.put("etat_projet", model.getEtatProjet().name());
        map.put("client_id", model.getClient().getId());
        return map;
    }

    @Override
    public Projet createProject(Projet project) {
        return this.save(mapModelData(project));
    }

    @Override
    public void applyMargeBeneficiaire(Projet project, double margeBeneficiaire) throws SQLException {
        Map<String, Object> data = new HashMap<>();
        data.put("marge_beneficiaire", margeBeneficiaire);
        this.update(data, project.getId());
    }

    @Override
    public void setCoutTotal(Projet project, double coutTotal) throws SQLException {
        Map<String, Object> data = new HashMap<>();
        data.put("cout_total", coutTotal);
        this.update(data, project.getId());
    }
}
