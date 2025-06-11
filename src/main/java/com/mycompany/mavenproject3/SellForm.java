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


public class SellForm extends JFrame {
    private JComboBox<String> productBox;
    private JComboBox<String> unitBox;
    private JTextField stockField;
    private JTextField priceField;
    private JTextField qtyField;
    private JButton processButton;
    private List<Product> products;
    private List<Unit> units;
    private Mavenproject3 mainApp;

    public SellForm(Mavenproject3 mainApp) {
        this.mainApp = mainApp;
        this.products = mainApp.getProductList();

        this.units = new ArrayList<>();
        units.add(new Unit("Botol", 1));
        units.add(new Unit("Box", 12));

        setTitle("WK. Cuan | Jual Barang");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel sellPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Dropdown produk
        gbc.gridx = 0; gbc.gridy = 0;
        sellPanel.add(new JLabel("Barang:"), gbc);

        productBox = new JComboBox<>();
        for (Product p : products) {
            productBox.addItem(p.getName());
        }
        gbc.gridx = 1;
        sellPanel.add(productBox, gbc);

        // Stok
        gbc.gridx = 0; gbc.gridy = 1;
        sellPanel.add(new JLabel("Stok Tersedia:"), gbc);

        stockField = new JTextField(10);
        stockField.setEditable(false);
        gbc.gridx = 1;
        sellPanel.add(stockField, gbc);

        // Harga
        gbc.gridx = 0; gbc.gridy = 2;
        sellPanel.add(new JLabel("Harga Jual:"), gbc);

        priceField = new JTextField(10);
        priceField.setEditable(false);
        gbc.gridx = 1;
        sellPanel.add(priceField, gbc);

        // Satuan
        gbc.gridx = 0; gbc.gridy = 3;
        sellPanel.add(new JLabel("Satuan:"), gbc);

        unitBox = new JComboBox<>();
        for (Unit u : units) {
            unitBox.addItem(u.getSatuan());
        }
        gbc.gridx = 1;
        sellPanel.add(unitBox, gbc);

        // Qty
        gbc.gridx = 0; gbc.gridy = 4;
        sellPanel.add(new JLabel("Qty:"), gbc);

        qtyField = new JTextField(10);
        gbc.gridx = 1;
        sellPanel.add(qtyField, gbc);

        // Tombol proses
        processButton = new JButton("Proses");
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        sellPanel.add(processButton, gbc);

        add(sellPanel);

        // Listener: Update stok dan harga saat produk dipilih
        productBox.addActionListener(e -> updateFields());
        unitBox.addActionListener(e -> updateFields());

        // Listener: Tombol Proses
        processButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = productBox.getSelectedIndex();
                Product selectedProduct = products.get(selectedIndex);
                try {
                    int qty = Integer.parseInt(qtyField.getText());

                    if (qty <= 0) {
                        JOptionPane.showMessageDialog(SellForm.this, "Qty harus lebih dari 0.");
                        return;
                    }

                    if (qty > selectedProduct.getStock()) {
                        JOptionPane.showMessageDialog(SellForm.this, "Stok tidak mencukupi!");
                        return;
                    }

                    selectedProduct.setStock(selectedProduct.getStock() - qty);
                    JOptionPane.showMessageDialog(SellForm.this, "Transaksi berhasil!\nSisa stok: " + selectedProduct.getStock());

                    updateFields();
                    qtyField.setText("");

                    mainApp.refreshBanner();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(SellForm.this, "Qty harus berupa angka.");
                }
            }
        });

        // Set nilai awal dari produk pertama
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
