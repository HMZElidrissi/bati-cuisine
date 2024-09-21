import service.*;
import util.CLI;
import util.DatabaseConnection;
import util.InputValidator;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    private static ClientService clientService;
    private static ProjetService projetService;
    private static MaterielService materielService;
    private static MainDoeuvreService mainDoeuvreService;
    private static DevisService devisService;

    public static void main(String[] args) throws SQLException {

        try {
            clientService = new ClientService();
            projetService = new ProjetService();
            materielService = new MaterielService();
            mainDoeuvreService = new MainDoeuvreService();
            devisService = new DevisService();
        } catch (SQLException e) {
            System.out.println("Erreur lors l'initalisation des services.");
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in);
        CLI cli = new CLI();

        boolean running = true;
        while (running) {
            CLI.mainMenu();

            int choice = InputValidator.getIntInput("Choisissez une option : ", scanner);

            switch (choice) {
                case 1:
                    cli.createNewProject(scanner, clientService, projetService, materielService, mainDoeuvreService, devisService);
                    break;
                case 2:
                    // TODO: Implement displaying existing projects
                    break;
                case 3:
                    // TODO: Implement calculating project cost
                    break;
                case 4:
                    System.out.println("Au revoir !");
                    running = false;
                    break;
                default:
                    System.out.println("Option invalide. Veuillez réessayer.");
            }
        }

        scanner.close();
        DatabaseConnection.getInstance().closeConnection();
    }
}