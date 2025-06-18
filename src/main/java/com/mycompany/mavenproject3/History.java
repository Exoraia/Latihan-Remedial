/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3;

import java.time.LocalDateTime;
/**
 *
 * @author ASUS
 */
public class History {
    private int idPesanan;
    private String namaCustomer;
    private String namaProduk;
    private int jumlah;
    private int jumlahPembayaran;
    private LocalDateTime waktu;

    public History(int idPesanan, String namaCustomer, String namaProduk, int jumlah, int jumlahPembayaran, LocalDateTime waktu) {
        this.idPesanan = idPesanan;
        this.namaCustomer = namaCustomer;
        this.namaProduk = namaProduk;
        this.jumlah = jumlah;
        this.jumlahPembayaran = jumlahPembayaran;
        this.waktu = waktu;
    }

    public int getIdPesanan() { return idPesanan; }
    public String getNamaCustomer() { return namaCustomer; }
    public String getNamaProduk() { return namaProduk; }
    public int getJumlah() { return jumlah; }
    public int getJumlahPembayaran() { return jumlahPembayaran; }
    public LocalDateTime getWaktu() { return waktu; }
    
    public void setJumlah(int jumlah) { this.jumlah = jumlah; }
    public void setWaktu(LocalDateTime waktu) { this.waktu = waktu; }
}