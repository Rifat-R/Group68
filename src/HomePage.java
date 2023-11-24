package src;
import javax.swing.*;

import src.User.Role;

import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

public class HomePage extends JPanel {

    EasyDatabase db;

    // Only for registered users
    public HomePage() {

        this.setBackground(Color.black);
        /*
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        String[][] newArray = new String[1][3];
        newArray[0][0] = "77";
        newArray[0][1] = "Meadow Road";
        newArray[0][2] = "Leeds";

        String[] columnNames = new String [3];
        columnNames[0] = "Number";
        columnNames[1] = "Street";
        columnNames[2] = "City";

        
        JTable table = new JTable(newArray, columnNames);

        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.weighty = 0;
        this.add(scrollPane, c);
        */
    }

    /*
    ResultSet getItems() {
        db = new EasyDatabase();
        try {
            try {
                String selectSQL = "SELECT * FROM ProducTable";
                db.executeQuery(selectSQL);
                while(db.resultSet.next()) {

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {            
            db.close();
        }
    }
    }
    */

}
