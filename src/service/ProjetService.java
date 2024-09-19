package service;

import model.Client;
import model.EtatProjet;
import model.Projet;
import repository.ProjetRepository;
import repository.impl.ProjetRepositoryImpl;

import java.sql.SQLException;

public class ProjetService {
    ProjetRepository projectRepository;

    public ProjetService() throws SQLException {
        this.projectRepository = new ProjetRepositoryImpl();
    }

    public Projet createProject(String nomProjet, Client client) throws SQLException {
        Projet newProject = new Projet();
        newProject.setNomProjet(nomProjet);
        newProject.setClient(client);
        newProject.setMargeBeneficiaire(0.0);
        newProject.setCoutTotal(0.0);
        newProject.setEtatProjet(EtatProjet.EN_COURS);
        return projectRepository.createProject(newProject);
    }
}
