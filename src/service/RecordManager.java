package service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import exception.DuplicateRecordException;
import model.ItemRecord;

public class RecordManager {
    private final List<ItemRecord> records = new ArrayList<>();

    public ItemRecord findById(String id) {
        if (id == null) {
            return null;
        }
        String trimmed = id.trim();
        for (ItemRecord existing : records) {
            if (existing.getId().equalsIgnoreCase(trimmed)) {
                return existing;
            }
        }
        return null;
    }

    public void add(ItemRecord record) throws DuplicateRecordException {
        if (record == null) {
            throw new IllegalArgumentException("Record cannot be null.");
        }
        if (findById(record.getId()) != null) {
            throw new DuplicateRecordException("Record ID " + record.getId() + " already exists.");
        }
        records.add(record);
    }

    public List<ItemRecord> getAllRecords() {
        return Collections.unmodifiableList(records);
    }

    public void replaceAll(List<ItemRecord> loadedRecords) throws DuplicateRecordException {
        for (int i = 0; i < loadedRecords.size(); i++) {
            String id = loadedRecords.get(i).getId();
            for (int j = i + 1; j < loadedRecords.size(); j++) {
                if (id.equalsIgnoreCase(loadedRecords.get(j).getId())) {
                    throw new DuplicateRecordException("Record ID " + id + " already exists.");
                }
            }
        }
        records.clear();
        records.addAll(loadedRecords);
    }
}
