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
    private static final Scanner INPUT = new Scanner(System.in);
    private static final RecordManager MANAGER = new RecordManager();
    private static final RecordRepository REPOSITORY = new TextFileRecordRepository(Path.of("data", "records.txt"));

    public static void main(String[] args) {
        loadRecords();
        boolean running = true;
        while (running) {
            printMenu();
            System.out.print("Choose an option: ");
            String option = INPUT.nextLine();
            switch (option) {
                case "1" -> addRecord();
                case "2" -> listRecords();
                case "3" -> searchRecord();
                case "4" -> filterByCategory();
                case "5" -> updateRecord();
                case "6" -> removeRecord();
                case "7" -> showSummaryAndSave();
                case "0" -> {
                    saveRecords();
                    running = false;
                    System.out.println("Exiting application. Goodbye!");
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
        INPUT.close();
    }

    private static void printMenu() {
        System.out.println("\n--- GuidedRecordsApp ---");
        System.out.println("1 - Add record");
        System.out.println("2 - List records");
        System.out.println("3 - Search record");
        System.out.println("4 - Filter by category");
        System.out.println("5 - Update record");
        System.out.println("6 - Remove record");
        System.out.println("7 - Show summary & save");
        System.out.println("0 - Save and exit");
    }

    private static void loadRecords() {
        try {
            List<ItemRecord> loaded = REPOSITORY.loadAll();
            MANAGER.replaceAll(loaded);
            System.out.println("Loaded " + loaded.size() + " record(s).");
        } catch (IOException e) {
            System.out.println("Records were not loaded: " + e.getMessage());
        } catch (DuplicateRecordException e) {
            System.out.println("Records were not loaded: " + e.getMessage());
        }
    }

    private static void saveRecords() {
        try {
            REPOSITORY.saveAll(MANAGER.findAll());
            System.out.println("Records saved successfully.");
        } catch (IOException e) {
            System.out.println("Records were not saved: " + e.getMessage());
        }
    }

    private static void addRecord() {
        try {
            System.out.print("Enter Record ID: ");
            String id = INPUT.nextLine();
            System.out.print("Enter Record Name: ");
            String name = INPUT.nextLine();
            System.out.print("Enter Category: ");
            String category = INPUT.nextLine();
            System.out.print("Enter Quantity: ");
            int quantity = Integer.parseInt(INPUT.nextLine().trim());
            System.out.print("Enter Status: ");
            String status = INPUT.nextLine();

            ItemRecord record = new ItemRecord(id, name, category, quantity, status);
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
            System.out.println("No records stored.");
        } else {
            for (ItemRecord record : records) {
                System.out.println(record);
            }
        }
    }

    private static void searchRecord() {
        System.out.print("Enter Record ID to search: ");
        String id = INPUT.nextLine();
        Optional<ItemRecord> found = MANAGER.findById(id);
        found.ifPresentOrElse(
            record -> System.out.println("Found: " + record),
            () -> System.out.println("Record not found.")
        );
    }

    private static void filterByCategory() {
        System.out.print("Enter category to filter: ");
        String category = INPUT.nextLine();
        List<ItemRecord> results = MANAGER.findByCategory(category);
        if (results.isEmpty()) {
            System.out.println("No records found for category: " + category);
        } else {
            System.out.println("\n--- Records in category: " + category + " ---");
            for (ItemRecord record : results) {
                System.out.println(record);
            }
        }
    }

    private static void updateRecord() {
        try {
            System.out.print("Enter Record ID to update: ");
            String id = INPUT.nextLine();
            System.out.print("Enter new Name: ");
            String newName = INPUT.nextLine();
            System.out.print("Enter new Category: ");
            String newCategory = INPUT.nextLine();
            System.out.print("Enter new Quantity: ");
            int newQuantity = Integer.parseInt(INPUT.nextLine().trim());
            System.out.print("Enter new Status: ");
            String newStatus = INPUT.nextLine();

            MANAGER.update(id, newName, newCategory, newQuantity, newStatus);
            Optional<ItemRecord> updated = MANAGER.findById(id);
            updated.ifPresent(record -> System.out.println("Record updated: " + record));
        } catch (NumberFormatException e) {
            System.out.println("Unable to update: Quantity must be a whole number.");
        } catch (IllegalArgumentException e) {
            System.out.println("Unable to update: " + e.getMessage());
        }
    }

    private static void removeRecord() {
        System.out.print("Enter Record ID to remove: ");
        String id = INPUT.nextLine();
        boolean removed = MANAGER.removeById(id);
        if (removed) {
            System.out.println("Record removed successfully.");
        } else {
            System.out.println("Record not found.");
        }
    }

    private static void showSummaryAndSave() {
        List<ItemRecord> records = MANAGER.findAll();
        System.out.println("\n--- Stock Summary ---");
        System.out.println("Total records: " + records.size());
        System.out.println("Total quantity: " + MANAGER.getTotalQuantity());
        saveRecords();
    }
}
