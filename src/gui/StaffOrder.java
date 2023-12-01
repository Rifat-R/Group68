package src.gui;
import javax.swing.*;

import src.database.*;

import java.sql.*;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

public class StaffOrder extends JPanel {
    
    EasyDatabase db;
    User user;

    // View Details

    protected JPanel detailsPanel = new JPanel();
    protected JButton backButton = new JButton("Back");
    protected JLabel nameLabel = new JLabel();
    protected ArrayList<OrderLine> orderLines = new ArrayList<>();
    protected ArrayList<JPanel> olPanels = new ArrayList<>();

    // Update Order

    ArrayList<JPanel> individualOrder = new ArrayList<JPanel>();
    ArrayList<JPanel> cards = new ArrayList<JPanel>();
    

    int currentCard = 0;

    public StaffOrder() {
        this.setLayout(new CardLayout());  
        detailsPanel.setLayout(new BoxLayout(detailsPanel,BoxLayout.Y_AXIS));      

        detailsPanel.add(backButton);
        detailsPanel.add(nameLabel);

        db = new EasyDatabase();
        String selectSQL = "SELECT * FROM `Order`";
        db.executeQuery(selectSQL);
        try {
            while(db.resultSet.next()) {
                Integer orderNumber = db.resultSet.getInt("orderNumber");
                Integer userID = db.resultSet.getInt("userID");
                String orderStatus = db.resultSet.getString("orderStatus");
                if(orderStatus == "Fulfilled") continue;
                String date = db.resultSet.getDate("orderDate").toString();

                JLabel tempLabel0 = new JLabel("order #: ");
                JLabel noLabel = new JLabel(orderNumber.toString());
                JLabel tempLabel1 = new JLabel(" User: ");
                JLabel idLabel = new JLabel(userID.toString());
                JLabel dateLabel = new JLabel(date);

                JButton view = new JButton("View Details");
                JComboBox<String> status = new JComboBox<>();
                status.addItem("Pending");
                status.addItem("Confirmed");
                status.addItem("Fulfilled");
                status.setSelectedItem(orderStatus);
                JButton submit = new JButton("Submit");

                JPanel temp1 = new JPanel();
                temp1.setLayout(new FlowLayout());
                temp1.add(tempLabel0);
                temp1.add(noLabel);
                temp1.add(tempLabel1);
                temp1.add(idLabel);
                temp1.add(view);
                temp1.add(status);
                temp1.add(submit);
                temp1.add(dateLabel);

                individualOrder.add(temp1);                          
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {            
            db.close();
        }       

        for(int i=0;i<=individualOrder.size()/12;i++) {
            JButton next = new JButton("->");
            JButton previous = new JButton("<-");
            JPanel p = new JPanel();
            p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
            JPanel p2 = new JPanel();
            p2.add(previous);
            p2.add(next);
            try {
                p.add(p2);
                p.add(individualOrder.get(i*12));
                p.add(individualOrder.get(i*12+1));
                p.add(individualOrder.get(i*12+2));
                p.add(individualOrder.get(i*12+3));
                p.add(individualOrder.get(i*12+4));
                p.add(individualOrder.get(i*12+5));
                p.add(individualOrder.get(i*12+6));
                p.add(individualOrder.get(i*12+7));
                p.add(individualOrder.get(i*12+8));
                p.add(individualOrder.get(i*12+9));
                p.add(individualOrder.get(i*12+10));
                p.add(individualOrder.get(i*12+11));
            } catch (Exception e) {

            }
            finally {
                cards.add(p);
            }
        }
        for(int i=0;i<cards.size();i++) {
            this.add(cards.get(i),Integer.toString(i));
            this.add(detailsPanel, "ViewDetails");
        }

        this.addListeners(this);
    }

    public void addListeners(StaffOrder panel) {
        CardLayout c1 = (CardLayout)(this.getLayout());

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                c1.show(panel, Integer.toString(currentCard));                
            }
        });

        for(JPanel c : cards) {
            JButton pre = (JButton) ((JPanel)c.getComponent(0)).getComponent(0);
            JButton nxt = (JButton) ((JPanel)c.getComponent(0)).getComponent(1);
            nxt.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    currentCard++;
                    if(currentCard >= cards.size()) currentCard--;
                    c1.show(panel, Integer.toString(currentCard));
                }
            });
            pre.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    currentCard--;
                    if(currentCard < 0) currentCard++;
                    c1.show(panel, Integer.toString(currentCard));
                }
            });
        }

        for(JPanel iO : individualOrder) {            
            JButton view = (JButton) iO.getComponent(4);
            JButton sub = (JButton) iO.getComponent(6);
            JComboBox<String> stat = (JComboBox<String>) iO.getComponent(5);
            view.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    User user1;
                    try {
                        user1 = new User(Integer.parseInt(((JLabel)iO.getComponent(3)).getText()));      
                        nameLabel.setText(user1.getFullName());
                        
                        orderLines.clear();
                        int orderNumber = Integer.parseInt(((JLabel)iO.getComponent(1)).getText());
                        Order order = new Order(orderNumber);
                        orderLines.addAll(order.getOrderLines());

                    } catch (SQLException f) {
                        f.printStackTrace();
                    }

                    olPanels.clear();

                    for(OrderLine ol : orderLines) {
                        JPanel j = new JPanel();
                        j.add(new JLabel(ol.getProductCode()));
                        j.add(new JLabel(" Quantity: " + ol.getQuantity()));
                        olPanels.add(j);
                    }
                    
                    for(JPanel oP : olPanels) {
                        detailsPanel.add(oP);
                    }

                    c1.show(panel, "ViewDetails");
                }
            });
            sub.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) { 
                    String statusUpdate = (String)stat.getSelectedItem();
                    EasyDatabase db = new EasyDatabase();
                    String x = ((JLabel)iO.getComponent(1)).getText();
                    int y = Integer.parseInt(x);
                    PreparedStatement preparedStatement;
                    try {
                        preparedStatement = db.getConnection().prepareStatement("UPDATE `Order` SET orderStatus = ? WHERE orderNumber= ?");
                        preparedStatement.setString(1, statusUpdate);
                        preparedStatement.setInt(2, y);
                        preparedStatement.executeUpdate();
                        preparedStatement.close();
                    } catch (SQLException f) {
                        f.printStackTrace();
                    } finally {

                    }
                }
            });
        }
    }
}
