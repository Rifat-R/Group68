package src;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import src.User.Role;

import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

public class HomePage extends JPanel {

    EasyDatabase db;
    JTable table;

    // Only for registered users
    public HomePage() {
        //this.setLayout(new GridBagLayout());
        // GridBagConstraints c = new GridBagConstraints();

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
                model.addRow(new Object[]{name, brand, price, gauge, era, dcc});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        JScrollPane scrollPane = new JScrollPane(table);

        this.add(scrollPane);   
    }
}
