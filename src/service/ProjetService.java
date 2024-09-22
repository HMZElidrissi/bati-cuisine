package service;

import model.Client;
import model.Composant;
import model.MainDoeuvre;
import model.Materiel;
import model.Projet;
import repository.ProjetRepository;
import repository.impl.ProjetRepositoryImpl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class ProjetService {
    ProjetRepository projetRepository;

    public ProjetService() throws SQLException {
        this.projetRepository = new ProjetRepositoryImpl();
    }

    public Projet getProjectById(Long id) throws SQLException {
        Projet projet = projetRepository.findById(id).orElse(null);
        if (projet != null) {
            List<Materiel> materiels = projetRepository.getMaterielsByProjectId(id);
            List<MainDoeuvre> mainsDoeuvre = projetRepository.getMainDoeuvreByProjectId(id);

            for (Materiel materiel : materiels) {
                materiel.setProjet(projet);
                projet.addComposant(materiel);
            }

            for (MainDoeuvre mainDoeuvre : mainsDoeuvre) {
                mainDoeuvre.setProjet(projet);
                projet.addComposant(mainDoeuvre);
            }
        }
        return projet;
    }

    public Projet createProject(String nomProjet, Client client) throws SQLException {
        Projet newProject = new Projet(nomProjet, client);
        return projetRepository.createProject(newProject);
    }

    public void applyMargeBeneficiaire(Projet project, double margeBeneficiaire) throws SQLException {
        projetRepository.applyMargeBeneficiaire(project, margeBeneficiaire);
    }

    public void setCoutTotal(Projet project, double coutTotal) throws SQLException {
        projetRepository.setCoutTotal(project, coutTotal);
    }

    public List<Projet> getAllProjects() throws SQLException {
        return projetRepository.findAll();
    }
}
