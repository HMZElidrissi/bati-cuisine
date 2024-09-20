package repository.impl;

import model.MainDoeuvre;
import model.Projet;
import repository.GenericJDBCRepository;
import repository.MainDoeuvreRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MainDoeuvreRepositoryImpl extends GenericJDBCRepository<MainDoeuvre> implements MainDoeuvreRepository {
    public MainDoeuvreRepositoryImpl() throws SQLException {
        super(MainDoeuvre.class, "materiels");
    }

    @Override
    protected Optional<MainDoeuvre> mapResultSetToModel(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String nom = resultSet.getString("nom");
        double tauxTVA = resultSet.getDouble("taux_tva");
        double tauxHoraire = resultSet.getDouble("taux_horaire");
        double heuresTravail = resultSet.getDouble("heures_travail");
        double productiviteOuvrier = resultSet.getDouble("productivite_ouvrier");
        Projet projet = new ProjetRepositoryImpl().findById(resultSet.getLong("projet_id")).orElse(null);
        return Optional.of(new MainDoeuvre(id, nom, tauxTVA, projet, tauxHoraire, heuresTravail, productiviteOuvrier));
    }

    @Override
    protected Map<String, Object> mapModelData(MainDoeuvre model) {
        Map<String, Object> data = new HashMap<>();
        data.put("nom", model.getNom());
        data.put("taux_tva", model.getTauxTVA());
        data.put("taux_horaire", model.getTauxHoraire());
        data.put("heures_travail", model.getHeuresTravail());
        data.put("productivite_ouvrier", model.getProductiviteOuvrier());
        data.put("projet_id", model.getProjet().getId());
        return data;
    }

    @Override
    public void createMainDoeuvre(MainDoeuvre mainDoeuvre) {
        this.save(mapModelData(mainDoeuvre));
    }
}
