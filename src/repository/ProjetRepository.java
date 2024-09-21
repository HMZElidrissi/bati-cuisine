package repository;

import model.Composant;
import model.Projet;

import java.sql.SQLException;
import java.util.List;

public interface ProjetRepository {
    Projet createProject(Projet project);
    void applyMargeBeneficiaire(Projet project, double margeBeneficiaire) throws SQLException;
    void setCoutTotal(Projet project, double coutTotal) throws SQLException;
}
