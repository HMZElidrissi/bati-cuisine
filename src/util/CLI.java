package util;


import model.*;
import service.ClientService;
import service.MainDoeuvreService;
import service.MaterielService;
import service.ProjetService;

import java.sql.SQLException;
import java.util.Scanner;

import static util.InputValidator.*;

public class CLI {
    public static void mainMenu() {
        System.out.println("1. Créer un nouveau projet");
        System.out.println("2. Afficher les projets existants");
        System.out.println("3. Calculer le coût d'un projet");
        System.out.println("4. Quitter");
    }

    public void createNewProject(Scanner scanner, ClientService clientService, ProjetService projetService, MaterielService materielService, MainDoeuvreService mainDoeuvreService) throws SQLException {
        System.out.println("--- Recherche de client ---");
        Client client = assignClient(scanner, clientService);
        if (client != null) {
            String nomProjet = getStringInput("Entrez le nom du projet : ", scanner);

            Projet projet = projetService.createProject(nomProjet, client);

            System.out.println("--- Ajout des matériaux ---");
            addMateriauxToProject(scanner, projet, materielService);

            System.out.println("--- Ajout de la main d'oeuvre ---");
            addMainOeuvreToProject(scanner, projet, mainDoeuvreService);

            calculateProjectCost(scanner, projet);

            createDevis(scanner);
        } else {
            System.out.println("Création de projet annulée.");
        }
    }

    public void addMateriauxToProject(Scanner scanner, Projet projet, MaterielService materielService) {
        String nomMateriel = getStringInput("Entrez le nom du matériel : ", scanner);
        double tauxTVA = getDoubleInput("Entrez le taux de TVA : ", scanner);
        double coutUnitaire = getDoubleInput("Entrez le coût unitaire : ", scanner);
        double quantite = getDoubleInput("Entrez la quantité : ", scanner);
        double coutTransport = getDoubleInput("Entrez le coût de transport : ", scanner);
        double coefficientQualite = getDoubleInput("Entrez le coefficient de qualité (1.0 = standard, > 1.0 = haute qualité) : ", scanner);

        materielService.createMateriel(nomMateriel, tauxTVA, projet, coutUnitaire, quantite, coutTransport, coefficientQualite);
        System.out.println("Matérieux ajouté avec succès.");

        System.out.println("Voulez-vous ajouter un autre matériel ? (o/n)");

        if (getBooleanInput("Votre choix : ", scanner)) {
            addMateriauxToProject(scanner, projet, materielService);
        } else {
            System.out.println("Ajout de matériaux terminé.");
        }

    }

    public void addMainOeuvreToProject(Scanner scanner, Projet projet, MainDoeuvreService mainDoeuvreService) {
        String nomMainOeuvre = getStringInput("Entrez le nom de la main d'oeuvre : ", scanner);
        double tauxTVA = getDoubleInput("Entrez le taux de TVA : ", scanner);
        double tauxHoraire = getDoubleInput("Entrez le taux horaire : ", scanner);
        double heuresTravail = getDoubleInput("Entrez le nombre d'heures de travail : ", scanner);
        double productiviteOuvrier = getDoubleInput("Entrez la productivité de l'ouvrier : (1.0 = standard, > 1.0 = haute productivité) : ", scanner);

        mainDoeuvreService.createMainDoeuvre(nomMainOeuvre, tauxTVA, projet, tauxHoraire, heuresTravail, productiviteOuvrier);
        System.out.println("Main d'oeuvre ajoutée avec succès.");

        System.out.println("Voulez-vous ajouter une autre main d'oeuvre ? (o/n)");
        if (getBooleanInput("Votre choix : ", scanner)) {
            addMainOeuvreToProject(scanner, projet, mainDoeuvreService);
        } else {
            System.out.println("Ajout de main d'oeuvre terminé.");
        }
    }

    public void calculateProjectCost(Scanner scanner, Projet projet) {
        System.out.println("--- Calcul du coût total du projet ---");
        // TODO: Calculate the total cost of the project
    }

    public void createDevis(Scanner scanner) {
        System.out.println("--- Enregistrement du devis ---");
        // TODO: Save the project as a quote
    }

    public Client assignClient(Scanner scanner, ClientService clientService) {
        System.out.println("Souhaitez-vous rechercher un client existant ou en ajouter un nouveau ?");
        System.out.println("1. Chercher un client existant");
        System.out.println("2. Ajouter un nouveau client");
        System.out.println("3. Retour");

        int choice = getIntInput("Votre choix : ", scanner);

        switch (choice) {
            case 1:
                Client client = searchClient(scanner, clientService);
                if (client == null) {
                    System.out.println("Client non trouvé.");
                    return assignClient(scanner, clientService);
                } else {
                    System.out.println(client);
                    System.out.println("Souhaitez-vous continuer avec ce client ? (o/n)");
                    if (getBooleanInput("Votre choix : ", scanner)) {
                        return client;
                    } else {
                        return assignClient(scanner, clientService);
                    }
                }
            case 2:
                return createNewClient(scanner, clientService);
            case 3:
                return null;
            default:
                System.out.println("Choix invalide");
                return assignClient(scanner, clientService);
        }
    }


    public Client searchClient(Scanner scanner, ClientService clientService) {
        System.out.println("--- Recherche de client existant ---");
        String nom = getStringInput("Entrez le nom du client : ", scanner);
        return clientService.getClientByNom(nom);
    }


    public Client createNewClient(Scanner scanner, ClientService clientService) {
        System.out.println("--- Création d'un nouveau client ---");
        String nom = getStringInput("Nom du client : ", scanner);
        String adresse = getStringInput("Adresse : ", scanner);
        String telephone = getStringInput("Numéro de téléphone : ", scanner);
        boolean estProfessionnel = getBooleanInput("Est-ce un client professionnel ? (o/n) : ", scanner);

        try {
            return clientService.createClient(nom, adresse, telephone, estProfessionnel);
        } catch (SQLException e) {
            System.out.println("Erreur lors de la création du client.");
            return null;
        }
    }
}
