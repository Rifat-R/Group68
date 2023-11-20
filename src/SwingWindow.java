package src;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

public class SwingWindow extends JFrame {


    JPanel panel;

    public SwingWindow(String text) {
        super(text);
        panel = new JPanel();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        setSize(screenSize.width/2, screenSize.height/2);
        setLocation(screenSize.width/4, screenSize.height/4);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        JMenuBar menubar = new JMenuBar();
        JMenu menu = new JMenu("Login/Register");
        JMenuItem register = new JMenuItem("Register");
        JMenuItem login = new JMenuItem("Login");
        menu.add(register);
        menu.add(login);
        menubar.add(menu);
        this.setJMenuBar(menubar);

        addButton(panel, "Button1");

        this.add(panel, BorderLayout.CENTER);

        /* 
         * Notes - Here is where we can define JButtons, JTextFields, JComboBoxes, JCheckBoxes, JPasswordFields, JRadioButtons, JMenu stuff...
         * I've set up a grid layout for now but idk what we're gonna use so we can discuss that in our next meeting
         * I'll also upgrade the JPanel when necessary.
         */


        this.addListeners();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
    private void addButton(JPanel panel, String label) {
        JButton button = new JButton(label);
        button.setFont(new Font("Monospaced", Font.BOLD, 22));
        
        panel.add(button);
    }
    public void addListeners() {
        /*
    	register.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			System.out.println("Plz work register");
                panel.setVisible(false);
                registerPanel.setVisible(true);
    		}
        });
        login.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			System.out.println("Plz work login!");
    		}
        });*/
    }
}
