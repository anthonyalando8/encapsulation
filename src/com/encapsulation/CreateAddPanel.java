package com.encapsulation;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.*;

public class CreateAddPanel extends JPanel {
    public CreateAddPanel(){
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(203, 201, 201), 1, true),
                BorderFactory.createEmptyBorder(0,15,10,15)));
        //setBackground(new Color(246, 246, 246));
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        JLabel imageLabel;
        try{
            BufferedImage localImage = ImageIO.read(new URL("https://ik.imagekit.io/anthonyalando/Soft_Connect/books.png?updatedAt=1709730190595"));
            imageLabel = new JLabel(new ImageIcon(localImage));
            topPanel.add(imageLabel);
        }catch (IOException e){
            e.printStackTrace();
            try {
                BufferedImage localImage = ImageIO.read(Objects.requireNonNull(this.getClass().getResource("/assets/books.png")));
                imageLabel = new JLabel(new ImageIcon(localImage));
                topPanel.add(imageLabel);
            } catch (IOException var25) {
                var25.printStackTrace();
            }
        }


        JLabel labelAddBook = new JLabel("<html><font color=blue><bold>Add New Book</bold></font></html>");
        labelAddBook.setFont(new Font("Arial", Font.BOLD, 30));
        topPanel.add(labelAddBook);

        JPanel centerPanel = new JPanel();
        //centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(new Color(118, 143, 246));
        centerPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE,40));
        centerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40)); // Set maximum height
        centerPanel.setMinimumSize(new Dimension(1, 40)); // Set minimum height

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);

        JPanel bottomPanel = new JPanel(new GridBagLayout());
        JPanel bottomLeft = new JPanel(new GridBagLayout());
        JPanel bottomRight = new JPanel(new GridBagLayout());

        gbc.gridx = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.NORTH;
        bottomPanel.add(bottomLeft, gbc);
        gbc.gridx = 1;

        bottomPanel.add(bottomRight, gbc);
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5,5,5,5),
                BorderFactory.createLineBorder(new Color(232, 232, 232), 1, true)));

        bottomLeft.setBackground(bottomPanel.getBackground());
        bottomRight.setBackground(bottomPanel.getBackground());

        Map<String, JComponent> fields = createFields(bottomLeft, bottomRight, gbc);

        JPanel jp_bottomButtons = new JPanel(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 0;

        JButton jb_reset = new JButton("Reset");
        jb_reset.setMargin(new Insets(8,10,8,10));
        jp_bottomButtons.add(jb_reset, gbc);
        JButton jb_submit = new JButton("Submit");
        jb_submit.setMargin(new Insets(8,10,8,10));
        gbc.gridx = 1;
        jp_bottomButtons.add(jb_submit,gbc);
        jp_bottomButtons.setBackground(bottomPanel.getBackground());
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1;

        bottomPanel.add(jp_bottomButtons, gbc);

        jb_submit.addActionListener(e -> {
            if (verify(fields)){
                setBook(fields);
            }

        });

        add(topPanel);
        add(centerPanel);
        add(bottomPanel);

    }
    private void addToPanel(JPanel panel, Component component, GridBagConstraints gbc, int gridx, int grid_y, double weight_x) {
        gbc.gridx = gridx;
        gbc.gridy = grid_y;
        gbc.weightx = weight_x;
        gbc.weighty = 0;
        panel.add(component, gbc);
    }

    private Map<String, JComponent> createFields(JPanel parentLeft, JPanel panelRight, GridBagConstraints gbc){
        String[] labels = {"Title *","ISBN *","Author *","Edition *","Language *","Acquisition Date","Publisher","Number of Pages*",
        "Cover Image", "Summary","Shelf Location","Subject", "Total Copies*"};
        String[] inputFields = {"jtf_Title*","jtf_ISBN*","jtf_Author*","jtf_Edition*","jcb_Language*","jtf_AcquisitionDate","jtf_Publisher","jtf_NumberPages*",
                "jl_CoverImage", "jtf_Summary","jtf_ShelfLocation","jtf_Subject", "jtf_TotalCopies*"};

        List<JLabel> jLabelList = new ArrayList<>();
        List<JComponent> jc_inputs = new ArrayList<>();
        Map<String, JComponent> fields = new HashMap<>();
        int grid__y = 0;
        for(int i = 0; i < inputFields.length; i++){
            JLabel jLabel = new JLabel(labels[i]);

            JComponent jComponent;
            if(inputFields[i].contains("jtf_")){
                 jComponent = new JTextField(255);
            } else if (inputFields[i].contains("jl_")) {
                jComponent = new JLabel(inputFields[i].replace("jl_", ""));
            } else if (inputFields[i].contains("jcb_")) {
                String[] items = {"English", "French", "Germany"};
                jComponent = new JComboBox<>(new DefaultComboBoxModel<>(items));
            }else {
                jComponent = null;
            }
            jComponent.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(201, 201, 201), 1, true),
                    BorderFactory.createEmptyBorder(10,10,10,10)));
            jLabel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
            JPanel parent;

            if (i < inputFields.length / 2){
                parent = parentLeft;
            }else if(inputFields.length % 2 != 0 && i * 2 == inputFields.length - 1) {
                parent = parentLeft;
            }else {
                parent = panelRight;
                if((inputFields.length % 2 == 0 &&  (i + 2) * 2 - inputFields.length == 1 ) ||
                        (inputFields.length % 2 != 0 &&  (i + 1) * 2 - inputFields.length == 1 )){
                    grid__y = 0;
                }
            }



            addToPanel(parent,jLabel, gbc,0,grid__y,0.4);
            addToPanel(parent,jComponent, gbc,1,grid__y,1);
            grid__y++;
            if((inputFields.length % 2 == 0 && i * 2 == inputFields.length-1)){
                grid__y = 0;
            }

            jLabelList.add(jLabel);
            jc_inputs.add(jComponent);
            fields.put(inputFields[i], jComponent);
        }
        return fields;
    }
    private Library setBook(Map<String, JComponent> components) {
        Books book = new Books();

        book.setTitle(getTextFieldValue(components, "jtf_Title*"));
        book.setISBN(getTextFieldValue(components, "jtf_ISBN*"));
        book.setAuthor(getTextFieldValue(components, "jtf_Author*"));
        book.setEdition(getTextFieldValue(components, "jtf_Edition*"));
        book.setLanguage(getComboBoxSelectedValue(components, "jcb_Language*"));
        book.setAcq_date(getTextFieldValue(components, "jtf_AcquisitionDate"));
        book.setPublisher(getTextFieldValue(components, "jtf_Publisher"));
        try{
            book.setNo_Pages(Integer.parseInt(getTextFieldValue(components, "jtf_NumberPages*")));
        }catch (NumberFormatException e){
            JOptionPane.showMessageDialog(null, "Enter valid number\nNumber of pages");
        }

        book.setCover_Image(getLabelValue(components, "jl_CoverImage"));
        book.setSummary(getTextFieldValue(components, "jtf_Summary"));
        book.setLocation(getTextFieldValue(components, "jtf_ShelfLocation"));
        book.setSubject(getTextFieldValue(components, "jtf_Subject"));
        try{
            book.setTotal_copies(Integer.parseInt(getTextFieldValue(components, "jtf_TotalCopies*")));
        }catch (NumberFormatException e){
            JOptionPane.showMessageDialog(null, "Enter valid number\nTotal Copies");
        }


        return new Library(book);
    }

    private String getTextFieldValue(Map<String, JComponent> components, String key) {
        return ((JTextField) components.get(key)).getText();
    }

    private String getComboBoxSelectedValue(Map<String, JComponent> components, String key) {
        return ((JComboBox<?>) components.get(key)).getSelectedItem().toString();
    }

    private String getLabelValue(Map<String, JComponent> components, String key) {
        return ((JLabel) components.get(key)).getText();
    }
    private boolean verify(Map<String, JComponent> componentMap){
        for(Map.Entry<String, JComponent> entry : componentMap.entrySet()){
            String key = entry.getKey();
            JComponent component = entry.getValue();
            if (key.contains("*") && ((component instanceof JTextField && ((JTextField) component).getText().isEmpty()) ||
                    (component instanceof JComboBox<?> && ((JComboBox<?>) component).getSelectedIndex() == -1))){
                JOptionPane.showMessageDialog(null, "All fields marked * are required!");
                return false;
            }else{
                System.out.println("Field "+key+" verified.");
            }
        }
        return true;
    }

}
