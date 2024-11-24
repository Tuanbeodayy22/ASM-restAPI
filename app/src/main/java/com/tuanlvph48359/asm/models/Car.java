package com.tuanlvph48359.asm.models;

public class Car {
    private int id;
    private String name;
    private String manufacturer;
    private int year;
    private double price;
    private String description;

    // Constructor mặc định
    public Car() {}

    // Constructor đầy đủ tham số
    public Car(String name, String manufacturer, int year, double price, String description) {
        this.name = name;
        this.manufacturer = manufacturer;
        this.year = year;
        this.price = price;
        this.description = description;
    }

    // Getters và Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", year=" + year +
                ", price=" + price +
                ", description='" + description + '\'' +
                '}';
    }
}
