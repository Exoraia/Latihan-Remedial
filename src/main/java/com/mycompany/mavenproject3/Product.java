/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3;

/**
 *
 * @author ASUS
 */
public class Product {
    private int id;
    private String code;
    private String name;
    private String category;
    private double price;
    private double originalPrice;
    private int stock;

    public Product(int id, String code, String name, String category, double price, int stock) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.category = category;
        this.price = price;
        this.originalPrice = price;
        this.stock = stock;
    }

    public int getId() { return id; }
    public String getCode() { return code; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }
    public double getOriginalPrice() { return originalPrice; }
    public void setStock(int stock) { this.stock = stock; }
    public void setPrice(double price) {this.price = price; }
    public void total (double price) {this.price = price;}
}
