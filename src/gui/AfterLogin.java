package src.gui;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

public class AfterLogin extends JPanel {
    public AfterLogin() {
        JLabel warning = new JLabel();
        warning.setText("You are now logged in!");
        warning.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(warning);

        JLabel info = new JLabel();
        info.setText("Please use the account menu on the top left of your screen to navigate the system.");
        this.add(info);
    }
}
