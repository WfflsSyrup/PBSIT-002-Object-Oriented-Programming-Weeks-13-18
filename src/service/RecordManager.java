package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import exception.DuplicateRecordException;
import model.ItemRecord;

public class RecordManager {
    private final List<ItemRecord> records = new ArrayList<>();

    public void add(ItemRecord record) throws DuplicateRecordException {
        if (record == null) {
            throw new IllegalArgumentException("Record cannot be null.");
        }
        if (findById(record.getId()).isPresent()) {
            throw new DuplicateRecordException("Record ID already exists: " + record.getId());
        }
        records.add(record);
    }

    public Optional<ItemRecord> findById(String id) {
        if (id == null || id.isBlank()) {
            return Optional.empty();
        }
        String trimmed = id.trim();
        for (ItemRecord existing : records) {
            if (existing.getId().equalsIgnoreCase(trimmed)) {
                return Optional.of(existing);
            }
        }
        return Optional.empty();
    }

    public List<ItemRecord> findByCategory(String category) {
        if (category == null || category.isBlank()) {
            return List.of();
        }
        String normalized = category.trim().toLowerCase();
        List<ItemRecord> matches = new ArrayList<>();
        for (ItemRecord record : records) {
            if (record.getCategory() != null
                    && record.getCategory().trim().toLowerCase().equals(normalized)) {
                matches.add(record);
            }
        }
        return List.copyOf(matches);
    }

    public void update(String id, String newName, String newCategory, int newQuantity, String newStatus) {
        ItemRecord existing = findById(id).orElseThrow(
            () -> new IllegalArgumentException("No record found for ID " + id + ".")
        );
        ItemRecord validation = new ItemRecord(existing.getId(), newName, newCategory, newQuantity, newStatus);
        existing.setName(validation.getName());
        existing.setCategory(validation.getCategory());
        existing.setQuantity(validation.getQuantity());
        existing.setStatus(validation.getStatus());
    }

    public boolean removeById(String id) {
        if (id == null || id.isBlank()) {
            return false;
        }
        String trimmed = id.trim();
        return records.removeIf(record -> record.getId().equalsIgnoreCase(trimmed));
    }

    public int getTotalQuantity() {
        int total = 0;
        for (ItemRecord record : records) {
            total += record.getQuantity();
        }
        return total;
    }

    public List<ItemRecord> findAll() {
        return List.copyOf(records);
    }

    public void replaceAll(List<ItemRecord> loadedRecords) throws DuplicateRecordException {
        RecordManager temp = new RecordManager();
        for (ItemRecord record : loadedRecords) {
            temp.add(record);
        }
        records.clear();
        records.addAll(temp.records);
    }
}
