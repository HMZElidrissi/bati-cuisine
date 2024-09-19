package util;

import java.util.Scanner;

public class InputValidator {
    public static int getIntInput(String query, Scanner scanner) {
        while (true) {
            try {
                System.out.println(query);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un nombre entier valide.");
            }
        }
    }

    public static double getDoubleInput(String query, Scanner scanner) {
        while (true) {
            try {
                System.out.println(query);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un nombre valide.");
            }
        }
    }

    public static String getStringInput(String query, Scanner scanner) {
        System.out.println(query);
        return scanner.nextLine().trim();
    }

    public static boolean getBooleanInput(String query, Scanner scanner) {
        while (true) {
            System.out.println(query);
            String response = scanner.nextLine().trim();
            if (response.equals("1") || response.equals("oui")) {
                return true;
            } else if (response.equals("0") || response.equals("non")) {
                return false;
            } else {
                System.out.println("Veuillez répondre par '1 (oui)/0 (non)'.");
            }
        }
    }
}
