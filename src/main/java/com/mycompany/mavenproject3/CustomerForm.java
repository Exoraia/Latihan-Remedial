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

public class CustomerForm extends JFrame {
    private JTable customerTable;
    private DefaultTableModel tableModel;
    private JTextField codeField;
    private JTextField nameField;
    private JTextField emailField;
    private JTextField passField;
    private JComboBox genderBox;
    private JButton saveButton;
    private JButton editButton;
    private JButton deleteButton;
    private List<Customer> customers;

    public CustomerForm(Mavenproject3 mainApp) {
        this.customers = mainApp.getCustomerList();

        setTitle("WK. Cuan | Daftar Customer");
        setSize(600, 450);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel titleLabel = new JLabel("Kelola Customer");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Nama:"), gbc);

        nameField = new JTextField(10);
        gbc.gridx = 1;
        formPanel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Email:"), gbc);

        emailField = new JTextField(10);
        gbc.gridx = 1;         
        formPanel.add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Password:"), gbc);
        
        passField = new JTextField(10);
        gbc.gridx = 1;
        formPanel.add(passField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Gender:"), gbc);
        
        genderBox = new JComboBox<>(new String[]{"Pria", "Wanita"});
        gbc.gridx = 1; 
        formPanel.add(genderBox, gbc);

        // Simpan
        gbc.gridx = 0; gbc.gridy = 4;
        saveButton = new JButton("Simpan");
        formPanel.add(saveButton, gbc);

        // Edit
        gbc.gridx = 0; gbc.gridy = 5;
        editButton = new JButton("Edit");
        formPanel.add(editButton, gbc);
        
        // Hapus
        gbc.gridx = 0; gbc.gridy = 6;
        deleteButton = new JButton("Hapus");
        formPanel.add(deleteButton, gbc);

        tableModel = new DefaultTableModel(new String[]{"ID", "Nama", "Email", "Password", "Gender"}, 0);
        customerTable = new JTable(tableModel);
        loadCustomerData(customers);

        JScrollPane scrollPane = new JScrollPane(customerTable);
        add(scrollPane, BorderLayout.CENTER);

        // Menampilkan panel CRUD
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(formPanel, BorderLayout.NORTH);

        // Tambahkan ke frame
        add(leftPanel, BorderLayout.WEST);

        saveButton.addActionListener(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String password = passField.getText();
            String gender = (String) genderBox.getSelectedItem();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Semua field harus diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int selectedRow = customerTable.getSelectedRow();
            if (selectedRow != -1) {
                Customer customer = customers.get(selectedRow);
                customer.setName(name);
                customer.setEmail(email);
                customer.setPassword(password);
                customer.setGender("Pria".equals(gender));

                tableModel.setValueAt(name, selectedRow, 1);
                tableModel.setValueAt(email, selectedRow, 2);
                tableModel.setValueAt(password, selectedRow, 3);
                tableModel.setValueAt(gender, selectedRow, 4);

                JOptionPane.showMessageDialog(this, "Data berhasil diperbarui.");
            } else {
                int newId = customers.size() + 1;
                Customer newCustomer = new Customer(newId, name, email, password, "Pria".equals(gender));
                customers.add(newCustomer);

                tableModel.addRow(new Object[]{
                    newCustomer.getId(),
                    newCustomer.getName(),
                    newCustomer.getEmail(),
                    newCustomer.getPassword(),
                    gender
                });

                JOptionPane.showMessageDialog(this, "Data berhasil ditambahkan.");
            }
            clearForm();
        });

        editButton.addActionListener(e -> {
            int selectedRow = customerTable.getSelectedRow();
            if (selectedRow != -1) {
                nameField.setText(customerTable.getValueAt(selectedRow, 1).toString());
                emailField.setText(customerTable.getValueAt(selectedRow, 2).toString());
                passField.setText(customerTable.getValueAt(selectedRow, 3).toString());
                genderBox.setSelectedItem(customerTable.getValueAt(selectedRow, 4).toString());
            } else {
                JOptionPane.showMessageDialog(this, "Pilih baris yang ingin diedit!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            }
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = customerTable.getSelectedRow();
            if (selectedRow != -1) {
                customers.remove(selectedRow);
                tableModel.removeRow(selectedRow);
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Pilih baris yang ingin dihapus!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    private void loadCustomerData(List<Customer> customerList) {
        for (Customer customer : customerList) {
            tableModel.addRow(new Object[]{
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getPassword(),
                customer.getGender() ? "Pria" : "Wanita"
            });
        }
    }

    private void clearForm() {
        nameField.setText("");
        emailField.setText("");
        passField.setText("");
        genderBox.setSelectedIndex(0);
        customerTable.clearSelection();
    }
}
