package util;


import model.*;
import service.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import static util.InputValidator.*;

public class CLI {
    public static void mainMenu() {
        System.out.println("\n1. Créer un nouveau projet");
        System.out.println("2. Afficher les projets existants");
        System.out.println("3. Calculer le coût d'un projet");
        System.out.println("4. Modifier un projet");
        System.out.println("5. Supprimer un projet");
        System.out.println("6. Quitter");
    }

    public void createNewProject(Scanner scanner, ClientService clientService, ProjetService projetService, MaterielService materielService, MainDoeuvreService mainDoeuvreService, DevisService devisService) throws SQLException {
        System.out.println("--- Recherche de client ---");
        Client client = assignClient(scanner, clientService);
        if (client != null) {
            String nomProjet = getStringInput("Entrez le nom du projet : ", scanner);

            Projet projet = projetService.createProject(nomProjet, client);

            System.out.println("--- Ajout des matériaux ---");
            addMateriauxToProject(scanner, projet, materielService);

            System.out.println("--- Ajout de la main d'oeuvre ---");
            addMainOeuvreToProject(scanner, projet, mainDoeuvreService);

            calculateProjectCost(scanner, projet, projetService);

            createDevis(scanner, projet, devisService);
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
        projet.addComposant(new Materiel(nomMateriel, tauxTVA, projet, coutUnitaire, quantite, coutTransport, coefficientQualite));
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
        double tauxTVA = getDoubleInput("Entrez le taux de TVA (%): ", scanner);
        double tauxHoraire = getDoubleInput("Entrez le taux horaire : ", scanner);
        double heuresTravail = getDoubleInput("Entrez le nombre d'heures de travail : ", scanner);
        double productiviteOuvrier = getDoubleInput("Entrez la productivité de l'ouvrier : (1.0 = standard, > 1.0 = haute productivité) : ", scanner);

        mainDoeuvreService.createMainDoeuvre(nomMainOeuvre, tauxTVA, projet, tauxHoraire, heuresTravail, productiviteOuvrier);
        projet.addComposant(new MainDoeuvre(nomMainOeuvre, tauxTVA, projet, tauxHoraire, heuresTravail, productiviteOuvrier));
        System.out.println("Main d'oeuvre ajoutée avec succès.");

        System.out.println("Voulez-vous ajouter une autre main d'oeuvre ? (o/n)");
        if (getBooleanInput("Votre choix : ", scanner)) {
            addMainOeuvreToProject(scanner, projet, mainDoeuvreService);
        } else {
            System.out.println("Ajout de main d'oeuvre terminé.");
        }
    }

    public void calculateProjectCost(Scanner scanner, Projet projet, ProjetService projetService) throws SQLException {
        System.out.println("--- Calcul du coût total du projet ---");

        if (projet.getMargeBeneficiaire() == 0) {
            System.out.println("Souhaitez-vous appliquer une marge bénéficiaire au projet ? (o/n)");
            if (getBooleanInput("Votre choix : ", scanner)) {
                double margeBeneficiaire = getDoubleInput("Entrez le pourcentage de marge bénéficiaire : ", scanner);
                projet.setMargeBeneficiaire(margeBeneficiaire);
                projetService.applyMargeBeneficiaire(projet, margeBeneficiaire);
            }
        }

        System.out.println("Calcul du coût en cours...");
        calculateProjectCostResult(projet, projetService);
    }

    public void calculateProjectCostResult(Projet projet, ProjetService projetService) throws SQLException {
        System.out.println("--- Résultat du calcul ---");
        System.out.println("Nom du projet : " + projet.getNomProjet());
        System.out.println("Client : " + projet.getClient().getNom());

        double totalMateriauxTTC = 0;
        double totalMateriauxTVA = 0;
        double totalMainDOeuvreTTC = 0;
        double totalMainDOeuvreTVA = 0;

        System.out.println("\n--- Détail des Coûts ---");

        for (Composant composant : projet.getComposants()) {
            double coutTTC = composant.calculerCout();
            double tva = composant.getTauxTVA();

            if (composant instanceof Materiel) {
                totalMateriauxTTC += coutTTC;
                totalMateriauxTVA += coutTTC * (tva / 100);
                System.out.printf("Matériel - %s : %.2f MAD%n", composant.getNom(), coutTTC);
            } else if (composant instanceof MainDoeuvre) {
                totalMainDOeuvreTTC += coutTTC;
                totalMainDOeuvreTVA += coutTTC * (tva / 100);
                System.out.printf("Main d'œuvre - %s : %.2f MAD%n", composant.getNom(), coutTTC);
            }
        }

        System.out.printf("%nCoût total des matériaux avant TVA : %.2f MAD%n", totalMateriauxTTC);
        System.out.printf("Coût total des matériaux après TVA : %.2f MAD%n", totalMateriauxTTC + totalMateriauxTVA);
        System.out.printf("Coût total de la main d'œuvre avant TVA : %.2f MAD%n", totalMainDOeuvreTTC);
        System.out.printf("Coût total de la main d'œuvre après TVA : %.2f MAD%n", totalMainDOeuvreTTC + totalMainDOeuvreTVA);

        double coutTotalTTC = totalMateriauxTTC + totalMainDOeuvreTTC + totalMateriauxTVA + totalMainDOeuvreTVA;
        if (projet.getClient().isEstProfessionnel()){
            coutTotalTTC *= 0.9;
        }
        projet.setCoutTotal(coutTotalTTC);
        projetService.setCoutTotal(projet, coutTotalTTC);
        System.out.printf("%nCoût total avant marge : %.2f MAD%n", coutTotalTTC);

        double margeBeneficiaire = projet.getMargeBeneficiaire();
        double montantMarge = coutTotalTTC * (margeBeneficiaire / 100);
        System.out.printf("Marge bénéficiaire (%.2f%%) : %.2f MAD%n", margeBeneficiaire, montantMarge);

        double coutFinal = coutTotalTTC + montantMarge;
        System.out.printf("%nCoût total final du projet : %.2f MAD%n", coutFinal);
    }

    public void createDevis(Scanner scanner, Projet projet, DevisService devisService) {
        System.out.println("--- Enregistrement du devis ---");
        double montantEstime = projet.getCoutTotal() + ((projet.getMargeBeneficiaire() / 100) * projet.getCoutTotal());
        LocalDate dateEmission = getLocalDateInput("Entrez la date d'émission du devis : ", scanner);
        LocalDate dateValidite = getLocalDateInput("Entrez la date de validité du devis : ", scanner);
        System.out.println("Souhaitez-vous accepter le devis ? (o/n)");
        if (getBooleanInput("Votre choix : ", scanner)) {
            devisService.createDevis(montantEstime, dateEmission, dateValidite, true, projet);
            System.out.println("Devis enregistré et accepté avec succès.");
        } else {
            devisService.createDevis(montantEstime, dateEmission, dateValidite, false, projet);
            System.out.println("Devis enregistré et en attente d'acceptation.");
        }
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

    public void displayExistingProjects(Scanner scanner, ProjetService projetService) throws SQLException {
        System.out.println("--- Affichage des projets existants ---");
        List<Projet> projects = projetService.getAllProjects();
        if (projects.isEmpty()) {
            System.out.println("Aucun projet existant.");
        } else {
            projects.forEach(System.out::println);
        }
    }

    public void calculateExistingProjectCost(Scanner scanner, ProjetService projetService) throws SQLException {
        System.out.println("--- Calcul du coût d'un projet existant ---");
        projetService.getAllProjects().forEach(System.out::println);
        long projectId = getLongInput("Entrez l'ID du projet : ", scanner);
        Projet projet = projetService.getProjectById(projectId);
        if (projet == null) {
            System.out.println("Projet non trouvé.");
            return;
        }
        calculateProjectCost(scanner, projet, projetService);
    }

    public void updateProject(Scanner scanner, ProjetService projetService, ClientService clientService) throws SQLException {
        System.out.println("--- Modification d'un projet existant ---");
        projetService.getAllProjects().forEach(System.out::println);
        long projectId = getLongInput("Entrez l'ID du projet à modifier : ", scanner);
        Projet projet = projetService.getProjectById(projectId);
        if (projet == null) {
            System.out.println("Projet non trouvé.");
            return;
        }
        System.out.println("Que voulez-vous modifier ?");
        System.out.println("1. Nom du projet");
        System.out.println("2. Client");
        System.out.println("3. Marge bénéficiaire");
        System.out.println("4. Retour");
        int choice = getIntInput("Votre choix : ", scanner);
        switch (choice) {
            case 1:
                String nomProjet = getStringInput("Entrez le nouveau nom du projet : ", scanner);
                projetService.updateNomProjet(projet, nomProjet);
                System.out.println("Nom du projet modifié avec succès.");
                break;
            case 2:
                Client client = assignClient(scanner, clientService);
                if (client != null) {
                    projetService.updateClient(projet, client);
                    System.out.println("Client modifié avec succès.");
                } else {
                    System.out.println("Modification du client annulée.");
                }
                break;
            case 3:
                double margeBeneficiaire = getDoubleInput("Entrez la nouvelle marge bénéficiaire : ", scanner);
                projetService.applyMargeBeneficiaire(projet, margeBeneficiaire);
                System.out.println("Marge bénéficiaire modifiée avec succès.");
                break;
            case 4:
                return;
            default:
                System.out.println("Choix invalide.");
        }
    }

    public void deleteProject(Scanner scanner, ProjetService projetService) throws SQLException {
        System.out.println("--- Suppression d'un projet existant ---");
        projetService.getAllProjects().forEach(System.out::println);
        long projectId = getLongInput("Entrez l'ID du projet à supprimer : ", scanner);
        Projet projet = projetService.getProjectById(projectId);
        if (projet == null) {
            System.out.println("Projet non trouvé.");
            return;
        }
        System.out.println("Êtes-vous sûr de vouloir supprimer ce projet ? (o/n)");
        if (getBooleanInput("Votre choix : ", scanner)) {
            projetService.deleteProject(projet);
            System.out.println("Projet supprimé avec succès.");
        } else {
            System.out.println("Suppression annulée.");
        }
    }
}
