package com.mycompany.mavenproject3;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Mavenproject3 extends JFrame implements Runnable {
    private String text;
    private int x;
    private int width;
    private BannerPanel bannerPanel;
    private JButton addProductButton;
    private JButton viewCustomerButton;
    private JButton sellProductButton;
    private JButton reportButton;
    private List<Product> productList = new ArrayList<>();
    private List<Customer> customerList = new ArrayList<>();
    private List<Unit> unitList = new ArrayList<>();
    private List<History> historyList = new ArrayList<>();

    public Mavenproject3() {
        setTitle("WK. STI Chill | Tampilan Penjual");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        productList.add(new Product(1, "P001", "Americano", "Coffee", 10000, 10));
        productList.add(new Product(2, "P002", "Pandan Latte", "Diary", 20000, 10));
        productList.add(new Product(3, "P003", "Root Beer", "Soda", 15000, 10));
        productList.add(new Product(4, "P004", "Matcha Frappucino", "Tea", 28000, 10));
        productList.add(new Product(5, "P005", "Jus Apel", "Juice", 17000, 10));
        
        customerList.add(new Customer(1, "Budi", "email123@gmail.com", "admin123", true));

        this.text = getBannerTextFromProducts();
        this.x = -getFontMetrics(new Font("Arial", Font.BOLD, 18)).stringWidth(text);

        // Panel Menu
        bannerPanel = new BannerPanel();
        add(bannerPanel, BorderLayout.CENTER);

        // Panel Tombol
        JPanel leftPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 4, 8, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        JLabel titleLabel = new JLabel("Home");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        leftPanel.add(titleLabel, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        addProductButton = new JButton("Kelola Produk");
        leftPanel.add(addProductButton, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        viewCustomerButton = new JButton("Kelola Customer");
        leftPanel.add(viewCustomerButton, gbc);
    
        gbc.gridx = 0; gbc.gridy = 3;
        sellProductButton = new JButton("Jual Produk");
        leftPanel.add(sellProductButton, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        reportButton = new JButton("Laporan Pembelian");
        leftPanel.add(reportButton, gbc);

        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.add(leftPanel, BorderLayout.NORTH);
        
        wrapperPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.GRAY));

        add(wrapperPanel, BorderLayout.WEST);

        addProductButton.addActionListener(e -> {
            new ProductForm(this).setVisible(true);
        });

        viewCustomerButton.addActionListener(e -> {
            new CustomerForm(this).setVisible(true);
        });

        sellProductButton.addActionListener(e -> {
            new SellForm(this).setVisible(true);
        });

        reportButton.addActionListener(e -> {
            new HistoryForm(this).setVisible(true);
        });

        setVisible(true);

        Thread thread = new Thread(this);
        thread.start();

        new CustomerWindow(this).setVisible(true);
    }

    class BannerPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.PLAIN, 18));
            g.drawString(text, x, getHeight() / 2);
        }
    }

    public void setBannerText(String newText) {
        this.text = newText;
        this.x = -getFontMetrics(new Font("Arial", Font.BOLD, 18)).stringWidth(text);
    }
    
    public String getBannerTextFromProducts() {
        StringBuilder sb = new StringBuilder("Menu yang masih ada: ");
        for (int i = 0; i < productList.size(); i++) {
            if (productList.get(i).getStock() > 0) {
                sb.append(productList.get(i).getName());
                if (i < productList.size() - 1) {
                    sb.append(" | ");
                }
            }
        }
        return sb.toString();
    }

    public String getPriceByProductName(String productName) {
        for (Product p : productList) {
            if (p.getName().equalsIgnoreCase(productName)) { 
                return String.valueOf(p.getPrice());       
            }
        }
        return "Product not found";
    }

    public void refreshBanner() {
        setBannerText(getBannerTextFromProducts());
    }

    public List<Product> getProductList() {
        return productList;
    }
    
    public List<Customer> getCustomerList() {
        return customerList;
    }

    public List<History> getHistoryList() {
        return historyList;
    }

    public void addHistory(History h) {
        historyList.add(h);
    }

    @Override
    public void run() {
        width = getWidth();
        while (true) {
            x += 5;
            if (x > width) {
                x = -getFontMetrics(new Font("Arial", Font.BOLD, 18)).stringWidth(text);
            }
            bannerPanel.repaint();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public static void main(String[] args) {
        new Mavenproject3();
    }
}