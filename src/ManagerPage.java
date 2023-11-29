package src;
import javax.swing.*;

import src.User.Role;

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
        String selectSQL = "SELECT * FROM UserTable";
        db.executeQuery(selectSQL);
        try {
            while(db.resultSet.next()) {
                String name = db.resultSet.getString(2);
                String role = db.resultSet.getString(4);
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
            b.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Please work!");
                }
            });
        }
    }
    public void setUser(User user)
    {
        this.user = user;
    }
}
