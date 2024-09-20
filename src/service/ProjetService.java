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
        Projet newProject = new Projet(nomProjet, client);
        return projectRepository.createProject(newProject);
    }
}
