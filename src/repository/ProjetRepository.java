package repository;

import model.Materiel;
import model.MainDoeuvre;
import model.Projet;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProjetRepository {
    Projet createProject(Projet project);
    void applyMargeBeneficiaire(Projet project, double margeBeneficiaire) throws SQLException;
    void setCoutTotal(Projet project, double coutTotal) throws SQLException;
    List<Materiel> getMaterielsByProjectId(Long projectId) throws SQLException;
    List<MainDoeuvre> getMainDoeuvreByProjectId(Long projectId) throws SQLException;
    Optional<Projet> findById(Long id) throws SQLException;
    List<Projet> findAll() throws SQLException;
    void update(Projet project) throws SQLException;
    void deleteById(Long id) throws SQLException;
}
