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

public class PastOrders extends JPanel {

    private User user;
    EasyDatabase db;
    String status;

    public void setUser(User user) {
        this.user = user;
    }

    public PastOrders() {
        //
    }

    public void renderLoggedInPage() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel pastOrdersText = new JLabel("PastOrders");
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.weightx = 0;
        c.weighty = 0;
        c.insets = new Insets(30,0,0,0);
        this.add(pastOrdersText, c);


        JPanel wholePastPanel = new JPanel();

        JScrollPane pastOrders = new JScrollPane(wholePastPanel);

        db = new EasyDatabase();

        try {

            ResultSet rs = db.getAllOrdersFromUser(user.getID());

            while (rs.next()) {
                JPanel orderPanel = new JPanel();
                orderPanel.setLayout(new GridBagLayout());

                Order order = new Order(rs.getInt("orderNumber"));

                JTextField orderNumber = new JTextField(order.getOrderNumber());
                c.anchor = GridBagConstraints.FIRST_LINE_START;
                c.fill = GridBagConstraints.HORIZONTAL;
                c.gridx = 0;
                c.gridy = 0;
                c.gridheight = 1;
                c.gridwidth = 1;
                c.weightx = 0.4;
                c.weighty = 0.4;
                c.insets = new Insets(0,50,0,0);
                orderPanel.add(orderNumber, c);

                DateFormat df = new SimpleDateFormat("mm/yy");
                JTextField orderDate = new JTextField(df.format(order.getDate()));
                c.anchor = GridBagConstraints.FIRST_LINE_START;
                c.fill = GridBagConstraints.HORIZONTAL;
                c.gridx = 1;
                c.gridy = 0;
                c.gridheight = 1;
                c.gridwidth = 1;
                c.weightx = 0.4;
                c.weighty = 0.4;
                c.insets = new Insets(0,20,0,0);
                orderPanel.add(orderDate, c);

                JTextField orderStatus = new JTextField(order.getStatus().name());
                c.anchor = GridBagConstraints.FIRST_LINE_START;
                c.fill = GridBagConstraints.HORIZONTAL;
                c.gridx = 2;
                c.gridy = 0;
                c.gridheight = 1;
                c.gridwidth = 1;
                c.weightx = 0.4;
                c.weighty = 0.4;
                c.insets = new Insets(0,20,0,0);
                orderPanel.add(orderDate, c);

                ///

                c.anchor = GridBagConstraints.FIRST_LINE_START;
                c.fill = GridBagConstraints.VERTICAL;
                c.gridx = 0;
                c.gridy = GridBagConstraints.RELATIVE;
                c.gridheight = 1;
                c.gridwidth = 1;
                c.weightx = 0.4;
                c.weighty = 0.4;
                c.insets = new Insets(0,0,20,0);
                wholePastPanel.add(orderPanel, c);

            }
        } catch (SQLException e) {

        }




        pastOrders.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        pastOrders.getViewport().setMinimumSize(new Dimension(450, 250));
        pastOrders.getViewport().setPreferredSize(new Dimension(450, 250));
        pastOrders.setBorder(null);

        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.weightx = 0.4;
        c.weighty = 0.4;
        c.insets = new Insets(20,0,25,0);

        this.add(pastOrders);
    }
}