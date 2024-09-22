package service;

import model.Devis;
import model.Projet;
import repository.DevisRepository;
import repository.impl.DevisRepositoryImpl;

import java.sql.SQLException;
import java.time.LocalDate;

public class DevisService {
    DevisRepository devisRepository;

    public DevisService() throws SQLException {
        this.devisRepository = new DevisRepositoryImpl();
    }

    public Devis createDevis(double montantEstime, LocalDate dateEmission, LocalDate dateValidite, boolean accepte, Projet projet) {
        Devis devis = new Devis(montantEstime, dateEmission, dateValidite, accepte, projet);
        return devisRepository.createDevis(devis);
    }
}
