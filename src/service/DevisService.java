package service;

import model.Devis;
import repository.DevisRepository;
import repository.impl.DevisRepositoryImpl;

import java.sql.SQLException;

public class DevisService {
    DevisRepository devisRepository;

    public DevisService() throws SQLException {
        this.devisRepository = new DevisRepositoryImpl();
    }

    public Devis createDevis(Devis devis) {
        return devisRepository.createDevis(devis);
    }
}
