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

    // instance variables

    EasyDatabase db;
    User user;

    // Add products
    protected JButton backButton = new JButton("Back");
    protected JPanel addProducts = new JPanel();
    protected JTextField itemID = new JTextField(6);
    protected JTextField itemName = new JTextField(20);
    protected JTextField itemBrand = new JTextField(20);
    protected JTextField itemPrice = new JTextField(10);
    protected JComboBox<String> itemGauge = new JComboBox<>();
    protected JComboBox<String> itemEra = new JComboBox<>();
    protected JComboBox<String> itemDCC = new JComboBox<>();
    protected JTextField itemStock = new JTextField(3);
    protected JButton submitButton = new JButton("Submit");
    protected JLabel errorLabel = new JLabel();

    // Update Products
    ArrayList<JPanel> individualProduct = new ArrayList<JPanel>();
    ArrayList<JPanel> cards = new ArrayList<JPanel>();
    int currentCard = 0;

    public StaffPage() {
        // Card layout for main panel; Boxlayout for listing details
        this.setLayout(new CardLayout());
        addProducts.setLayout(new BoxLayout(addProducts, BoxLayout.Y_AXIS));

        itemGauge.addItem("OO Gauge");
        itemGauge.addItem("N Gauge");
        itemGauge.addItem("TT Gauge");

        for(int i=1;i<=11;i++) {
            itemEra.addItem("Era "+i);
        }

        itemDCC.addItem("Analogue");
        itemDCC.addItem("DCC-Sound");
        itemDCC.addItem("DCC-Fitted");
        itemDCC.addItem("DCC-Ready");
        
        // Adding JSwing elements to AddProducts card
        addProducts.add(backButton);
        JPanel temp = new JPanel();
        temp.add(new JLabel("ID: "));
        temp.add(itemID);
        addProducts.add(temp);
        temp = new JPanel();
        temp.add(new JLabel("Name: "));
        temp.add(itemName);
        addProducts.add(temp);
        temp = new JPanel();
        temp.add(new JLabel("Brand: "));
        temp.add(itemBrand);
        addProducts.add(temp);
        temp = new JPanel();
        temp.add(new JLabel("Price: "));
        temp.add(itemPrice);
        addProducts.add(temp);
        temp = new JPanel();
        temp.add(new JLabel("Gauge: "));
        temp.add(itemGauge);
        addProducts.add(temp);
        temp = new JPanel();
        temp.add(new JLabel("Era: "));
        temp.add(itemEra);
        addProducts.add(temp);
        temp = new JPanel();
        temp.add(new JLabel("DCC: "));
        temp.add(itemDCC);
        addProducts.add(temp);
        temp = new JPanel();
        temp.add(new JLabel("Stock: "));
        temp.add(itemStock);
        addProducts.add(temp);
        addProducts.add(errorLabel);
        addProducts.add(submitButton);
        
        // Listing all products, adding details to an individual panel in the boxlayout
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

                // adding to an arbitrary flow JPanel that lets me list details side by side.
                JPanel temp1 = new JPanel();
                temp1.setLayout(new FlowLayout());
                temp1.add(tempLabel0);
                temp1.add(idLabel);
                temp1.add(tempLabel1);
                temp1.add(nameLabel);
                temp1.add(tempLabel2);
                temp1.add(brandLabel);
                temp1.add(tempLabel3);
                temp1.add(priceLabel);
                temp1.add(plus);
                temp1.add(minus);
                temp1.add(stockLabel);

                individualProduct.add(temp1);                          
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {            
            db.close();
        }       


        // Creating the cards
        for(int i=0;i<=individualProduct.size()/12;i++) {
            JButton next = new JButton("->");
            JButton previous = new JButton("<-");
            JButton add = new JButton("Add");
            JPanel p = new JPanel();
            p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
            JPanel p2 = new JPanel();
            p2.add(add);
            p2.add(previous);
            p2.add(next);
            try { // Add buttons and orders to a card
                p.add(p2);
                for(int j=0; j<12;j++) p.add(individualProduct.get(i*12+j));
            } catch (Exception e) { // break instance at end of individualOrder.
                // This catch is expected; Nothing needs to be done here.
            }
            finally {
                cards.add(p);
            }
        }
        for(int i=0;i<cards.size();i++) {
            this.add(cards.get(i),Integer.toString(i));
            this.add(addProducts, "AddProduct");
        }

        this.addListeners(this);
    }
    public void addListeners(StaffPage panel)
    {
        CardLayout c1 = (CardLayout)(this.getLayout());

        // Returns to viewing orders
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                c1.show(panel, Integer.toString(currentCard));                
            }
        });

        // Listeners are needed for the forward and backward buttons in each card.
        for(JPanel c : cards) {
            JButton add = (JButton) ((JPanel)c.getComponent(0)).getComponent(0);
            JButton pre = (JButton) ((JPanel)c.getComponent(0)).getComponent(1);
            JButton nxt = (JButton) ((JPanel)c.getComponent(0)).getComponent(2);
            nxt.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Increment currentCard - goes forward in the list of product cards
                    currentCard++;
                    if(currentCard >= cards.size()) currentCard--;
                    c1.show(panel, Integer.toString(currentCard));
                }
            });
            pre.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // see above, but the opposite
                    currentCard--;
                    if(currentCard < 0) currentCard++;
                    c1.show(panel, Integer.toString(currentCard));
                }
            });
            add.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Goes to different page "AddProduct"
                    currentCard = 0;
                    c1.show(panel, "AddProduct");
                }
            });
        }

        // Editing products
        for(JPanel iU : individualProduct) {
            JButton p = (JButton) iU.getComponent(8); //plus button
            JButton m = (JButton) iU.getComponent(9); //minus button
            String id = ((JLabel) iU.getComponent(1)).getText(); //product ID
            JLabel stock = (JLabel) iU.getComponent(10); // Quantity of product in stock
            p.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Simply increments stock
                    db = new EasyDatabase();
                    String selectSQL = "UPDATE Product SET numberInStock = numberInStock + 1 WHERE productID = ?";
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
                // Simply decrements stock
                public void actionPerformed(ActionEvent e) {
                    db = new EasyDatabase();
                    String selectSQL = "UPDATE Product SET numberInStock = numberInStock - 1 WHERE productID = ?";
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
        submitButton.addActionListener(new ActionListener() {
            // First sanitises the input, then will add the product to database (function at bottom of StaffPage.java)
            public void actionPerformed(ActionEvent e) {
                if (!itemID.getText().matches("^[A-Z].*$")) {
                    errorLabel.setText("Item ID must begin with a letter");
                }
                else if (itemID.getText().length() < 4) {
                    errorLabel.setText("ID is too short");
                }
                else if (itemName.getText().isBlank() || itemBrand.getText().isBlank() 
                        || itemPrice.getText().isBlank() || itemStock.getText().isBlank()) {
                    errorLabel.setText("Fields cannot be left blank");
                }
                else addProductToDatabase();
            }
        });
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void addProductToDatabase() {
        db = new EasyDatabase();        
        try {
            String selectSQL = 
            "INSERT INTO Product (productID, productName, ProductBrand, productPrice, productGauge, productEra, dCCCode, numberInStock) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = db.getConnection().prepareStatement(selectSQL);
            preparedStatement.setString(1, itemID.getText());
            preparedStatement.setString(2, itemName.getText());
            preparedStatement.setString(3, itemBrand.getText());
            preparedStatement.setFloat(4, Float.parseFloat(itemPrice.getText()));
            preparedStatement.setString(5, (String)itemGauge.getSelectedItem());
            preparedStatement.setString(6, (String)itemEra.getSelectedItem());
            preparedStatement.setString(7, (String)itemDCC.getSelectedItem());
            preparedStatement.setInt(8, Integer.parseInt(itemStock.getText()));
            preparedStatement.executeUpdate();
            preparedStatement.close();
            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
           errorLabel.setText("Price and stock must be a decimal and integer, respectively."); 
        }
    }
}