package model;

public class ItemRecord {
    private final String id;
    private String name;
    private String category;
    private int quantity;
    private String status;

    public ItemRecord(String id, String name, String category, int quantity, String status) {
        this.id = validateText(id, "Record ID");
        this.name = validateText(name, "Record name");
        this.category = validateText(category, "Record category");
        this.status = validateText(status, "Record status");
        if (quantity < 1) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setName(String name) {
        this.name = validateText(name, "Record name");
    }

    public void setCategory(String category) {
        this.category = validateText(category, "Record category");
    }

    public void setQuantity(int quantity) {
        if (quantity < 1) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }
        this.quantity = quantity;
    }

    public void setStatus(String status) {
        this.status = validateText(status, "Record status");
    }

    private static String validateText(String value, String fieldLabel) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldLabel + " is required.");
        }
        if (value.contains("|")) {
            throw new IllegalArgumentException("Record fields must not contain the '|' delimiter.");
        }
        return value.trim();
    }

    public String toDataLine() {
        return id + "|" + name + "|" + category + "|" + quantity + "|" + status;
    }

    @Override
    public String toString() {
        return String.format("ID: %s | Name: %s | Category: %s | Quantity: %d | Status: %s",
                id, name, category, quantity, status);
    }
}
