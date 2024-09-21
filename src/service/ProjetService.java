package service;

import model.Client;
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

    public void applyMargeBeneficiaire(Projet project, double margeBeneficiaire) throws SQLException {
        projectRepository.applyMargeBeneficiaire(project, margeBeneficiaire);
    }

    public void setCoutTotal(Projet project, double coutTotal) throws SQLException {
        projectRepository.setCoutTotal(project, coutTotal);
    }
}
