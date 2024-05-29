package com.app.roomzy.Models;

public class Room {
    private String address;
    private String id;
    private String imageURL;
    private String name;
    private int price;
    private int rate;
    private String type;

    public Room(String address, String id, String imageURL, String name, int price, int rate, String type) {
        this.address = address;
        this.id = id;
        this.imageURL = imageURL;
        this.name = name;
        this.price = price;
        this.rate = rate;
        this.type = type;
    }

    public Room() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
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

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
