package src.gui;
import javax.swing.*;


import src.database.EasyDatabase;
import src.database.User;
import src.database.User.Role;

import src.database.Encryption;
import java.security.NoSuchAlgorithmException;

import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

public class MainPanel extends JPanel {

    // instance variables

    final int column = 20;

    //All characters invalid for email.
    final String[] invalidChars = {"!","*", " ","_","-","'","+","<",">","(",")","[","]","{","}","&","="};

    EasyDatabase db;

    protected User user;

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

    //More JPanels
    protected HomePage customerHome;
    protected UpdateAccountDetails updateAccount;
    protected ManagerPage ManagerPage;
    protected StaffPage staffPage;
    protected StaffOrder staffOrder;
    protected AfterLogin afterLogin;
    protected CustomerOrder customerOrder;
    protected PastOrders pastOrders;

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

        //All external pages.
        customerHome = new HomePage();
        afterLogin = new AfterLogin();
        updateAccount = new UpdateAccountDetails();
        customerOrder = new CustomerOrder();
        staffPage = new StaffPage();
        staffOrder = new StaffOrder();
        ManagerPage = new ManagerPage();
        pastOrders = new PastOrders();

        // Adding all cards to Panel
        this.add(new JPanel(),"Splash");
        this.add(registerContainer, "Register");
        this.add(register2Container, "Register2");
        this.add(loginContainer,"Login");
        this.add(customerHome, "HomePage");
        this.add(customerOrder, "CustomerOrder");
        this.add(updateAccount, "UpdateAccount");
        this.add(staffPage,"UpdateStock");
        this.add(staffOrder,"UpdateOrders");
        this.add(ManagerPage, "ManagerPage");
        this.add(afterLogin, "AfterLogin");

        addListeners(this);
    }    

    public void addListeners(MainPanel p) {
        CardLayout c1 = (CardLayout)(this.getLayout());

    	registerContinueButton.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
                String accountCreation = createAccountA();
                if(accountCreation == "") c1.show(p,"Register2");
                else registerIssues.setText(accountCreation);
    		}
        });

        //Submits registration attempt, and if valid, will add user to database, or display more errors to the user.
        registerSubmitButton.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
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
                    User createdUser = new User(1, email, Role.Customer, name, surname, house, street, city, postcode);
                    addUserToDatabase(createdUser, password);
                    c1.show(p,"Splash");
                }
                else registerIssues2.setText(accountCreation);
    		}
        });

        // Logs the user in (First thread), checking if the user can log in, otherwise displaying errors to the user.
        loginSubmitButton.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
                String loginResult = login();
                if(loginResult != "") loginIssues.setText(loginResult);
                // Details are removed from the JPanel after login is successful.
                if(user != null) {
                    loginEmailField.setText("");
                    loginPasswordField.setText("");
                    if(user.getRole() == Role.Customer) {
                        customerHome.setUser(user);
                        updateAccount.setUser(user);
                        customerOrder.setUser(user);
                        updateAccount.renderLoggedInPage();
                        customerOrder.renderLoggedInPage();
                        //customerHome.rednerLoggedInPage(); ??

                    }
                    c1.show(p, "AfterLogin");
                    if(user.getRole() == Role.Manager) ManagerPage.setUser(user); // Sets the user to manager page only if they are a manager.
                }
    		}
        });
    }

    // First account creation page - checks details are correct.
    String createAccountA() {
        String email, password, confirmation;
        email = registerEmailField.getText();
        password = registerPasswordField.getText();
        confirmation = registerConfirmField.getText();
        if(!isEmailValid(email)) return "Invalid email address, please revise";
        for (String i : invalidChars)
            if(email.contains(i) || password.contains(i)) {
                return "Characters used were invalid.";
            }
        if(email.equals("")) return "Please enter an email.";
        if(password.equals("")) return "Please enter a password.";
        if(!password.equals(confirmation)) return "Passwords don't match";
        
        return "";
    }

    // Second verse, same as the first
    String createAccountB(String name, String surname, String street, String city, String postcode, int house) {
        if(name.equals("") || surname.equals("") || street.equals("") || city.equals("") || postcode.equals(""))
            return "Please check fields: One or more fields have been left blank.";
        for(String i : invalidChars) {
            if(name.contains(i) || surname.contains(i))
                return "Please check fields: Invalid characters used in first and/or last names";
        }
        if(!isPostcodeValid(postcode)) return "Postcode is invalid.";
        return "";
    }

    //Postcode validation - used in account creation
    Boolean isPostcodeValid(String x) {
        if (x.length() < 5 || x.length() > 7) return false;
        return true;
    }
    //Email validation - also used in account creation
    Boolean isEmailValid(String x) {
        if(!x.contains("@")) return false;
        if(!x.contains(".")) return false;
        boolean flag = false;
        for(int i=x.indexOf("@");i<x.length();i++) {
            if(x.charAt(i) == '.') {
                flag = true;
            }
            if(x.charAt(i)=='.'&& i>=x.length()-1) return false;
        }
        if(flag) return true;
        else return false;
    }

    //Logs the user in if details are correct - otherwise, returns specfic error.
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
                String selectSQL = "SELECT COUNT(id) AS total_rows FROM User WHERE email = ?";
                PreparedStatement preparedStatement = db.getConnection().prepareStatement(selectSQL);
                preparedStatement.setString(1, email);
                ResultSet rs = preparedStatement.executeQuery();
                while(rs.next()) {
                    if(Integer.parseInt(rs.getString("total_rows")) < 1) return "Invalid email";
                    else if(Integer.parseInt(rs.getString("total_rows")) > 1) return "what";
                }

                // Checks for password
                User tempUser = new User(email);
                String hashedPassword = tempUser.getHashedPassword();
                String salt = tempUser.getSalt();
                String generatedHashPassword = Encryption.generateHash(password, salt);
                System.out.println(hashedPassword + " " + generatedHashPassword);
                
                if(!hashedPassword.equals(generatedHashPassword)) return "Invalid password";

                user = tempUser;
                
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


    public void addUserToDatabase(User addUser, String password) {
        db = new EasyDatabase();
        String hashedPassword;
        String salt = Encryption.generateSalt();

        try {
            hashedPassword = Encryption.generateHash(password, salt);

            String selectSQL = "INSERT INTO User (email, hashed_password, salt, role, firstName, lastName, houseNumber, roadName, city, postCode) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = db.getConnection().prepareStatement(selectSQL);
            preparedStatement.setString(1, addUser.getEmail());
            preparedStatement.setString(2, hashedPassword);
            preparedStatement.setString(3, salt);
            preparedStatement.setString(4, "Customer");
            preparedStatement.setString(5, addUser.getFirstName());
            preparedStatement.setString(6, addUser.getLastName());
            preparedStatement.setInt(7, addUser.getHouseNumber());
            preparedStatement.setString(8, addUser.getRoadName());
            preparedStatement.setString(9, addUser.getCity());
            preparedStatement.setString(10, addUser.getPostCode());

            preparedStatement.executeUpdate();
            preparedStatement.close();
            db.close();
        } catch (NoSuchAlgorithmException | SQLException e) {
            e.printStackTrace();
        }
    }
}

