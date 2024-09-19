package util;


import model.Client;
import model.Projet;
import model.EtatProjet;
import service.ClientService;
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

    public void createNewProject(Scanner scanner, ClientService clientService, ProjetService projetService) throws SQLException {
        System.out.println("--- Recherche de client ---");
        Client client = assignClient(scanner, clientService);
        if (client != null) {
            String nomProjet = getStringInput("Entrez le nom du projet : ", scanner);

            Projet projet = projetService.createProject(nomProjet, client);

            addMateriauxToProject(scanner, projet);

            addMainOeuvreToProject(scanner, projet);

            calculateProjectCost(scanner, projet);

            createDevis(scanner);
        } else {
            System.out.println("Création de projet annulée.");
        }
    }

    public void addMateriauxToProject(Scanner scanner, Projet projet) {
        System.out.println("--- Ajout des matériaux ---");
        // TODO: Add materials to the project
    }

    public void addMainOeuvreToProject(Scanner scanner, Projet projet) {
        System.out.println("--- Ajout de la main d'oeuvre ---");
        // TODO: Add labor to the project
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

        Client newClient = new Client();
        newClient.setNom(nom);
        newClient.setAdresse(adresse);
        newClient.setTelephone(telephone);
        newClient.setEstProfessionnel(estProfessionnel);

        try {
            return clientService.createClient(newClient);
        } catch (SQLException e) {
            System.out.println("Erreur lors de la création du client.");
            return null;
        }
    }
}
