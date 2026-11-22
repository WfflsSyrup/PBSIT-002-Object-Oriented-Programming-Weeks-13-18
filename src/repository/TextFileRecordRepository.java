package repository;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import model.ItemRecord;

public class TextFileRecordRepository implements RecordRepository {
    private final Path file;

    public TextFileRecordRepository(Path file) {
        this.file = file;
    }

    @Override
    public List<ItemRecord> loadAll() throws IOException {
        return new ArrayList<>();
    }

    @Override
    public void saveAll(List<ItemRecord> records) throws IOException {
    }

    private ItemRecord decode(String line, int lineNumber) throws IOException {
        return null;
    }

    public Path getFile() {
        return file;
    }
}
