package src;
import javax.swing.*;

import src.User.Role;

import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;


public class UpdateAccountDetails extends JPanel {
    
    private User user;

    public void setUser(User user) {
        this.user = user;

    }

    public UpdateAccountDetails() {
        JLabel warning = new JLabel();
        warning.setText("Please log in to change account details.");
        warning.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(warning);
    }

    public void renderLoggedInPage() {
        this.removeAll();
        this.setBackground(Color.CYAN);
    }

}
