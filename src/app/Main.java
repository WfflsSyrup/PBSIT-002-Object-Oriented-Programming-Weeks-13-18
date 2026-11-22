package app;

import java.util.Scanner;
import exception.DuplicateRecordException;
import model.ItemRecord;
import service.RecordManager;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        RecordManager manager = new RecordManager();
        boolean running = true;

        while (running) {
            System.out.println("\n--- Exception-Safe Record Entry ---");
            System.out.println("1 - Add record");
            System.out.println("2 - List records");
            System.out.println("0 - Exit");
            System.out.print("Choose an option: ");

            String option = scanner.nextLine();

            switch (option) {
                case "1" -> addRecord(scanner, manager);
                case "2" -> listRecords(manager);
                case "0" -> {
                    running = false;
                    System.out.println("Exiting application. Goodbye!");
                }
                default -> System.out.println("Invalid option. Please choose 1, 2, or 0.");
            }
        }

        scanner.close();
    }

    private static void addRecord(Scanner scanner, RecordManager manager) {
        try {
            System.out.print("Enter Record ID: ");
            String id = scanner.nextLine();

            System.out.print("Enter Record Name: ");
            String name = scanner.nextLine();

            System.out.print("Enter Quantity: ");
            String quantityText = scanner.nextLine();
            int quantity = Integer.parseInt(quantityText.trim());

            ItemRecord record = new ItemRecord(id, name, quantity);
            manager.add(record);
            System.out.println("Record added: " + record);
        } catch (NumberFormatException e) {
            System.out.println("Unable to add: Quantity must be a whole number.");
        } catch (IllegalArgumentException e) {
            System.out.println("Unable to add: " + e.getMessage());
        } catch (DuplicateRecordException e) {
            System.out.println("Unable to add: " + e.getMessage());
        }
    }

    private static void listRecords(RecordManager manager) {
        System.out.println("\n--- Stored Records ---");
        var records = manager.getAllRecords();
        if (records.isEmpty()) {
            System.out.println("(no records)");
        } else {
            for (ItemRecord record : records) {
                System.out.println(record);
            }
        }
    }
}
