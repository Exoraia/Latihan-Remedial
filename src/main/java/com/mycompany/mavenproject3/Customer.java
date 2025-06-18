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
    private String nohp;
    private String email;
    private String password;
    private boolean gender;

    public Customer(int id, String name, String nohp, String email, String password, Boolean gender) {
        this.id = id;
        this.name = name;
        this.nohp = nohp;
        this.email = email;
        this.password = password;
        this.gender = gender;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getNohp() { return nohp; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public boolean getGender() { return gender; }

    public void setName(String name) { this.name = name; }
    public void setNohp(String nohp) { this.nohp = nohp; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setGender(boolean gender) { this.gender = gender; }

    public String getGenderString() {
        return gender ? "Male" : "Female";
    }
}