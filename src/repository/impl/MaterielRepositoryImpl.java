package repository.impl;

import model.Materiel;
import model.Projet;
import repository.GenericJDBCRepository;
import repository.MaterielRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MaterielRepositoryImpl extends GenericJDBCRepository<Materiel> implements MaterielRepository {
    public MaterielRepositoryImpl() throws SQLException {
        super(Materiel.class, "materiels");
    }

    @Override
    protected Optional<Materiel> mapResultSetToModel(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String nom = resultSet.getString("nom");
        double tauxTVA = resultSet.getDouble("taux_tva");
        double coutUnitaire = resultSet.getDouble("cout_unitaire");
        double quantite = resultSet.getDouble("quantite");
        double coutTransport = resultSet.getDouble("cout_transport");
        double coefficientQualite = resultSet.getDouble("coefficient_qualite");
        Projet projet = new ProjetRepositoryImpl().findById(resultSet.getLong("projet_id")).orElse(null);
        return Optional.of(new Materiel(id, nom, tauxTVA, projet, coutUnitaire, quantite, coutTransport, coefficientQualite));
    }

    @Override
    protected Map<String, Object> mapModelData(Materiel model) {
        Map<String, Object> data = new HashMap<>();
        data.put("nom", model.getNom());
        data.put("taux_tva", model.getTauxTVA());
        data.put("cout_unitaire", model.getCoutUnitaire());
        data.put("quantite", model.getQuantite());
        data.put("cout_transport", model.getCoutTransport());
        data.put("coefficient_qualite", model.getCoefficientQualite());
        data.put("projet_id", model.getProjet().getId());
        return data;
    }

    @Override
    public void createMateriel(Materiel materiel) {
        this.save(mapModelData(materiel));
    }

    @Override
    public void addMaterielToProject(Long projetId, Long materielId) {
        Map<String, Object> data = new HashMap<>();
        data.put("projet_id", projetId);
        this.update(data, materielId);
    }
}
