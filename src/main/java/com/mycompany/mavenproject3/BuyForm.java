/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import java.time.LocalDateTime;
import java.util.List;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;


public class BuyForm extends JFrame {
    private JComboBox<String> customerBox;
    private JComboBox<String> productBox;
    private JComboBox<String> unitBox;

    private JTextField stockField;
    private JTextField priceField;
    private JTextField qtyField;

    private JButton orderButton;
    private JButton regisButton;

    private List<Product> products;
    private List<Customer> customers;
    private List<Unit> units;

    private Mavenproject3 mainApp;

    public BuyForm(Mavenproject3 mainApp) {
        this.mainApp = mainApp;
        this.customers = mainApp.getCustomerList();
        this.products = mainApp.getProductList();

        this.units = new ArrayList<>();
        units.add(new Unit("Botol", 1));
        units.add(new Unit("Box", 12));

        setTitle("WK. Cuan | Beli Barang");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel buyPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Customer
        gbc.gridx = 0; gbc.gridy = 0;
        buyPanel.add(new JLabel("Customer:"), gbc);

        customerBox = new JComboBox<>();
        for (Customer c : customers) {
            customerBox.addItem(c.getName());
        }
        gbc.gridx = 1;
        buyPanel.add(customerBox, gbc);

        // Barang
        gbc.gridx = 0; gbc.gridy = 1;
        buyPanel.add(new JLabel("Barang:"), gbc);

        productBox = new JComboBox<>();
        for (Product p : products) {
            productBox.addItem(p.getName());
        }
        gbc.gridx = 1;
        buyPanel.add(productBox, gbc);

        // Satuan
        gbc.gridx = 0; gbc.gridy = 2;
        buyPanel.add(new JLabel("Satuan:"), gbc);

        unitBox = new JComboBox<>();
        for (Unit u : units) {
            unitBox.addItem(u.getSatuan());
        }
        gbc.gridx = 1;
        buyPanel.add(unitBox, gbc);

        // Stok
        gbc.gridx = 0; gbc.gridy = 3;
        buyPanel.add(new JLabel("Stok:"), gbc);

        stockField = new JTextField(10);
        stockField.setEditable(false);
        gbc.gridx = 1;
        buyPanel.add(stockField, gbc);

        // Harga Jual
        gbc.gridx = 0; gbc.gridy = 4;
        buyPanel.add(new JLabel("Harga Jual:"), gbc);

        priceField = new JTextField(10);
        priceField.setEditable(false);
        gbc.gridx = 1;
        buyPanel.add(priceField, gbc);

        // Qty
        gbc.gridx = 0; gbc.gridy = 5;
        buyPanel.add(new JLabel("Qty:"), gbc);

        qtyField = new JTextField(10);
        gbc.gridx = 1;
        buyPanel.add(qtyField, gbc);

        // Register
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 1;
        regisButton = new JButton("Register");
        buyPanel.add(regisButton, gbc);

        // Pesan
        gbc.gridx = 1; gbc.gridy = 6; gbc.gridwidth = 1;
        orderButton = new JButton("Pesan");
        buyPanel.add(orderButton, gbc);

        add(buyPanel);

        this.products = mainApp.getProductList();

        productBox.addActionListener(e -> updateFields());
        unitBox.addActionListener(e -> updateFields());

        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int productIndex = productBox.getSelectedIndex();
                int unitIndex = unitBox.getSelectedIndex();

                if (productIndex == -1 || unitIndex == -1) return;

                Product selectedProduct = products.get(productIndex);
                Unit selectedUnit = units.get(unitIndex);

                try {
                    int qty = Integer.parseInt(qtyField.getText());

                    if (qty <= 0) {
                        JOptionPane.showMessageDialog(BuyForm.this, "Qty harus lebih dari 0.");
                        return;
                    }

                    int totalBotol = qty * selectedUnit.getJumlah();
                    if (totalBotol > selectedProduct.getStock()) {
                        JOptionPane.showMessageDialog(BuyForm.this, "Stok tidak mencukupi!");
                        return;
                    }

                    double hargaPerUnit = selectedProduct.getPrice() * selectedUnit.getJumlah();
                    double total = hargaPerUnit * qty;

                    selectedProduct.setStock(selectedProduct.getStock() - totalBotol);

                    int orderId = mainApp.getHistoryList().size() + 1;
                    String customerName = (String) customerBox.getSelectedItem();
                    String productName = selectedProduct.getName();
                    LocalDateTime now = LocalDateTime.now();

                    History newHistory = new History(orderId, customerName, productName, totalBotol, now);
                    mainApp.addHistory(newHistory);

                    JOptionPane.showMessageDialog(BuyForm.this, "Transaksi berhasil! Total Harga: " + total);

                    updateFields();
                    qtyField.setText("");
                    mainApp.refreshBanner();
                    new CustomerWindow(mainApp).setVisible(true);
                    dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(BuyForm.this, "Qty harus berupa angka.");
                }
            }
        });

        regisButton.addActionListener(e -> {
            new RegisForm(mainApp).setVisible(true);
            dispose();
        });

        if (!products.isEmpty()) {
            productBox.setSelectedIndex(0);
            updateFields();
        }
    }

    private void updateFields() {
        int productIndex = productBox.getSelectedIndex();
        int unitIndex = unitBox.getSelectedIndex();

        if (productIndex != -1 && unitIndex != -1) {
            Product selectedProduct = products.get(productIndex);
            Unit selectedUnit = units.get(unitIndex);

            stockField.setText(String.valueOf(selectedProduct.getStock()));
            double hargaPerUnit = selectedProduct.getPrice() * selectedUnit.getJumlah();
            priceField.setText(String.valueOf(hargaPerUnit));
        }
    }
} 
