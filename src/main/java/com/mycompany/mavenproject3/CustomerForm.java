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

        JPanel formPanel = new JPanel();
        formPanel.add(new JLabel("Nama:"));
        nameField = new JTextField(10);
        formPanel.add(nameField);

        formPanel.add(new JLabel("Email:"));
        emailField = new JTextField(10);
        formPanel.add(emailField);

        formPanel.add(new JLabel("Password:"));
        passField = new JTextField(10);
        formPanel.add(passField);

        formPanel.add(new JLabel("Gender:"));
        genderBox = new JComboBox<>(new String[]{"Pria", "Wanita"});
        formPanel.add(genderBox);

        saveButton = new JButton("Simpan");
        formPanel.add(saveButton);

        editButton = new JButton("Edit");
        formPanel.add(editButton);

        deleteButton = new JButton("Hapus");
        formPanel.add(deleteButton);

        tableModel = new DefaultTableModel(new String[]{"ID", "Nama", "Email", "Password", "Gender"}, 0);
        customerTable = new JTable(tableModel);
        loadCustomerData(customers);

        JScrollPane scrollPane = new JScrollPane(customerTable);
        add(formPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

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
