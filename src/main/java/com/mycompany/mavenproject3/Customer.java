/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3;

/**
 *
 * @author ASUS
 */
public class Customer {
    private int id;
    private String name;
    private boolean gender;

    public Customer(int id, String name, boolean gender) {
        this.id = id;
        this.name = name;
        this.gender = gender;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public boolean getGender() { return gender; }
    public void setName(String name) { this.name = name; }

    public String getGenderString() {
        return gender ? "Male" : "Female";
    }
}


