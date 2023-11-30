package src.gui;
import javax.swing.*;

import src.database.User;
import src.database.User.Role;

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

    JMenu staff = new JMenu("Staff options");
    JMenuItem updateProduct = new JMenuItem("Update product details");
    JMenuItem changeStaff = new JMenuItem("Update staff (Manager only)");

    User userLoggedIn;

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
                c1.show(panel,"Register");
    		}
        });
        login.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
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
        updateProduct.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			System.out.println("Plz work products!");
          c1.show(panel,"StaffPage");
    		}
        });
        changeStaff.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			System.out.println("Plz work staff!");
          c1.show(panel,"ManagerPage");
    		}
        });
        panel.loginSubmitButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            Thread t = new Thread(new Runnable() {

              @Override
              public void run() {
                  try {
                      Thread.sleep(1000);
                      userLoggedIn = panel.user;
                      if(userLoggedIn != null) {
                        panel.customerHome.setUser(userLoggedIn);
                        panel.updateAccount.setUser(userLoggedIn);
                        panel.ManagerPage.setUser(userLoggedIn);
                        if(userLoggedIn.getRole() != Role.Customer) {
                          staff.add(updateProduct);
                          menubar.add(staff);
                          if(userLoggedIn.getRole() == Role.Manager) staff.add(changeStaff);              
                        }
                      }
                  } catch (InterruptedException e1) {
                      e1.printStackTrace();
                  }                  
              }              
            });
          t.start(); 
            
          }
        });
    }
}
