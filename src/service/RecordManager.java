package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import exception.DuplicateRecordException;
import model.ItemRecord;

public class RecordManager {
    private final List<ItemRecord> records = new ArrayList<>();

    public void add(ItemRecord record) throws DuplicateRecordException {
    }

    public Optional<ItemRecord> findById(String id) {
        return Optional.empty();
    }

    public List<ItemRecord> findByCategory(String category) {
        return List.copyOf(List.of());
    }

    public void update(String id, String newName, String newCategory, int newQuantity, String newStatus) {
    }

    public boolean removeById(String id) {
        return false;
    }

    public int getTotalQuantity() {
        return 0;
    }

    public List<ItemRecord> findAll() {
        return List.copyOf(records);
    }

    public void replaceAll(List<ItemRecord> loadedRecords) throws DuplicateRecordException {
    }
}
