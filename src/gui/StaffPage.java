package src.gui;

import javax.swing.*;

import src.database.EasyDatabase;
import src.database.User;
import src.database.User.Role;

import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

import java.util.ArrayList;


public class StaffPage extends JPanel {

    EasyDatabase db;
    User user;

    ArrayList<JPanel> individualProduct = new ArrayList<JPanel>();

    public StaffPage() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        db = new EasyDatabase();
        String selectSQL = "SELECT * FROM Product";
        db.executeQuery(selectSQL);
        try {
            while(db.resultSet.next()) {
                String productID = db.resultSet.getString("productID");
                String name = db.resultSet.getString("productName");
                String brand = db.resultSet.getString("ProductBrand");
                String price = db.resultSet.getString("productPrice");
                Integer numberInStock = db.resultSet.getInt("numberInStock");


                JLabel tempLabel0 = new JLabel("ID: ");
                JLabel idLabel = new JLabel(productID);
                JLabel tempLabel1 = new JLabel("Name: ");
                JLabel nameLabel = new JLabel(name);
                JLabel tempLabel2 = new JLabel(": ");
                JLabel brandLabel = new JLabel(brand);
                JLabel tempLabel3 = new JLabel(": ");
                JLabel priceLabel = new JLabel(price);
                JButton plus = new JButton("+");
                JButton minus = new JButton("-");
                JLabel stockLabel = new JLabel(numberInStock.toString());

                JPanel temp = new JPanel();
                temp.setLayout(new FlowLayout());
                temp.add(tempLabel0);
                temp.add(idLabel);
                temp.add(tempLabel1);
                temp.add(nameLabel);
                temp.add(tempLabel2);
                temp.add(brandLabel);
                temp.add(tempLabel3);
                temp.add(priceLabel);
                temp.add(plus);
                temp.add(minus);
                temp.add(stockLabel);

                individualProduct.add(temp);                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {            
            db.close();
        }
        for(JPanel i : individualProduct) {
            this.add(i);
        }
        this.addListeners();
    }
    public void addListeners()
    {
        for(JPanel iU : individualProduct) {
            JButton p = (JButton) iU.getComponent(8);
            JButton m = (JButton) iU.getComponent(9);
            JLabel n = (JLabel) iU.getComponent(1);
            String id = n.getText();
            JLabel stock = (JLabel) iU.getComponent(10);
            p.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Please plus!");
                    db = new EasyDatabase();
                    String selectSQL = "UPDATE Product SET numberInStock = numberInStock + 1 WHERE productID='"+id+"'";
                    db.executeUpdate(selectSQL);
                    db.close();         
                    int x = (Integer.parseInt(stock.getText()) + 1);
                    stock.setText(Integer.toString(x));                                                
                }
            });
            m.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Please minus!");
                    db = new EasyDatabase();
                    String selectSQL = "UPDATE Product SET numberInStock = numberInStock - 1 WHERE productID='"+id+"'";
                    db.executeUpdate(selectSQL);
                    db.close();                        
                    int x = (Integer.parseInt(stock.getText()) - 1);
                    stock.setText(Integer.toString(x));                              
                }
            });
        }
    }
    public void setUser(User user)
    {
        this.user = user;
    }
}