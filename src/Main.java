import util.CLI;
import service.ClientService;
import util.DatabaseConnection;
import util.InputValidator;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    private static ClientService clientService;
    public static void main(String[] args) {

        try {
            clientService = new ClientService();
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
                    cli.createNewProject(scanner, clientService);
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