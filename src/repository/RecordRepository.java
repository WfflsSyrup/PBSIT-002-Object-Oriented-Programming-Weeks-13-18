package repository;

import java.io.IOException;
import java.util.List;
import model.ItemRecord;

public interface RecordRepository {
    List<ItemRecord> loadAll() throws IOException;
    void saveAll(List<ItemRecord> records) throws IOException;
}
