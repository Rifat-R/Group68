package src;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

public class MainPanel extends JPanel {
    final int column = 20;
    // Register JItems

    protected JButton registerContinueButton = new JButton("Continue");
    protected JTextField registerUsernameField = new JTextField(column);
    protected JTextField registerEmailField = new JTextField(column);
    protected JTextField registerPasswordField = new JTextField(column);
    protected JTextField registerConfirmField = new JTextField(column);

    // Register2 JItems

    protected JButton registerSubmitButton = new JButton("Submit");
    protected JTextField registerFirstNameField = new JTextField(column);
    protected JTextField registerLastNameField = new JTextField(column);
    protected JTextField registerAddress1 = new JTextField(column);
    protected JTextField registerAddress2 = new JTextField(column);
    protected JTextField registerAddress3 = new JTextField(column);
    protected JTextField registerPostcode = new JTextField(column);

    // Login JItems
    protected JButton loginSubmitButton = new JButton("Submit");
    protected JTextField loginUsernameField = new JTextField(column);
    protected JTextField loginPasswordField = new JTextField(column);

    // Constructor
    public MainPanel(){

        this.setLayout(new CardLayout());

        JLabel emptyLine = new JLabel("");
        emptyLine.setPreferredSize(new Dimension(3000,0));

        JPanel registerPanel = new JPanel();
        registerPanel.setLayout(new BoxLayout(registerPanel,BoxLayout.Y_AXIS));
        registerPanel.add(new JLabel("Username: "));
        registerPanel.add(registerUsernameField);
        registerPanel.add(new JLabel("Email: "));
        registerPanel.add(registerEmailField);
        registerPanel.add(new JLabel("Password: "));
        registerPanel.add(registerPasswordField);
        registerPanel.add(new JLabel("Confirm Password: "));
        registerPanel.add(registerConfirmField);
        registerPanel.add(registerContinueButton);

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

        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
        loginPanel.add(new JLabel("Username: "));
        loginPanel.add(loginUsernameField);
        loginPanel.add(new JLabel("Password: "));
        loginPanel.add(loginPasswordField);
        loginPanel.add(loginSubmitButton);
        
        JPanel registerContainer = new JPanel();
        registerContainer.add(registerPanel);
        JPanel register2Container = new JPanel();
        register2Container.add(register2Panel);
        JPanel loginContainer = new JPanel();
        loginContainer.add(loginPanel);

        this.add(new JPanel(),"Splash");
        this.add(registerContainer, "Register");
        this.add(register2Container, "Register2");
        this.add(loginContainer,"Login");

        addListeners(this);
    }    

    public void addListeners(MainPanel p) {
        CardLayout c1 = (CardLayout)(this.getLayout());
    	registerContinueButton.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			System.out.println("Plz work register2");
                c1.show(p,"Register2");
    		}
        });
    }
}
