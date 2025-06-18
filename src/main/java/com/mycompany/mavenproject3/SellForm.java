/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.List;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class SellForm extends JFrame {
    private JComboBox<String> productBox;
    private JComboBox<String> unitBox;
    private JComboBox<String> customerBox;
    private JTextField stockField;
    private JTextField priceField;
    private JTextField qtyField;
    private JButton processButton;
    private List<Product> products;
    private List<Unit> units;
    private List<Customer> customers;

    private Mavenproject3 mainApp;

        public SellForm(Mavenproject3 mainApp) {
        this.mainApp = mainApp;
        this.products = mainApp.getProductList(); 
        this.customers = mainApp.getCustomerList(); 
        this.units = new ArrayList<>();
        units.add(new Unit("Botol", 1));
        units.add(new Unit("Box", 12));

        setTitle("Form Penjualan");
        setSize(300, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel sellPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Customer
        gbc.gridx = 0; gbc.gridy = 0;
        sellPanel.add(new JLabel("Customer:"), gbc);

        customerBox = new JComboBox<>();
        for (Customer c : customers) {
            customerBox.addItem(c.getName());
        }
        gbc.gridx = 1;
        sellPanel.add(customerBox, gbc);

        // Barang
        gbc.gridx = 0; gbc.gridy = 1;
        sellPanel.add(new JLabel("Barang:"), gbc);

        productBox = new JComboBox<>();
        for (Product p : products) {
            productBox.addItem(p.getName());
        }
        gbc.gridx = 1;
        sellPanel.add(productBox, gbc);

        // Stok Tersedia
        gbc.gridx = 0; gbc.gridy = 2;
        sellPanel.add(new JLabel("Stok Tersedia:"), gbc);

        stockField = new JTextField(10);
        stockField.setEditable(false);
        gbc.gridx = 1;
        sellPanel.add(stockField, gbc);

        // Harga Jual
        gbc.gridx = 0; gbc.gridy = 3;
        sellPanel.add(new JLabel("Harga Jual:"), gbc);

        priceField = new JTextField(10);
        priceField.setEditable(false);
        gbc.gridx = 1;
        sellPanel.add(priceField, gbc);

        // Satuan
        gbc.gridx = 0; gbc.gridy = 4;
        sellPanel.add(new JLabel("Satuan:"), gbc);

        unitBox = new JComboBox<>();
        for (Unit u : units) {
            unitBox.addItem(u.getSatuan());
        }
        gbc.gridx = 1;
        sellPanel.add(unitBox, gbc);

        // Qty
        gbc.gridx = 0; gbc.gridy = 5;
        sellPanel.add(new JLabel("Qty:"), gbc);

        qtyField = new JTextField(10);
        gbc.gridx = 1;
        sellPanel.add(qtyField, gbc);

        // Tombol Proses
        processButton = new JButton("Proses");
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        sellPanel.add(processButton, gbc);

        add(sellPanel);

        productBox.addActionListener(e -> updateInfo());
        unitBox.addActionListener(e -> updateInfo());

        processButton.addActionListener(e -> processTransaction());

        updateInfo(); 
    }

    private void updateInfo() {
        String productName = (String) productBox.getSelectedItem();
        String unitName = (String) unitBox.getSelectedItem();
        Product selectedProduct = getProductByName(productName);
        Unit unit = getUnit(unitName);

        if (selectedProduct != null && unit != null) {
            int availableUnitStock = selectedProduct.getStock() / unit.getJumlah();
            double unitPrice = selectedProduct.getOriginalPrice() * unit.getJumlah();

            stockField.setText(String.valueOf(availableUnitStock));
            priceField.setText(formatRupiah(unitPrice));
        }
    }

    private void processTransaction() {
        try {
            String productName = (String) productBox.getSelectedItem();
            String customerName = (String) customerBox.getSelectedItem();
            Product selectedProduct = getProductByName(productName);
            Unit unit = getUnit((String) unitBox.getSelectedItem());
            int qty = Integer.parseInt(qtyField.getText());

            if (qty <= 0) {
                JOptionPane.showMessageDialog(this, "Qty harus lebih dari 0");
                return;
            }

            if (selectedProduct == null || unit == null) {
                JOptionPane.showMessageDialog(this, "Produk atau Satuan tidak valid");
                return;
            }

            int totalBotol = qty * unit.getJumlah();
            int totalHargaInt = (int)(totalBotol * selectedProduct.getOriginalPrice());

            if (selectedProduct.getStock() >= totalBotol) {
                selectedProduct.setStock(selectedProduct.getStock() - totalBotol);

                History newHistory = new History(
                    mainApp.getHistoryList().size() + 1,
                    customerName,
                    productName,
                    totalBotol,
                    totalHargaInt,
                    LocalDateTime.now()
                );

                mainApp.addHistory(newHistory);
                mainApp.refreshBanner();

                JOptionPane.showMessageDialog(this, "Transaksi berhasil\nTotal: " + formatRupiah(totalHargaInt));
                qtyField.setText("");
                updateInfo();
            } else {
                JOptionPane.showMessageDialog(this, "Stok tidak mencukupi!");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Qty harus angka!");
        }
    }

    private Product getProductByName(String name) {
        for (Product p : products) {
            if (p.getName().equalsIgnoreCase(name)) return p;
        }
        return null;
    }

    private Unit getUnit(String name) {
        for (Unit u : units) {
            if (u.getSatuan().equalsIgnoreCase(name)) return u;
        }
        return null;
    }

    private String formatRupiah(double harga) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        return formatter.format(harga).replace(",00", "");
    }
}