package app;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;
import exception.DuplicateRecordException;
import model.ItemRecord;
import repository.RecordRepository;
import repository.TextFileRecordRepository;
import service.RecordManager;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        RecordManager manager = new RecordManager();
        RecordRepository repository = new TextFileRecordRepository(Path.of("data", "records.txt"));
        boolean running = true;

        loadRecords(repository, manager);

        while (running) {
            System.out.println("\n--- Exception-Safe Record Entry ---");
            System.out.println("1 - Add record");
            System.out.println("2 - List records");
            System.out.println("3 - Save records");
            System.out.println("0 - Save and exit");
            System.out.print("Choose an option: ");

            String option = scanner.nextLine();

            switch (option) {
                case "1" -> addRecord(scanner, manager);
                case "2" -> listRecords(manager);
                case "3" -> saveRecords(repository, manager);
                case "0" -> {
                    saveRecords(repository, manager);
                    running = false;
                    System.out.println("Exiting application. Goodbye!");
                }
                default -> System.out.println("Invalid option. Please choose 1, 2, 3, or 0.");
            }
        }

        scanner.close();
    }

    private static void loadRecords(RecordRepository repository, RecordManager manager) {
        try {
            manager.replaceAll(repository.loadAll());
        } catch (IOException | DuplicateRecordException e) {
            System.out.println("Records were not loaded: " + e.getMessage());
        }
    }

    private static void saveRecords(RecordRepository repository, RecordManager manager) {
        try {
            repository.saveAll(manager.getAllRecords());
        } catch (IOException e) {
            System.out.println("Records were not saved: " + e.getMessage());
        }
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
