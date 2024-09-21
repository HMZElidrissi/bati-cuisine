package repository.impl;

import model.Devis;
import repository.DevisRepository;
import repository.GenericJDBCRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DevisRepositoryImpl extends GenericJDBCRepository<Devis> implements DevisRepository {
    public DevisRepositoryImpl() throws SQLException {
        super(Devis.class, "devis");
    }

    @Override
    public Devis createDevis(Devis devis) {
        return this.save(mapModelData(devis));
    }

    @Override
    protected Optional<Devis> mapResultSetToModel(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        double montantEstime = resultSet.getDouble("montant_estime");
        LocalDate dateEmission = resultSet.getDate("date_emission").toLocalDate();
        LocalDate dateValidite = resultSet.getDate("date_validite").toLocalDate();
        Boolean accepte = resultSet.getBoolean("accepte");
        return Optional.of(new Devis(id, montantEstime, dateEmission, dateValidite, accepte));
    }

    @Override
    protected Map<String, Object> mapModelData(Devis model) {
        Map<String, Object> map = new HashMap<>();
        map.put("montant_estime", model.getMontantEstime());
        map.put("date_emission", model.getDateEmission());
        map.put("date_validite", model.getDateValidite());
        map.put("accepte", model.isAccepte());
        return map;
    }
}
