package app;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import exception.DuplicateRecordException;
import model.ItemRecord;
import repository.RecordRepository;
import repository.TextFileRecordRepository;
import service.RecordManager;

public class Main {
    private static final RecordManager MANAGER = new RecordManager();
    private static final RecordRepository REPOSITORY = new TextFileRecordRepository(Path.of("data", "records.txt"));

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        loadRecords();

        while (running) {
            System.out.println("\n--- Exception-Safe Record Entry ---");
            System.out.println("1 - Add record");
            System.out.println("2 - List records");
            System.out.println("3 - Search record");
            System.out.println("4 - Update record");
            System.out.println("5 - Remove record");
            System.out.println("6 - Save records");
            System.out.println("0 - Save and exit");
            System.out.print("Choose an option: ");

            String option = scanner.nextLine();

            switch (option) {
                case "1" -> addRecord(scanner);
                case "2" -> listRecords();
                case "3" -> searchRecord(scanner);
                case "4" -> updateRecord(scanner);
                case "5" -> removeRecord(scanner);
                case "6" -> saveRecords();
                case "0" -> {
                    saveRecords();
                    running = false;
                    System.out.println("Exiting application. Goodbye!");
                }
                default -> System.out.println("Invalid option. Please choose 1, 2, 3, 4, 5, 6, or 0.");
            }
        }

        scanner.close();
    }

    private static void loadRecords() {
        try {
            MANAGER.replaceAll(REPOSITORY.loadAll());
        } catch (IOException | DuplicateRecordException e) {
            System.out.println("Records were not loaded: " + e.getMessage());
        }
    }

    private static void saveRecords() {
        try {
            REPOSITORY.saveAll(MANAGER.findAll());
        } catch (IOException e) {
            System.out.println("Records were not saved: " + e.getMessage());
        }
    }

    private static void addRecord(Scanner scanner) {
        try {
            System.out.print("Enter Record ID: ");
            String id = scanner.nextLine();

            System.out.print("Enter Record Name: ");
            String name = scanner.nextLine();

            System.out.print("Enter Quantity: ");
            String quantityText = scanner.nextLine();
            int quantity = Integer.parseInt(quantityText.trim());

            ItemRecord record = new ItemRecord(id, name, quantity);
            MANAGER.add(record);
            System.out.println("Record added: " + record);
        } catch (NumberFormatException e) {
            System.out.println("Unable to add: Quantity must be a whole number.");
        } catch (IllegalArgumentException e) {
            System.out.println("Unable to add: " + e.getMessage());
        } catch (DuplicateRecordException e) {
            System.out.println("Unable to add: " + e.getMessage());
        }
    }

    private static void listRecords() {
        System.out.println("\n--- Stored Records ---");
        List<ItemRecord> records = MANAGER.findAll();
        if (records.isEmpty()) {
            System.out.println("(no records)");
        } else {
            for (ItemRecord record : records) {
                System.out.println(record);
            }
        }
    }

    private static void searchRecord(Scanner scanner) {
        System.out.print("Enter Record ID to search: ");
        String id = scanner.nextLine();
        Optional<ItemRecord> found = MANAGER.findById(id);
        found.ifPresentOrElse(
            record -> System.out.println("Found: " + record),
            () -> System.out.println("Record not found.")
        );
    }

    private static void updateRecord(Scanner scanner) {
        try {
            System.out.print("Enter Record ID to update: ");
            String id = scanner.nextLine();

            System.out.print("Enter new Name: ");
            String newName = scanner.nextLine();

            System.out.print("Enter new Quantity: ");
            String quantityText = scanner.nextLine();
            int newQuantity = Integer.parseInt(quantityText.trim());

            MANAGER.update(id, newName, newQuantity);
            ItemRecord updated = MANAGER.findById(id).orElseThrow(
                () -> new IllegalStateException("Record unexpectedly missing after update.")
            );
            System.out.println("Record updated: " + updated);
        } catch (NumberFormatException e) {
            System.out.println("Unable to update: Quantity must be a whole number.");
        } catch (IllegalArgumentException e) {
            System.out.println("Unable to update: " + e.getMessage());
        }
    }

    private static void removeRecord(Scanner scanner) {
        System.out.print("Enter Record ID to remove: ");
        String id = scanner.nextLine();
        boolean removed = MANAGER.removeById(id);
        if (removed) {
            System.out.println("Record removed successfully.");
        } else {
            System.out.println("Record not found.");
        }
    }
}
