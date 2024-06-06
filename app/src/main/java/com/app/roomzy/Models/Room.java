package com.app.roomzy.Models;

import java.util.ArrayList;

public class Room {
    private String address;
    private String id;
    private String imageURL;
    private String name;
    private int price;
    private int rate;
    private String type;
    private ArrayList<String> subImages;
    private ArrayList<String> subRooms;
    private String description;
    private String locationId;
    private String categoriesId;

    public Room(String address, String id, String imageURL, String name, int price, int rate, String type, ArrayList<String> subImages, ArrayList<String> subRooms, String description, String locationId, String categoriesId) {
        this.address = address;
        this.id = id;
        this.imageURL = imageURL;
        this.name = name;
        this.price = price;
        this.rate = rate;
        this.type = type;
        this.subImages = subImages;
        this.subRooms = subRooms;
        this.description = description;
        this.locationId = locationId;
        this.categoriesId = categoriesId;
    }

    public Room() {
    }

    // Getters and Setters
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

    public ArrayList<String> getSubImages() {
        return subImages;
    }

    public void setSubImages(ArrayList<String> subImages) {
        this.subImages = subImages;
    }

    public ArrayList<String> getSubRooms() {
        return subRooms;
    }

    public void setSubRooms(ArrayList<String> subRooms) {
        this.subRooms = subRooms;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getCategoriesId() {
        return categoriesId;
    }

    public void setCategoriesId(String categoriesId) {
        this.categoriesId = categoriesId;
    }
}
