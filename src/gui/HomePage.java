package src.gui;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import src.database.EasyDatabase;
import src.database.Product;
import src.database.User;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;


public class HomePage extends JPanel {

    EasyDatabase db;
    JTable table;

    // Only for registered users

    User user;

    Map map = new HashMap<String, Integer>();

    public HomePage() {

        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        

        JPanel individualProduct = new JPanel();
        individualProduct.setLayout(new GridBagLayout());


        db = new EasyDatabase();
        try {
            ResultSet rs = db.getProducts();
            while (rs.next()){
                String productID = rs.getString(1);
                String name = rs.getString(2);
                String brand = rs.getString(3);
                String price = rs.getString(4);
                String gauge = rs.getString(5);
                String era = rs.getString(6);
                String dcc = rs.getString(7);
                Integer numberInStock = rs.getInt(8);

                if (numberInStock == 0) {
                    continue;
                }

                map.put(productID, 0);

                JPanel f = new JPanel();
                f.setLayout(new GridBagLayout());
                
                JLabel label1 = new JLabel(name);
                //label1.setText(name);

                c.anchor = GridBagConstraints.FIRST_LINE_START;
                c.fill = GridBagConstraints.HORIZONTAL;
                c.gridx = 0;
                c.gridy = 0;
                c.gridwidth = 1;
                c.insets = new Insets(0,0,0,0);
                f.add(label1, c);

                //System.out.println(name);
                //System.out.println(250 - label1.getPreferredSize().width);

                JLabel label2 = new JLabel(brand);
                label2.setText(brand);
                c.fill = GridBagConstraints.HORIZONTAL;
                c.gridx = 0;
                c.gridy = 1;
                c.gridwidth = 1;
                f.add(label2, c);

                JLabel label3 = new JLabel("£" + price);
                label3.setText("£" + price);
                c.fill = GridBagConstraints.HORIZONTAL;
                c.gridx = 1;
                c.gridy = 1;
                c.gridwidth = 1;
                f.add(label3, c);

                int nameWidth = label1.getPreferredSize().width;
                int brandWidth = label2.getPreferredSize().width;
                int pound = label3.getPreferredSize().width;

                if (nameWidth >= brandWidth) {
                    Component invs = Box.createHorizontalStrut(300 - (nameWidth + pound));
                    f.add(invs);
                } else {
                    Component invs = Box.createHorizontalStrut(300 - (brandWidth + pound));
                    f.add(invs);
                }

                
                JButton bag = new JButton("Add");
                bag.setName(productID);
                bag.addActionListener(new buttonListener());
                c.fill = GridBagConstraints.VERTICAL;
                c.gridx = 2;
                c.gridy = 0;
                c.gridheight = 2;
                c.gridwidth = 1;
                c.insets = new Insets(0,0,0,0);
                f.add(bag, c);

                SpinnerModel model = new SpinnerNumberModel(0, 0, 100, 1);     
                JSpinner spinner = new JSpinner(model);
                spinner.setEditor(new JSpinner.DefaultEditor(spinner));
                spinner.setName(productID);
                spinner.addChangeListener(new spinnerListener());
                c.fill = GridBagConstraints.VERTICAL;
                c.gridx = 3;
                c.gridy = 0;
                c.gridheight = 2;
                c.gridwidth = 1;
                c.insets = new Insets(0,30,0,10);
                f.add(spinner, c);
                

                c.anchor = GridBagConstraints.FIRST_LINE_START;
                c.fill = GridBagConstraints.VERTICAL;
                c.gridx = 0;
                c.gridy = GridBagConstraints.RELATIVE;
                c.gridheight = 1;
                c.gridwidth = 1;
                c.insets = new Insets(0,0,25,0);
                individualProduct.add(f, c);
                individualProduct.validate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        individualProduct.validate();
        
        

        JScrollPane scrollPane = new JScrollPane(individualProduct);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getViewport().setMinimumSize(new Dimension(450, 500));
        scrollPane.getViewport().setPreferredSize(new Dimension(450, 500));
        scrollPane.setBorder(null);

        
        JLabel title = new JLabel("View Products");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setText("View Products");
        c.anchor = GridBagConstraints.PAGE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 1;
        c.gridwidth = 1;
        //c.weightx = 1;
        c.weighty = 1;
        c.insets = new Insets(20,0,0,10);
        this.add(title, c);
        

        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.NONE;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        //c.weightx = 1;
        c.weighty = 1;
        c.insets = new Insets(10,0,20,0);
        this.add(scrollPane, c);

        db.close();

    }

    public void setUser(User user) {
        this.user = user;
    }

    class buttonListener implements ActionListener {
        public void actionPerformed(ActionEvent event){

            // Order Line
            Object src = event.getSource();
            String productID = ((JButton)src).getName();  
            Integer orderQuantity = (Integer)map.get(productID);   
            if (orderQuantity == 0) {
                return;
            }

            try {
                Product orderLineProduct = new Product(productID);
                Double orderLinePriceIndividual = orderLineProduct.getPrice();
                String orderLineBrand = orderLineProduct.getBrand();
                String orderLineName = orderLineProduct.getName();
                Double orderLinePrice = orderLinePriceIndividual * orderQuantity;
                db.addOrderLine(productID, orderLineBrand, orderLineName, orderQuantity, orderLinePrice);
                db.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    class spinnerListener implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
            JSpinner spinner = (JSpinner)e.getSource();
            int value = (int)spinner.getValue();
            String spinnerName = (String)spinner.getName();
            map.put(spinnerName, value);
        }
    }
}