package src;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

public class RegisterLoginPanels extends JPanel {

    public RegisterLoginPanels() {
        setLayout(new GridLayout(5, 4));
        addButton("Submit");
        
    }
    
    private void addButton(String label) {
        JButton button = new JButton(label);
        button.setFont(new Font("Monospaced", Font.BOLD, 22));
        button.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			System.out.println("Plz work!");
    		}
        });
        this.add(button);
    }
    public void addListeners() {
        
    }
}