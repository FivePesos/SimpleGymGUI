import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class RegisterForm {

    public static void main(String[] args) {
        // Create JFrame
        JFrame frame = new JFrame("Register");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // Load custom font
        Font montserrat;
        try {
            montserrat = Font.createFont(Font.TRUETYPE_FONT, new File("Montserrat.ttf")).deriveFont(18f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(montserrat);
        } catch (FontFormatException | IOException e) {
            montserrat = new Font("SansSerif", Font.PLAIN, 18); // Fallback font
        }

        // Title Label
        JLabel titleLabel = new JLabel("REGISTER");
        titleLabel.setFont(montserrat.deriveFont(Font.BOLD, 24f));
        titleLabel.setBounds(130, 20, 200, 30);
        frame.add(titleLabel);

        // Name Label
        JLabel nameLabel = new JLabel("NAME");
        nameLabel.setFont(montserrat);
        nameLabel.setBounds(50, 80, 100, 20);
        frame.add(nameLabel);

        // Name TextField
        JTextField nameField = new JTextField("FULL NAME");
        nameField.setFont(montserrat.deriveFont(14f));
        nameField.setBounds(150, 80, 200, 30);
        frame.add(nameField);

        // Username Label
        JLabel usernameLabel = new JLabel("USERNAME");
        usernameLabel.setFont(montserrat);
        usernameLabel.setBounds(50, 130, 100, 20);
        frame.add(usernameLabel);

        // Username TextField
        JTextField usernameField = new JTextField("SET USERNAME");
        usernameField.setFont(montserrat.deriveFont(14f));
        usernameField.setBounds(150, 130, 200, 30);
        frame.add(usernameField);

        // Password Label
        JLabel passwordLabel = new JLabel("PASSWORD");
        passwordLabel.setFont(montserrat);
        passwordLabel.setBounds(50, 180, 100, 20);
        frame.add(passwordLabel);

        // Password Field
        JPasswordField passwordField = new JPasswordField("SET PASSWORD");
        passwordField.setFont(montserrat.deriveFont(14f));
        passwordField.setBounds(150, 180, 200, 30);
        frame.add(passwordField);

        // Register Button
        JButton registerButton = new JButton("REGISTER");
        registerButton.setFont(montserrat);
        registerButton.setBounds(80, 250, 120, 40);
        frame.add(registerButton);

        // Cancel Button
        JButton cancelButton = new JButton("CANCEL");
        cancelButton.setFont(montserrat);
        cancelButton.setBounds(220, 250, 120, 40);
        frame.add(cancelButton);

        // Button Actions
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Registered Successfully!");
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        // Make frame visible
        frame.setVisible(true);
    }
}
