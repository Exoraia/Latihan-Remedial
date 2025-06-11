package com.mycompany.mavenproject3;

import javax.swing.*;

import com.mycompany.mavenproject3.Mavenproject3.BannerPanel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

class CustomerWindow extends JFrame {
    private String text;
    private int x;
    private int width;
    private BannerPanel bannerPanel;
    private JButton addProductButton;
    private JButton viewCustomerButton;
    private JButton sellProductButton;
    private JButton buyProductButton;
    private JButton reportButton;
    private List<Product> productList = new ArrayList<>();
    private List<Customer> customerList = new ArrayList<>();
    private List<History> historyList = new ArrayList<>();

    public CustomerWindow(Mavenproject3 mainApp) {
        setTitle("WK. STI Chill | Tampilan Pembeli");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        JPanel menuPanel = new JPanel(new GridBagLayout());
        JPanel bottomPanel = new JPanel();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 25, 8, 25);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        JLabel titleLabel = new JLabel("Menu Minuman");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        topPanel.add(titleLabel, gbc);
        gbc.gridwidth = 1;

        // List of categories
        String[] categories = { "Coffee", "Diary", "Juice", "Soda", "Tea" };

        for (int column = 0; column < categories.length; column++) {
            String category = categories[column];

            gbc.gridx = column;
            gbc.gridy = 1;
            JLabel categoryLabel = new JLabel(category);
            categoryLabel.setFont(new Font("Arial", Font.BOLD, 20));
            categoryLabel.setHorizontalAlignment(JLabel.CENTER);
            menuPanel.add(categoryLabel, gbc);

            int row = 2;
            for (Product p : mainApp.getProductList()) {
                if (category.equalsIgnoreCase(p.getCategory()) && p.getStock() > 0) {
                    gbc.gridx = column;
                    gbc.gridy = row++;
                    menuPanel.add(new JLabel(p.getName()), gbc);
                }
            }
        }

        // Tombol Beli
        buyProductButton = new JButton("Beli Produk");
        bottomPanel.add(buyProductButton);
        
        buyProductButton.addActionListener(e -> {
            new BuyForm(mainApp).setVisible(true);
        });
        
        add(topPanel, BorderLayout.NORTH);
        add(menuPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
}