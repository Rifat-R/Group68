package src;
// import java.sql.*;
import javax.swing.*;


public class App {
    public static void main(String[] args) throws Exception {
        EasyDatabase db = new EasyDatabase();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                final SwingWindow window = new SwingWindow("Database");
                window.setVisible(true);
            }
        });
        db.close();
    }
}
