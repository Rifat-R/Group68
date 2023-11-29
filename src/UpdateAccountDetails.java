package src;
import javax.swing.*;

import src.HomePage.buttonListener;
import src.User.Role;

import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;


public class UpdateAccountDetails extends JPanel {
    
    private User user;
    private JTextField email;
    private JTextField firstName;
    private JTextField surname;
    private JTextField houseNumber;
    private JTextField roadName;
    private JTextField city;
    private JTextField postcode;

    EasyDatabase db;

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
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        //change their personal, email and address details, or to change their bank details
        //Textfield with filled on detail -> button to change single element
        //Same with bank details -> if not given then allow user to create them
        //When placing order will have to check if bank details already exsists

        String initialFirstName = user.getFirstName();
        String initialSurname = user.getLastName();
        String initialEmail = user.getEmail();
        Integer initialHouseNumber = user.getHouseNumber();
        String initialRoadName = user.getRoadName();
        String initialCity = user.getCity();
        String initialPostcode = user.getPostCode();




        email = new JTextField(initialEmail);
        c.anchor = GridBagConstraints.PAGE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.weightx = 0.4;
        c.weighty = 0.4;
        c.insets = new Insets(50,50,0,0);
        this.add(email, c);


        firstName = new JTextField(initialFirstName);
        c.anchor = GridBagConstraints.PAGE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.weightx = 0.4;
        c.weighty = 0.4;
        c.insets = new Insets(0,50,0,0);
        this.add(firstName, c);

        surname = new JTextField(initialSurname);
        c.anchor = GridBagConstraints.PAGE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 1;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.weightx = 0.4;
        c.weighty = 0.4;
        c.insets = new Insets(0,0,0,0);
        this.add(surname, c);

        JButton updateDetails = new JButton("Update your personal details");
        updateDetails.addActionListener(new updateDetailsListener());
        c.anchor = GridBagConstraints.PAGE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 1;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.weightx = 0.4;
        c.weighty = 0.4;
        c.insets = new Insets(0,0,0,50);
        this.add(updateDetails, c);

        houseNumber = new JTextField(String.valueOf(initialHouseNumber));
        c.anchor = GridBagConstraints.PAGE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.weightx = 0.2;
        c.weighty = 0.2;
        c.insets = new Insets(0,50,0,0);
        this.add(houseNumber, c);

        roadName = new JTextField(initialRoadName);
        c.anchor = GridBagConstraints.PAGE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 2;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.weightx = 0.2;
        c.weighty = 0.2;
        c.insets = new Insets(0,0,0,50);
        this.add(roadName, c);

        city = new JTextField(initialCity);
        c.anchor = GridBagConstraints.PAGE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 3;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.weightx = 0.2;
        c.weighty = 0.2;
        c.insets = new Insets(0, 50,0,0);
        this.add(city, c);

        postcode = new JTextField(initialPostcode);
        c.anchor = GridBagConstraints.PAGE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 3;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.weightx = 0.2;
        c.weighty = 0.2;
        c.insets = new Insets(0,0,0,0);
        this.add(postcode, c);

        JButton updateAddress = new JButton("Update your Address");
        updateAddress.addActionListener(new updateAddressListener());
        c.anchor = GridBagConstraints.PAGE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 3;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.weightx = 0.2;
        c.weighty = 0.2;
        c.insets = new Insets(0,0,50,50);
        this.add(updateAddress, c);


    }

    
    class updateDetailsListener implements ActionListener {
        public void actionPerformed(ActionEvent event){
            try {
                db = new EasyDatabase();
                ResultSet rs = db.GetUserDetails(user.getID());
                rs.next();
                String previousEmail = rs.getString(2);
                String previousFirstName = rs.getString(6);
                String previousSurname = rs.getString(7);

                String newEmail = email.getText();
                String newFirstName = firstName.getText();
                String newSurname = surname.getText();

                if ((previousEmail.equals(newEmail)) 
                && (previousFirstName.equals(newFirstName)) 
                && (previousSurname.equals(newSurname))) {
                    //failed
                    return;
                } else {
                    try {
                        db.updateUserDetails(user.getID(), newEmail, newFirstName, newSurname);
                        db.close();
                    } catch (SQLException e) {
                        //failed
                        return;
                    }
                }
            } catch (SQLException e) {
                //failed
                return;
            }
        }
    }

    class updateAddressListener implements ActionListener {
        public void actionPerformed(ActionEvent event){
            try {
                db = new EasyDatabase();
                ResultSet rs = db.GetUserDetails(user.getID());
                rs.next();

                String previousHouseNumber = rs.getString(5);
                String previousRoadName = rs.getString(8);
                String previousCity = rs.getString(9);
                String previousPostcode = rs.getString(10);

                String newHouseNumber = houseNumber.getText(); //String not int
                String newRoadName = roadName.getText();
                String newCity = city.getText();
                String newPostcode = postcode.getText();

                if ((previousHouseNumber.equals(newHouseNumber)) 
                && (previousRoadName.equals(newRoadName)) 
                && (previousCity.equals(newCity))
                && (previousPostcode.equals(newPostcode))) {
                    //failed
                    return;
                } else {
                    try {
                        db.updateUserAddress(user.getID(), newHouseNumber, newRoadName, newCity, newPostcode);
                        db.close();
                    } catch (SQLException e) {
                        //failed
                        return;
                    }
                }
            } catch (SQLException e) {
                //failed
                return;
            }
        }
    }
}
