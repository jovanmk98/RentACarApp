package org.meicode.rentacarapp.Model;

public class Car {
    private int id;
    private String name;
    private int price;
    private int year;
    private String image;

    public Car(int id, String name, int price, int year, String image) {
        this.id = id;
        this.name = name;
        this.price=price;
        this.year=year;
        this.image=image;
    }

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
