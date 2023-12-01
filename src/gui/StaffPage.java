package src.gui;

import javax.swing.*;

import src.database.EasyDatabase;
import src.database.User;

import java.sql.*;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

public class StaffPage extends JPanel {
    
    EasyDatabase db;
    User user;

    ArrayList<JPanel> individualProduct = new ArrayList<JPanel>();
    ArrayList<JPanel> cards = new ArrayList<JPanel>();

    int currentCard = 0;

    public StaffPage() {
        this.setLayout(new CardLayout());

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

        for(int i=0;i<=individualProduct.size()/12;i++) {
            JButton next = new JButton("->");
            JButton previous = new JButton("<-");
            JPanel p = new JPanel();
            p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
            p.add(next);
            p.add(previous);
            try {
                p.add(individualProduct.get(i*12));
                p.add(individualProduct.get(i*12+1));
                p.add(individualProduct.get(i*12+2));
                p.add(individualProduct.get(i*12+3));
                p.add(individualProduct.get(i*12+4));
                p.add(individualProduct.get(i*12+5));
                p.add(individualProduct.get(i*12+6));
                p.add(individualProduct.get(i*12+7));
                p.add(individualProduct.get(i*12+8));
                p.add(individualProduct.get(i*12+9));
                p.add(individualProduct.get(i*12+10));
                p.add(individualProduct.get(i*12+11));
            } catch (Exception e) {

            }
            finally {
                cards.add(p);
            }
        }
        for(int i=0;i<cards.size();i++) {
            this.add(cards.get(i),Integer.toString(i));
        }

        this.addListeners(this);
    }
    public void addListeners(StaffPage panel)
    {
        CardLayout c1 = (CardLayout)(this.getLayout());
        for(JPanel c : cards) {
            JButton nxt = (JButton) c.getComponent(0);
            JButton pre = (JButton) c.getComponent(1);
            nxt.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    currentCard++;
                    if(currentCard > cards.size()) currentCard--;
                    c1.show(panel, Integer.toString(currentCard));
                    System.out.println(currentCard);            
                }
            });
            pre.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    currentCard--;
                    if(currentCard < 0) currentCard++;
                    c1.show(panel, Integer.toString(currentCard));
                    System.out.println(currentCard);            
                }
            });
        }

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

                    String selectSQL = "UPDATE Product SET numberInStock = numberInStock + 1 WHERE productID=?";
                    try {
                        PreparedStatement ps = db.getConnection().prepareStatement(selectSQL);
                        ps.setString(1, id);
                        ps.executeUpdate();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    
                    db.close();         
                    int x = (Integer.parseInt(stock.getText()) + 1);
                    stock.setText(Integer.toString(x));                                                
                }
            });
            m.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Please minus!");
                    db = new EasyDatabase();
                    String selectSQL = "UPDATE Product SET numberInStock = numberInStock - 1 WHERE productID=?";
                    try {
                        PreparedStatement ps = db.getConnection().prepareStatement(selectSQL);
                        ps.setString(1, id);
                        ps.executeUpdate();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
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