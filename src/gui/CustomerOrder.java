package src.gui;

import src.database.User;
import src.database.EasyDatabase;
import src.database.Order;
import src.database.OrderLine;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

public class CustomerOrder extends JPanel {

    private User user;
    EasyDatabase db;
    Order currentOrder;
    String status;

    public void setUser(User user) {
        this.user = user;
    }

    public void setStatus(String str) {
        status = str;
    }

    public CustomerOrder() {
        //
    }

    public void renderLoggedInPage() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        if (!(status == null)){
            JLabel statusDisplay = new JLabel(status);
            this.add(statusDisplay);
        }

        db = new EasyDatabase();
        if (db.checkIfOrderExsistsForCustomer(user.getID())) {
            JLabel currentOrderText = new JLabel("Your current order:");

            int orderNum = db.getOrderNumber(user.getID());
            try {
                Order currentOrder = new Order(orderNum);



                c.anchor = GridBagConstraints.FIRST_LINE_START;
                c.fill = GridBagConstraints.HORIZONTAL;
                c.gridx = 0;
                c.gridy = 0;
                c.gridheight = 1;
                c.gridwidth = 1;
                c.weightx = 0.4;
                c.weighty = 0.4;
                c.insets = new Insets(50,50,0,0);
                this.add(currentOrderText, c);

                JLabel orderNumDisplay = new JLabel("Order number " + currentOrder.getOrderNumber());
                c.anchor = GridBagConstraints.FIRST_LINE_START;
                c.fill = GridBagConstraints.HORIZONTAL;
                c.gridx = 0;
                c.gridy = 1;
                c.gridheight = 1;
                c.gridwidth = 1;
                c.weightx = 0.4;
                c.weighty = 0.4;
                c.insets = new Insets(0,50,0,0);
                this.add(orderNumDisplay, c);

                DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
                String strDate = dateFormat.format(currentOrder.getDate());

                JLabel orderDate = new JLabel(strDate);
                c.anchor = GridBagConstraints.FIRST_LINE_START;
                c.fill = GridBagConstraints.HORIZONTAL;
                c.gridx = 1;
                c.gridy = 1;
                c.gridheight = 1;
                c.gridwidth = 1;
                c.weightx = 0.4;
                c.weighty = 0.4;
                c.insets = new Insets(0,0,0,0);
                this.add(orderDate, c);  
                
                ArrayList<OrderLine> currentOrderOrderLines = currentOrder.getOrderLines();

                JPanel allOrderLines = new JPanel();
                allOrderLines.setLayout(new GridBagLayout());


                for (int i = 0; i < currentOrderOrderLines.size(); i++) {
                    OrderLine line = currentOrderOrderLines.get(i);
                    JPanel orderLinePanel = createPanelForOrderLine(line);

                    c.anchor = GridBagConstraints.FIRST_LINE_START;
                    c.fill = GridBagConstraints.VERTICAL;
                    c.gridx = 0;
                    c.gridy = GridBagConstraints.RELATIVE;
                    c.gridheight = 1;
                    c.gridwidth = 1;
                    c.weightx = 0.4;
                    c.weighty = 0.4;
                    c.insets = new Insets(0,0,25,0);
                    allOrderLines.add(orderLinePanel, c);
                }

                c.anchor = GridBagConstraints.FIRST_LINE_START;
                c.fill = GridBagConstraints.HORIZONTAL;
                c.gridx = 0;
                c.gridy = 2;
                c.gridheight = 1;
                c.gridwidth = 1;
                c.weightx = 0.4;
                c.weighty = 0.4;
                c.insets = new Insets(0,50,0,0);

                JScrollPane currentOrderPane = new JScrollPane(allOrderLines);

                currentOrderPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                currentOrderPane.getViewport().setMinimumSize(new Dimension(450, 100));
                currentOrderPane.getViewport().setPreferredSize(new Dimension(450, 100));
                currentOrderPane.setBorder(null);

                this.add(currentOrderPane, c);

                JLabel orderPrice = new JLabel("£" + Integer.toString(currentOrder.getTotalOrderCost()));
                c.anchor = GridBagConstraints.FIRST_LINE_START;
                c.fill = GridBagConstraints.HORIZONTAL;
                c.gridx = 0;
                c.gridy = 3;
                c.gridheight = 1;
                c.gridwidth = 1;
                c.weightx = 0.4;
                c.weighty = 0.4;
                c.insets = new Insets(0,50,0,0);
                this.add(orderPrice, c);

                JLabel orderStatus = new JLabel(currentOrder.getStatus().name());
                c.anchor = GridBagConstraints.FIRST_LINE_START;
                c.fill = GridBagConstraints.HORIZONTAL;
                c.gridx = 1;
                c.gridy = 3;
                c.gridheight = 1;
                c.gridwidth = 1;
                c.weightx = 0;
                c.weighty = 0;
                c.insets = new Insets(0,0,0,0);
                this.add(orderStatus, c);

                JButton orderOrderBtn = new JButton("Order");
                orderOrderBtn.addActionListener(new buttonListener());
                c.anchor = GridBagConstraints.FIRST_LINE_START;
                c.fill = GridBagConstraints.HORIZONTAL;
                c.gridx = 2;
                c.gridy = 3;
                c.gridheight = 1;
                c.gridwidth = 1;
                c.weightx = 0;
                c.weighty = 0;
                c.insets = new Insets(0,0,0,0);
                this.add(orderOrderBtn, c);

                




            } catch (SQLException e) {
              e.printStackTrace();
            }



        } else {
            JLabel currentOrderText = new JLabel("You have no current orders");
        }

        JScrollPane pastOrders = new JScrollPane();


        pastOrders.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        pastOrders.getViewport().setMinimumSize(new Dimension(450, 250));
        pastOrders.getViewport().setPreferredSize(new Dimension(450, 250));
        pastOrders.setBorder(null);
    }

    public JPanel createPanelForOrderLine(OrderLine orderLine) {

        JPanel p = new JPanel();

        p.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel productCode = new JLabel(orderLine.getProductCode());
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.weightx = 0.4;
        c.weighty = 0.4;
        c.insets = new Insets(0,10,0,0);
        p.add(productCode, c);  

        JLabel productBrand = new JLabel(orderLine.getProduct().getBrand());
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 0;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.weightx = 0.4;
        c.weighty = 0.4;
        c.insets = new Insets(0,20,0,0);
        p.add(productBrand, c);  

        JLabel productName = new JLabel(orderLine.getProduct().getName());
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 0;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.weightx = 0.4;
        c.weighty = 0.4;
        c.insets = new Insets(0,20,0,0);
        p.add(productName, c);
        
        JLabel quantity = new JLabel(Integer.toString(orderLine.getQuantity()));
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 3;
        c.gridy = 0;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.weightx = 0.4;
        c.weighty = 0.4;
        c.insets = new Insets(0,20,0,0);
        p.add(quantity, c);  

        Double lineCostValue = orderLine.getProduct().getPrice() * orderLine.getQuantity();

        JLabel lineCost = new JLabel("£" + Double.toString(lineCostValue));
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 4;
        c.gridy = 0;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.weightx = 0.4;
        c.weighty = 0.4;
        c.insets = new Insets(0,20,0,0);
        p.add(lineCost, c); 

        
        return p;
    }

    class buttonListener implements ActionListener {
        public void actionPerformed(ActionEvent event){
            db = new EasyDatabase();
            if (db.checkIfCardDetailsExsists(user.getID())) {
                currentOrder.changeStatus();
            } else {
                status = "Please enter card details in account settings";
                refresh();
            }
        }
    }

    public void refresh() {
        revalidate();
        renderLoggedInPage();
        repaint();
    }

}
