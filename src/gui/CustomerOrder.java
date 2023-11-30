package src.gui;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

public class CustomerOrder extends JPanel {
    public CustomerOrder() {
        this.setLayout(new GridBagLayout());

        JScrollPane currentOrder = new JScrollPane();
        JScrollPane pastOrders = new JScrollPane();

        currentOrder.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        currentOrder.getViewport().setMinimumSize(new Dimension(450, 250));
        currentOrder.getViewport().setPreferredSize(new Dimension(450, 250));
        currentOrder.setBorder(null);

        pastOrders.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        pastOrders.getViewport().setMinimumSize(new Dimension(450, 250));
        pastOrders.getViewport().setPreferredSize(new Dimension(450, 250));
        pastOrders.setBorder(null);
    }

    public void createPanelForOrderLine() {
        
    }
}
