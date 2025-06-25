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
import java.awt.FlowLayout;
import java.time.LocalDate;
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
    private List<History> history;
    private JComboBox<String> filterBox;
    
    public HistoryForm(Mavenproject3 mainApp) {
        this.history = mainApp.getHistoryList(); 

        setTitle("WK. Cuan | Daftar Customer");
        setSize(600, 450);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Tabel
        tableModel = new DefaultTableModel(new String[]{"ID Pesanan", "Nama", "Pesanan", "Jumlah", "Jumlah Pembayaran", "Waktu Pemesanan"}, 0);
        historyTable = new JTable(tableModel);
        loadProductData(history);

        // Filter 
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterBox = new JComboBox<>(new String[]{"Semua", "Hari Ini", "Minggu Ini", "Bulan Ini"});
        topPanel.add(new JLabel("Filter:"));
        topPanel.add(filterBox);

        add(topPanel, BorderLayout.NORTH);

        // Menampilkan tabel data
        JScrollPane scrollPane = new JScrollPane(historyTable);
        add(scrollPane, BorderLayout.CENTER);

        filterBox.addActionListener(e -> applyFilter());
    }
    
    private void loadProductData(List<History> historyList) {
        tableModel.setRowCount(0); 
        for (History history : historyList) {
            tableModel.addRow(new Object[]{
                history.getIdPesanan(),
                history.getNamaCustomer(),
                history.getNamaProduk(),
                history.getJumlah() + " Botol",
                formatRupiah(history.getJumlahPembayaran()),
                history.getWaktu()
            });
        }
    }

    private String formatRupiah(double harga) {
        java.text.NumberFormat formatter = java.text.NumberFormat.getCurrencyInstance(
            java.util.Locale.forLanguageTag("id-ID")
        );
        return formatter.format(harga).replace(",00", "");
    }

    private void applyFilter() {
        String selected = (String) filterBox.getSelectedItem();
        List<History> filtered = new ArrayList<>();

        LocalDate now = LocalDate.now();

        for (History h : history) {
            LocalDate date = h.getWaktu().toLocalDate(); 
            switch (selected) {
                case "Today":
                    if (date.equals(now)) filtered.add(h);
                    break;
                case "This Week":
                    if (!date.isBefore(now.minusDays(6))) filtered.add(h);
                    break;
                case "This Month":
                    if (date.getMonth() == now.getMonth() && date.getYear() == now.getYear())
                        filtered.add(h);
                    break;
                default:
                    filtered = history;
                    break;
            }
        }

        loadProductData(filtered);
    }
}
