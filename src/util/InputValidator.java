package util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
            if (response.equals("1") || response.equals("oui") || response.equals("o")) {
                return true;
            } else if (response.equals("0") || response.equals("non") || response.equals("n")) {
                return false;
            } else {
                System.out.println("Veuillez répondre par '1 (oui)/0 (non)'.");
            }
        }
    }

    public static LocalDate getLocalDateInput(String query, Scanner scanner) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate today = LocalDate.now();

        while (true) {
            try {
                System.out.println(query + " (format: dd-mm-yyyy)");
                String input = scanner.nextLine().trim();
                LocalDate date = LocalDate.parse(input, formatter);

                if (date.isBefore(today)) {
                    System.out.println("La date doit être aujourd'hui ou dans le futur.");
                } else {
                    return date;
                }
            } catch (DateTimeParseException e) {
                System.out.println("Format de date invalide. Veuillez utiliser le format dd-mm-yyyy.");
            }
        }
    }

    public static Long getLongInput(String query, Scanner scanner) {
        while (true) {
            try {
                System.out.println(query);
                return Long.parseLong(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un nombre entier valide.");
            }
        }
    }
}
