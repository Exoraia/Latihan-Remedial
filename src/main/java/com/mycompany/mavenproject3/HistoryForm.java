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

public class HistoryForm extends JFrame {
    private JTable historyTable;
    private DefaultTableModel tableModel;
    private List<Customer> customers;
    private List<History> history;
    
    public HistoryForm(Mavenproject3 mainApp) {
        this.history = mainApp.getHistoryList(); 

        setTitle("WK. Cuan | Daftar Customer");
        setSize(600, 450);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        tableModel = new DefaultTableModel(new String[]{"ID Pesanan", "Nama", "Pesanan", "Jumlah", "Waktu Pemesanan"}, 0);
        historyTable = new JTable(tableModel);
        loadProductData(history);

        // Menampilkan tabel data
        JScrollPane scrollPane = new JScrollPane(historyTable);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void loadProductData(List<History> historyList) {
        for (History history : historyList) {
            tableModel.addRow(new Object[]{
                history.getIdPesanan(), history.getNamaCustomer(), history.getNamaProduk(), history.getJumlah(), history.getWaktu()
            });
        }
    }
}
