/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3;

/**
 *
 * @author ASUS
 */
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Font;
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
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import javax.swing.table.DefaultTableModel;

public class ProductForm extends JFrame {
    private JTable drinkTable;
    private DefaultTableModel tableModel;
    private JTextField codeField;
    private JTextField nameField;
    private JComboBox<String> categoryField;
    private JTextField priceField;
    private JTextField stockField;
    private JButton saveButton;
    private JButton editButton;
    private JButton deleteButton;
    private List<Product> products;

    private String getAllProductNames() {
        StringBuilder sb = new StringBuilder("Menu yang tersedia: ");
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            sb.append(tableModel.getValueAt(i, 1)); 
            if (i < tableModel.getRowCount() - 1) {
                sb.append(" | ");
            }
        }
        return sb.toString();
    }
    
    public ProductForm(Mavenproject3 mainApp) {
        this.products = mainApp.getProductList(); 

        setTitle("WK. Cuan | Stok Barang");
        setSize(600, 450);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JLabel titleLabel = new JLabel("Kelola Produk");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        // Panel form pemesanan
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Kode
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Kode Barang"));

        codeField = new JTextField(8);
        gbc.gridx = 1;
        formPanel.add(codeField);
        
        // Nama Barang
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Nama Barang:"), gbc);
        
        nameField = new JTextField(8);
        gbc.gridx = 1;
        formPanel.add(nameField, gbc);
        
        // Kategori
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Kategori:"), gbc);

        categoryField = new JComboBox<>(new String[]{"Coffee", "Dairy", "Juice", "Soda", "Tea"});
        gbc.gridx = 1; 
        formPanel.add(categoryField, gbc);
        
        // Harga Jual
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Harga Jual:"), gbc);
        
        priceField = new JTextField(8);
        gbc.gridx = 1;
        formPanel.add(priceField, gbc);
        
        // Stok
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Stok Tersedia:"), gbc);
        
        stockField = new JTextField(8);
        gbc.gridx = 1; 
        formPanel.add(stockField, gbc);
        
        // Simpan
        gbc.gridx = 0; gbc.gridy = 5;
        saveButton = new JButton("Simpan");
        formPanel.add(saveButton, gbc);

        // Edit
        gbc.gridx = 0; gbc.gridy = 6;
        editButton = new JButton("Edit");
        formPanel.add(editButton, gbc);
        
        // Hapus
        gbc.gridx = 0; gbc.gridy = 7;
        deleteButton = new JButton("Hapus");
        formPanel.add(deleteButton, gbc);

        tableModel = new DefaultTableModel(new String[]{"Kode", "Nama", "Kategori", "Harga Jual", "Stok"}, 0);
        drinkTable = new JTable(tableModel);
        loadProductData(products);

        // Menampilkan panel CRUD
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(formPanel, BorderLayout.NORTH);

        // Tambahkan ke frame
        add(leftPanel, BorderLayout.WEST);

        
        // Menampilkan tabel data
        JScrollPane scrollPane = new JScrollPane(drinkTable);
        add(scrollPane, BorderLayout.CENTER);

        saveButton.addActionListener(e -> {
            int selectedRow = drinkTable.getSelectedRow();
            String code = codeField.getText();
            String name = nameField.getText();
            String category = (String) categoryField.getSelectedItem();
            String priceText = priceField.getText();
            String stockText = stockField.getText();
            
            if (code.isEmpty() || name.isEmpty() || priceText.isEmpty() || stockText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Semua field harus diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            try {
                double price = Double.parseDouble(priceText);
                int stock = Integer.parseInt(stockText);

                if (selectedRow != -1) {
                    tableModel.setValueAt(code, selectedRow, 0);
                    tableModel.setValueAt(name, selectedRow, 1);
                    tableModel.setValueAt(category, selectedRow, 2);
                    tableModel.setValueAt(price, selectedRow, 3);
                    tableModel.setValueAt(stock, selectedRow, 4);

                    JOptionPane.showMessageDialog(this, "Data berhasil diperbarui.");
                } else {
                    tableModel.addRow(new Object[]{code, name, category, price, stock});
                    products.add(new Product(0, code, name, category, price, stock));

                    JOptionPane.showMessageDialog(this, "Data berhasil ditambahkan.");
                }

                codeField.setText("");
                nameField.setText("");
                categoryField.setSelectedIndex(0);
                priceField.setText("");
                stockField.setText("");

                mainApp.setBannerText(getAllProductNames());

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Harga dan stok harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        editButton.addActionListener(e -> {
            int selectedRow = drinkTable.getSelectedRow();
            if (selectedRow != -1) {
                try {
                    String selectedCode = drinkTable.getValueAt(selectedRow, 0).toString();
                    String selectedName = drinkTable.getValueAt(selectedRow, 1).toString();
                    String selectedCategory = drinkTable.getValueAt(selectedRow, 2).toString();
                    String selectedPrice = drinkTable.getValueAt(selectedRow, 3).toString();
                    String selectedStock = drinkTable.getValueAt(selectedRow, 4).toString();

                    codeField.setText(selectedCode);
                    nameField.setText(selectedName);
                    categoryField.setSelectedItem(selectedCategory);
                    priceField.setText(selectedPrice);
                    stockField.setText(selectedStock);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Harga dan stok harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Pilih baris yang ingin diedit!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        deleteButton.addActionListener(e -> {
            int selectedRow = drinkTable.getSelectedRow();
            if (selectedRow != -1) {
                products.remove(selectedRow);
                tableModel.removeRow(selectedRow);

                codeField.setText("");
                nameField.setText("");
                categoryField.setSelectedIndex(0);
                priceField.setText("");
                stockField.setText("");

                mainApp.setBannerText(getAllProductNames());
            } else {
                JOptionPane.showMessageDialog(formPanel, "tidak ada yang dipilih");
            }
        });
    }

    private void loadProductData(List<Product> productList) {
        for (Product product : productList) {
            tableModel.addRow(new Object[]{
                product.getCode(), product.getName(), product.getCategory(), product.getPrice(), product.getStock()
            });
        }
    }
}
