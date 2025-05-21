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
    private JTextField passwordField;
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

        // Password
        gbc.gridx = 0; gbc.gridy = 2;
        regisPanel.add(new JLabel("Password:"), gbc);

        passwordField = new JTextField(10);
        gbc.gridx = 1;
        regisPanel.add(passwordField, gbc);

        // Regis Button
        regisButton = new JButton("Pesan");
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        regisPanel.add(regisButton, gbc);
    }
}
