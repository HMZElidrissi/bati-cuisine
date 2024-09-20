package repository;

import model.Projet;

import java.sql.SQLException;

public interface ProjetRepository {
    Projet createProject(Projet project);
}
