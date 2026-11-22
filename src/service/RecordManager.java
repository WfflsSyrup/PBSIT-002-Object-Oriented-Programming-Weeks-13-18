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
            throw new DuplicateRecordException("Record ID " + record.getId() + " already exists.");
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

    public void update(String id, String newName, int newQuantity) {
        ItemRecord existing = findById(id).orElseThrow(
            () -> new IllegalArgumentException("No record found for ID " + id + ".")
        );
        ItemRecord validation = new ItemRecord(existing.getId(), newName, newQuantity);
        existing.setName(validation.getName());
        existing.setQuantity(validation.getQuantity());
    }

    public boolean removeById(String id) {
        return findById(id).map(record -> {
            records.remove(record);
            return true;
        }).orElse(false);
    }

    public List<ItemRecord> findAll() {
        return List.copyOf(records);
    }

    public void replaceAll(List<ItemRecord> loadedRecords) throws DuplicateRecordException {
        for (int i = 0; i < loadedRecords.size(); i++) {
            String currentId = loadedRecords.get(i).getId();
            for (int j = i + 1; j < loadedRecords.size(); j++) {
                if (currentId.equalsIgnoreCase(loadedRecords.get(j).getId())) {
                    throw new DuplicateRecordException("Record ID " + currentId + " already exists.");
                }
            }
        }
        records.clear();
        records.addAll(loadedRecords);
    }
}
