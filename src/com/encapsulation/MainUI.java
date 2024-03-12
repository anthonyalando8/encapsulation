package com.encapsulation;

import javax.swing.*;
import java.awt.*;

public class MainUI extends JFrame {
    GridBagConstraints gbc = new GridBagConstraints();
    public MainUI(){
        Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenDim);
        setTitle("Library Management System");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE);
        setLocationRelativeTo(null);
        initializeUI();
    }
    private JFrame initializeUI(){
        JMenuBar jmb_Main = new JMenuBar();
        setJMenuBar(jmb_Main);
        JMenu jm_admin = new JMenu("Admin");
        JMenuItem mi_addBook = new JMenuItem("Add book");
        JMenuItem mi_lend = new JMenuItem("Lend Book");
        jm_admin.add(mi_addBook);
        jm_admin.add(mi_lend);

        JMenu jm_catalogue = new JMenu("Catalogue");
        JMenu jm_search = new JMenu("Search");

        JMenu jm_account = new JMenu("User");
        JMenuItem mi_acc = new JMenuItem("Profile");
        JMenuItem mi_logout = new JMenuItem("Logout");
        jm_account.add(mi_acc);
        jm_account.add(mi_logout);

        JMenu jm_help = new JMenu("Help");


        jmb_Main.add(jm_admin);
        jmb_Main.add(jm_catalogue);
        jmb_Main.add(jm_search);
        jmb_Main.add(jm_help);
        jmb_Main.add(jm_account);


        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        gbc.weighty = 1;
        gbc.fill= 1;
        mainPanel.setBackground(Color.WHITE);
        //mainPanel.setBorder(BorderFactory.createEmptyBorder(50,80,80,80));
        JPanel jp_nav = new JPanel(new GridBagLayout());
        jp_nav.setBackground(Color.WHITE);

        mainPanel.add(jp_nav, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;

        mainPanel.add(new CreateAddPanel(),gbc);

        JPanel jp_bottom = new JPanel();
        setVisible(true);
        jp_bottom.setBackground(Color.WHITE);
        gbc.gridy=1;
        gbc.weighty = 0.15;
        mainPanel.add(jp_bottom, gbc);
        jmb_Main.setBackground(this.getBackground());
        this.getContentPane().add(mainPanel);
        return this;
    }
}
