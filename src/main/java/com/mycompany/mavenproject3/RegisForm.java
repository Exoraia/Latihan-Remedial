/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import javax.swing.*;

/**
 *
 * @author ASUS
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;


public class RegisForm extends JFrame {
    private JTextField customerField;
    private JTextField emailField;
    private JTextField passwordField;
    private JComboBox genderBox;
    private JButton regisButton;
    private List<Customer> customers;
    private Mavenproject3 mainApp;

    public RegisForm(Mavenproject3 mainApp) {
        this.mainApp = mainApp;
        this.customers = mainApp.getCustomerList();

        setTitle("WK. Cuan | Login");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel regisPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Customer
        gbc.gridx = 0; gbc.gridy = 0;
        regisPanel.add(new JLabel("Nama:"), gbc);

        customerField = new JTextField(10);
        gbc.gridx = 1;
        regisPanel.add(customerField, gbc);

        // Gender
        gbc.gridx = 0; gbc.gridy = 1;
        regisPanel.add(new JLabel("Gender:"), gbc);

        genderBox = new JComboBox<>(new String[] {"Pria", "Wanita"});
        gbc.gridx = 1;
        regisPanel.add(genderBox, gbc);

        // Email
        gbc.gridx = 0; gbc.gridy = 2;
        regisPanel.add(new JLabel("Email:"), gbc);

        emailField = new JTextField(10);
        gbc.gridx = 1;
        regisPanel.add(emailField, gbc);

        // Password
        gbc.gridx = 0; gbc.gridy = 3;
        regisPanel.add(new JLabel("Password:"), gbc);

        passwordField = new JTextField(10);
        gbc.gridx = 1;
        regisPanel.add(passwordField, gbc);

        // Regis Button
        regisButton = new JButton("Buat Akun");
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        regisPanel.add(regisButton, gbc);

        add(regisPanel);
        setVisible(true);

        regisButton.addActionListener(e -> {
            String nama = customerField.getText().trim();
            String gender = (String) genderBox.getSelectedItem();
            String email = emailField.getText().trim();
            String password = passwordField.getText().trim();

            if (nama.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Semua field harus diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            boolean genderValue = gender.equals("Pria");

            int newId = customers.size() + 1; 
            
            Customer newCustomer = new Customer(newId, nama, email, password, genderValue);
            customers.add(newCustomer);

            JOptionPane.showMessageDialog(this, "Registrasi berhasil!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            new BuyForm(mainApp).setVisible(true);
            dispose(); // tutup form setelah sukses
        });
    }
}