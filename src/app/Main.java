package app;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;
import exception.DuplicateRecordException;
import repository.RecordRepository;
import repository.TextFileRecordRepository;
import service.RecordManager;

public class Main {
    private static final Scanner INPUT = new Scanner(System.in);
    private static final RecordManager MANAGER = new RecordManager();
    private static final RecordRepository REPOSITORY = new TextFileRecordRepository(Path.of("data", "records.txt"));

    public static void main(String[] args) {
        System.out.println("GuidedRecordsApp Week 16 skeleton compiled and initialized.");
        loadRecords();
    }

    private static void printMenu() {
    }

    private static void addRecord() throws DuplicateRecordException {
    }

    private static void listRecords() {
    }

    private static void searchRecord() {
    }

    private static void filterByCategory() {
    }

    private static void updateRecord() {
    }

    private static void removeRecord() {
    }

    private static void showSummary() {
    }

    private static void loadRecords() {
        try {
            MANAGER.replaceAll(REPOSITORY.loadAll());
        } catch (IOException | DuplicateRecordException e) {
        }
    }

    private static void saveRecords() {
        try {
            REPOSITORY.saveAll(MANAGER.findAll());
        } catch (IOException e) {
        }
    }
}
