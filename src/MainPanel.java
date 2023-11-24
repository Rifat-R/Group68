package src;
import javax.swing.*;

import src.User.Role;

import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

public class MainPanel extends JPanel {
    final int column = 20;
    final String[] invalidChars = {"!","*", " "};

    EasyDatabase db;

    private User user;

    // Register JItems

    protected JButton registerContinueButton = new JButton("Continue");
    protected JTextField registerUsernameField = new JTextField(column);
    protected JTextField registerEmailField = new JTextField(column);
    protected JPasswordField registerPasswordField = new JPasswordField(column);
    protected JPasswordField registerConfirmField = new JPasswordField(column);
    protected JLabel registerIssues = new JLabel();

    // Register2 JItems

    protected JButton registerSubmitButton = new JButton("Submit");
    protected JTextField registerFirstNameField = new JTextField(column);
    protected JTextField registerLastNameField = new JTextField(column);
    protected JTextField registerAddress1 = new JTextField(column);
    protected JTextField registerAddress2 = new JTextField(column);
    protected JTextField registerAddress3 = new JTextField(column);
    protected JTextField registerPostcode = new JTextField(column);
    protected JLabel registerIssues2 = new JLabel();

    // Login JItems
    protected JButton loginSubmitButton = new JButton("Submit");
    protected JTextField loginEmailField = new JTextField(column);
    protected JPasswordField loginPasswordField = new JPasswordField(column);
    protected JLabel loginIssues = new JLabel();

    // Constructor
    public MainPanel(){

        this.setLayout(new CardLayout());

        registerIssues.setForeground(Color.RED);
        registerIssues2.setForeground(Color.RED);
        loginIssues.setForeground(Color.RED);

        JLabel emptyLine = new JLabel("");
        emptyLine.setPreferredSize(new Dimension(3000,0));

        JPanel registerPanel = new JPanel();
        registerPanel.setLayout(new BoxLayout(registerPanel,BoxLayout.Y_AXIS));
        registerPanel.add(new JLabel("Email: "));
        registerPanel.add(registerEmailField);
        registerPanel.add(new JLabel("Password: "));
        registerPanel.add(registerPasswordField);
        registerPanel.add(new JLabel("Confirm Password: "));
        registerPanel.add(registerConfirmField);
        registerPanel.add(registerContinueButton);
        registerPanel.add(registerIssues);

        JPanel register2Panel = new JPanel();
        register2Panel.setLayout(new BoxLayout(register2Panel, BoxLayout.Y_AXIS));
        register2Panel.add(new JLabel("First name: "));
        register2Panel.add(registerFirstNameField);
        register2Panel.add(new JLabel("Surname: "));
        register2Panel.add(registerLastNameField);
        register2Panel.add(new JLabel("House no.: "));
        register2Panel.add(registerAddress1);
        register2Panel.add(new JLabel("Street name: "));
        register2Panel.add(registerAddress2);
        register2Panel.add(new JLabel("City: "));
        register2Panel.add(registerAddress3);
        register2Panel.add(new JLabel("Postcode: "));
        register2Panel.add(registerPostcode);
        register2Panel.add(registerSubmitButton);
        register2Panel.add(registerIssues2);

        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
        loginPanel.add(new JLabel("Email: "));
        loginPanel.add(loginEmailField);
        loginPanel.add(new JLabel("Password: "));
        loginPanel.add(loginPasswordField);
        loginPanel.add(loginSubmitButton);
        loginPanel.add(loginIssues);
        
        JPanel registerContainer = new JPanel();
        registerContainer.add(registerPanel);
        JPanel register2Container = new JPanel();
        register2Container.add(register2Panel);
        JPanel loginContainer = new JPanel();
        loginContainer.add(loginPanel);

        JPanel customerHome = new HomePage();

        this.add(new JPanel(),"Splash");
        this.add(registerContainer, "Register");
        this.add(register2Container, "Register2");
        this.add(loginContainer,"Login");
        this.add(customerHome, "HomePage");

        addListeners(this);
    }    

    public void addListeners(MainPanel p) {
        CardLayout c1 = (CardLayout)(this.getLayout());
    	registerContinueButton.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			System.out.println("Plz work register2");
                String accountCreation = createAccountA();
                if(accountCreation == "") c1.show(p,"Register2");
                else registerIssues.setText(accountCreation);
    		}
        });
        registerSubmitButton.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			System.out.println("Plz submit data!");
                String name, surname, street, city, postcode;
                int house = 0;
                String email = registerEmailField.getText();
                String password = registerPasswordField.getText();
                name = registerFirstNameField.getText();
                surname = registerLastNameField.getText();
                street = registerAddress2.getText();
                city = registerAddress3.getText();
                postcode = registerPostcode.getText();
                String accountCreation;
                try {
                    house = Integer.parseInt(registerAddress1.getText());
                    accountCreation = createAccountB(name, surname, street, city, postcode, house);
                } catch (Throwable t) {
                    accountCreation = "Please enter numbers only into this field: House number";
                }
                if(accountCreation == "") { 
                    User createdUser = new User(1, email, Role.Customer, house, street, city, postcode);
                    addUserToDatabase(createdUser, password);
                    c1.show(p,"Splash");
                }
                else registerIssues2.setText(accountCreation);
    		}
        });
        loginSubmitButton.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			System.out.println("Plz login using data!");
                String loginResult = login();
                if(loginResult == "") c1.show(p,"Splash");
                else loginIssues.setText(loginResult);
                if(user != null) c1.show(p, "HomePage");
    		}
        });
    }
    int getNextID()
    {
        db = new EasyDatabase();
        String selectSQL = "SELECT COUNT(userID) FROM UserTable";
        db.executeQuery(selectSQL);
        try {
            while(db.resultSet.next()) {
                return Integer.parseInt(db.resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {            
            db.close();
        }
        return -1;

    }
    String createAccountA() {
        String email, password, confirmation;
        email = registerEmailField.getText();
        password = registerPasswordField.getText();
        confirmation = registerConfirmField.getText();
        System.out.println(password+confirmation);
        for (String i : invalidChars)
            if(email.contains(i) || password.contains(i)) {
                return "Characters used were invalid.";
            }
        if(email == "") return "Please enter an email.";
        if(password == "") return "Please enter a password.";
        if(!password.equals(confirmation)) return "Passwords don't match";
        
        return "";
    }
    String createAccountB(String name, String surname, String street, String city, String postcode, int house) {
        
        for(String i : invalidChars) {
            if(name.contains(i) || surname.contains(i))
                return "Please check fields: Invalid characters used in first and/or last names";
        }
        if(!isPostcodeValid(postcode)) return "Postcode is invalid.";
        return "";
    }
    Boolean isPostcodeValid(String x) {
        if (x.length() < 5 || x.length() > 7) return false;
        return true;
    }


    String login() {
        String email, password;
        email = loginEmailField.getText();
        password = loginPasswordField.getText();
        for (String i : invalidChars)
            if(email.contains(i) || password.contains(i)) {
                return "Characters used were invalid.";
            }
        if(email == "") return "Please enter an email.";
        if(password == "") return "Please enter a password.";

        db = new EasyDatabase();
        try {
            try {
                String selectSQL = "SELECT COUNT(userID) FROM UserTable WHERE userEmail = '" + email 
                                + "'";
                db.executeQuery(selectSQL);
                while(db.resultSet.next()) {
                    if(Integer.parseInt(db.resultSet.getString(1)) < 1) return "Invalid email";
                    else if(Integer.parseInt(db.resultSet.getString(1)) > 1) return "what";
                }

                selectSQL = "SELECT COUNT(userID) FROM UserTable WHERE userEmail = '" + email 
                                + "' AND userPassword = '" + password + "'";
                db.executeQuery(selectSQL);
                while(db.resultSet.next()) {
                    if(Integer.parseInt(db.resultSet.getString(1)) < 1) return "Invalid password";
                }

                selectSQL = "SELECT userID FROM UserTable WHERE userEmail = '" + email
                                + "' AND userPassword = '" + password + "'";
                db.executeQuery(selectSQL);
                while(db.resultSet.next()) {
                    user = new User(Integer.parseInt(db.resultSet.getString("userID")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {            
            db.close();
        }
        return "";
    }

    public void addUserToDatabase(User addUser,String password)
    {
        db = new EasyDatabase();
        String selectSQL = "INSERT INTO UserTable (userEmail, userPassword, userRole, houseNumber, roadName, city, postCode) VALUES ('"
                            + addUser.getEmail()+"','"+ password+"','Customer','"+ addUser.getHouseNumber()
                            +"','"+addUser.getRoadName() +"','"+addUser.getCity()+"','"+addUser.getPostCode()+"')";
        db.executeUpdate(selectSQL);
        db.close();
        
    }
}

