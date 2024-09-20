package service;

import model.MainDoeuvre;
import model.Projet;
import repository.MainDoeuvreRepository;
import repository.impl.MainDoeuvreRepositoryImpl;

import java.sql.SQLException;

public class MainDoeuvreService {
    MainDoeuvreRepository mainDoeuvreRepository;

    public MainDoeuvreService() throws SQLException {
        this.mainDoeuvreRepository = new MainDoeuvreRepositoryImpl();
    }

    public void createMainDoeuvre(String nomMainOeuvre, double tauxTVA, Projet projet, double tauxHoraire, double heuresTravail, double productiviteOuvrier) {
        MainDoeuvre mainDoeuvre = new MainDoeuvre(nomMainOeuvre, tauxTVA, projet, tauxHoraire, heuresTravail, productiviteOuvrier);
        mainDoeuvreRepository.createMainDoeuvre(mainDoeuvre);
    }
}
