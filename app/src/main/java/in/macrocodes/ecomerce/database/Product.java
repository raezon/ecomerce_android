package in.macrocodes.ecomerce.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "products")
public class Product {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String mark;
    private double price;

    // Constructor with parameters
        public Product(String name, String mark, double price) {
            this.name = name;
            this.mark = mark;
            this.price = price;
        }


    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMark() {
        return mark;
    }

    public double getPrice() {
        return price;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
