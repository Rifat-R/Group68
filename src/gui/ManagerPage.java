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


public class ManagerPage extends JPanel {

    EasyDatabase db;
    JTable table;
    User user;

    ArrayList<JPanel> individualUser = new ArrayList<JPanel>();

    public ManagerPage() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        db = new EasyDatabase();
        String selectSQL = "SELECT * FROM User";
        db.executeQuery(selectSQL);
        try {
            while(db.resultSet.next()) {
                String name = db.resultSet.getString("firstName");
                String role = db.resultSet.getString("role");
                if(role.equals("Manager")) continue;

                JLabel tempLabel1 = new JLabel("User: ");
                JLabel nameLabel = new JLabel(name);
                JLabel tempLabel2 = new JLabel(": ");
                JLabel roleLabel = new JLabel(role);
                JButton changeRoleButton = new JButton("Change Role");

                JPanel temp = new JPanel();
                temp.setLayout(new FlowLayout());
                temp.add(tempLabel1);
                temp.add(nameLabel);
                temp.add(tempLabel2);
                temp.add(roleLabel);
                temp.add(changeRoleButton);

                individualUser.add(temp);                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {            
            db.close();
        }
        for(JPanel i : individualUser) {
            this.add(i);
        }
        this.addListeners();
    }
    public void addListeners()
    {
        for(JPanel iU : individualUser) {
            JButton b = (JButton) iU.getComponent(4);
            JLabel role = (JLabel) iU.getComponent(3);
            String text;
            if(role.getText().equals("Customer")) {
                text = "Staff";
            }
            else {
                text = "Customer";
            }
            JLabel n = (JLabel) iU.getComponent(1);
            String name = n.getText();
            b.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Please work!");
                    db = new EasyDatabase();
                    String selectSQL = "UPDATE User SET role = ? WHERE email = ?";
                    try {
                        PreparedStatement ps = db.getConnection().prepareStatement(selectSQL);
                        ps.setString(1, text);
                        ps.setString(2, name);
                        ps.executeUpdate();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }

                    db.close();
                    if(role.getText().equals("Customer")) {
                        role.setText("Staff");
                    }
                    else {
                        role.setText("Customer");
                    }                    
                }
            });
        }
    }
    public void setUser(User user)
    {
        this.user = user;
    }
}
