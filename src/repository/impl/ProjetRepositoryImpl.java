package repository.impl;

import model.*;
import repository.ProjetRepository;
import repository.GenericJDBCRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

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

    @Override
    public List<Materiel> getMaterielsByProjectId(Long projectId) throws SQLException {
        String sql = "SELECT * FROM materiels WHERE projet_id = ?";
        List<Materiel> materiels = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, projectId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Materiel materiel = new Materiel(
                            rs.getLong("id"),
                            rs.getString("nom"),
                            rs.getDouble("taux_tva"),
                            null, // We'll set the project later
                            rs.getDouble("cout_unitaire"),
                            rs.getDouble("quantite"),
                            rs.getDouble("cout_transport"),
                            rs.getDouble("coefficient_qualite")
                    );
                    materiels.add(materiel);
                }
            }
        }
        return materiels;
    }

    @Override
    public List<MainDoeuvre> getMainDoeuvreByProjectId(Long projectId) throws SQLException {
        String sql = "SELECT * FROM mains_doeuvre WHERE projet_id = ?";
        List<MainDoeuvre> mainsDoeuvre = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, projectId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    MainDoeuvre mainDoeuvre = new MainDoeuvre(
                            rs.getLong("id"),
                            rs.getString("nom"),
                            rs.getDouble("taux_tva"),
                            null, // We'll set the project later
                            rs.getDouble("taux_horaire"),
                            rs.getDouble("heures_travail"),
                            rs.getDouble("productivite_ouvrier")
                    );
                    mainsDoeuvre.add(mainDoeuvre);
                }
            }
        }
        return mainsDoeuvre;
    }

    @Override
    public List<Projet> findAll() throws SQLException {
        return this.findAll(new HashMap<>());
    }
}
