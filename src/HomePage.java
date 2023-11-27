package src;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import src.User.Role;

import java.sql.*;
import java.util.concurrent.Flow;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

public class HomePage extends JPanel {

    EasyDatabase db;
    JTable table;

    // Only for registered users
    public HomePage() {

        this.setLayout(new GridBagLayout());
        /*
        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);
        table.setDefaultEditor(Object.class, null);
        table.setFillsViewportHeight(true);

        model.addColumn("Name"); 
        model.addColumn("Brand");
        model.addColumn("Price"); 
        model.addColumn("Gauge"); 
        model.addColumn("Era"); 
        model.addColumn("dCCCode");
        */
        GridBagConstraints c = new GridBagConstraints();

        Box container = Box.createVerticalBox();
        

        JPanel individualProduct = new JPanel();
        individualProduct.setLayout(new GridBagLayout());


        db = new EasyDatabase();
        try {
            ResultSet rs = db.getProducts();
            while (rs.next()){
                String name = rs.getString(2);
                String brand = rs.getString(3);
                String price = rs.getString(4);
                String gauge = rs.getString(5);
                String era = rs.getString(6);
                String dcc = rs.getString(7);
                //model.addRow(new Object[]{name, brand, price, gauge, era, dcc});

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
                Component invs = Box.createHorizontalStrut(250 - (label1.getPreferredSize().width));
                f.add(invs);

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

                
                JButton bag = new JButton("Add");
                c.fill = GridBagConstraints.VERTICAL;
                c.gridx = 2;
                c.gridy = 0;
                c.gridheight = 2;
                c.gridwidth = 1;
                c.insets = new Insets(0,30,0,0);
                f.add(bag, c);

                SpinnerModel model = new SpinnerNumberModel(1, 1, 100, 1);     
                JSpinner spinner = new JSpinner(model);
                spinner.setEditor(new JSpinner.DefaultEditor(spinner));
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

        c.anchor = GridBagConstraints.PAGE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0,0,40,0);
        this.add(scrollPane, c);

    }
}
