package src;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

public class SwingWindow extends JFrame {

    MainPanel panel;    
        
    JMenuBar menubar = new JMenuBar();
    JMenu menu = new JMenu("Account");
    JMenuItem register = new JMenuItem("Register");
    JMenuItem login = new JMenuItem("Login");
    JMenuItem order = new JMenuItem("Order");
    JMenuItem updateAccount = new JMenuItem("Update account details");

    public SwingWindow(String text) {
        super(text);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        setSize(screenSize.width/2, screenSize.height/2);
        setLocation(screenSize.width/4, screenSize.height/4);

        menu.add(register);
        menu.add(login);
        menu.add(order);
        menu.add(updateAccount);
        menubar.add(menu);
        this.setJMenuBar(menubar);
        panel = new MainPanel();


        /* 
         * Notes - Here is where we can define JButtons, JTextFields, JComboBoxes, JCheckBoxes, JPasswordFields, JRadioButtons, JMenu stuff...
         * I've set up a grid layout for now but idk what we're gonna use so we can discuss that in our next meeting
         * I'll also upgrade the JPanel when necessary.
         */

        this.add(panel);
        this.addListeners();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
    public void addListeners() {
        CardLayout c1 = (CardLayout)(panel.getLayout());
    	  register.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			System.out.println("Plz work register");
                c1.show(panel,"Register");
    		}
        });
        login.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			System.out.println("Plz work login!");
                c1.show(panel,"Login");
    		}
        });
        order.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			System.out.println("Plz work order!");
          c1.show(panel,"CustomerOrder");
    		}
        });
        updateAccount.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			System.out.println("Plz work order!");
          c1.show(panel,"UpdateAccount");
    		}
        });
    }
}
