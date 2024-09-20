package service;

import model.Materiel;
import model.Projet;
import repository.MaterielRepository;
import repository.impl.MaterielRepositoryImpl;

import java.sql.SQLException;

public class MaterielService {
    MaterielRepository materielRepository;

    public MaterielService() throws SQLException {
        this.materielRepository = new MaterielRepositoryImpl();
    }

    public void createMateriel(String nom, double tauxTVA, Projet projet, double coutUnitaire, double quantite, double coutTransport, double coefficientQualite) {
        Materiel materiel = new Materiel(nom, tauxTVA, projet, coutUnitaire, quantite, coutTransport, coefficientQualite);
        materielRepository.createMateriel(materiel);
    }
}
