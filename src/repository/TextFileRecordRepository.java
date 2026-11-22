package repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import model.ItemRecord;

public class TextFileRecordRepository implements RecordRepository {
    private final Path file;

    public TextFileRecordRepository(Path file) {
        this.file = file;
    }

    public Path getFile() {
        return file;
    }

    @Override
    public List<ItemRecord> loadAll() throws IOException {
        if (Files.notExists(file)) {
            return new ArrayList<>();
        }

        List<ItemRecord> records = new ArrayList<>();
        int lineNumber = 0;

        try (BufferedReader reader = Files.newBufferedReader(file)) {
            String line;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.isBlank()) {
                    continue;
                }
                records.add(decode(line, lineNumber));
            }
        }

        return records;
    }

    private ItemRecord decode(String line, int lineNumber) throws IOException {
        String[] fields = line.split("\\|", -1);
        if (fields.length != 3) {
            throw new IOException("Invalid field count at line " + lineNumber + ".");
        }

        try {
            int quantity = Integer.parseInt(fields[2].trim());
            return new ItemRecord(fields[0], fields[1], quantity);
        } catch (IllegalArgumentException exception) {
            throw new IOException("Invalid record at line " + lineNumber + ": " + exception.getMessage(), exception);
        }
    }

    @Override
    public void saveAll(List<ItemRecord> records) throws IOException {
        Path parent = file.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }

        try (BufferedWriter writer = Files.newBufferedWriter(file)) {
            for (ItemRecord record : records) {
                writer.write(record.toDataLine());
                writer.newLine();
            }
        }
    }
}
