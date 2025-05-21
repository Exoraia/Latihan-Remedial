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


public class BuyForm extends JFrame {
    private JComboBox<String> customerField;
    private JComboBox<String> productField;
    private JTextField stockField;
    private JTextField priceField;
    private JTextField qtyField;
    private JTextField descField;
    private JButton orderButton;
    private List<Product> products;
    private List<Customer> customers;
    private Mavenproject3 mainApp;

    public BuyForm(Mavenproject3 mainApp) {
        this.mainApp = mainApp;
        this.customers = mainApp.getCustomerList(); 
        this.products = mainApp.getProductList();

        setTitle("WK. Cuan | Beli Barang");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel sellPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Nama Customer
        gbc.gridx = 0; gbc.gridy = 0;
        sellPanel.add(new JLabel("Customer:"), gbc);

        customerField = new JComboBox<>();
        for (Customer c : customers) {
            customerField.addItem(c.getName());
        }        
        gbc.gridx = 1;
        sellPanel.add(customerField, gbc);

        // Dropdown produk
        gbc.gridx = 0; gbc.gridy = 1;
        sellPanel.add(new JLabel("Barang:"), gbc);

        productField = new JComboBox<>();
        for (Product p : products) {
            productField.addItem(p.getName());
        }
        gbc.gridx = 1;
        sellPanel.add(productField, gbc);

        // Stok
        gbc.gridx = 0; gbc.gridy = 2;
        sellPanel.add(new JLabel("Stok:"), gbc);

        stockField = new JTextField(10);
        stockField.setEditable(false);
        gbc.gridx = 1;
        sellPanel.add(stockField, gbc);

        // Deskripsi
        gbc.gridx = 0; gbc.gridy = 3;
        sellPanel.add(new JLabel("Deskripsi:"), gbc);

        descField = new JTextField(10);
        descField.setEditable(false);
        gbc.gridx = 1;
        sellPanel.add(descField, gbc);

        // Harga
        gbc.gridx = 0; gbc.gridy = 4;
        sellPanel.add(new JLabel("Harga Jual:"), gbc);

        priceField = new JTextField(10);
        priceField.setEditable(false);
        gbc.gridx = 1;
        sellPanel.add(priceField, gbc);

        // Qty
        gbc.gridx = 0; gbc.gridy = 5;
        sellPanel.add(new JLabel("Qty:"), gbc);

        qtyField = new JTextField(10);
        gbc.gridx = 1;
        sellPanel.add(qtyField, gbc);

        // Tombol pesan
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        orderButton = new JButton("Pesan");
        sellPanel.add(orderButton, gbc);

        add(sellPanel);

        productField.addActionListener(e -> updateFields());

        // Listener Tombol Proses
        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = productField.getSelectedIndex();
                Product selectedProduct = products.get(selectedIndex);
                try {
                    int qty = Integer.parseInt(qtyField.getText());

                    if (qty <= 0) {
                        JOptionPane.showMessageDialog(BuyForm.this, "Qty harus lebih dari 0.");
                        return;
                    }

                    if (qty > selectedProduct.getStock()) {
                        JOptionPane.showMessageDialog(BuyForm.this, "Stok tidak mencukupi!");
                        return;
                    }

                    // Main Proses
                    double total = selectedProduct.getPrice() * qty; // Hitung Total
                    selectedProduct.setPrice(selectedProduct.getOriginalPrice()); // Balikin Harga Jual jadi harga awal
                    selectedProduct.setStock(selectedProduct.getStock() - qty); // Mengurangi Stok

                    JOptionPane.showMessageDialog(BuyForm.this, "Transaksi berhasil!\nTotal Harga: " + total);

                    updateFields();
                    qtyField.setText("");

                    mainApp.refreshBanner();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(BuyForm.this, "Qty harus berupa angka.");

                //   catch (NumberFormatException ex) {
                //     JOptionPane.showMessageDialog(null.this, "Qty harus berupa angka."); juga bisa
                }
            }
        });

        // Set nilai awal dari produk pertama
        if (!products.isEmpty()) {
            productField.setSelectedIndex(0);
            updateFields();
        }
    }

    private void updateFields() {
        int selectedIndex = productField.getSelectedIndex();
        if (selectedIndex != -1) {
            Product selectedProduct = products.get(selectedIndex);
            stockField.setText(String.valueOf(selectedProduct.getStock()));
            priceField.setText(String.valueOf(selectedProduct.getPrice()));
        }
    }
}
