package model;

public class ItemRecord {
    private final String id;
    private String name;
    private String category;
    private int quantity;
    private String status;

    public ItemRecord(String id, String name, String category, int quantity, String status) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.status = status;
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
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String toDataLine() {
        return "";
    }

    @Override
    public String toString() {
        return "";
    }
}
