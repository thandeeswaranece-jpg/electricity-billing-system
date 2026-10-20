import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginApp extends JFrame implements ActionListener {

    JTextField usernameField;
    JPasswordField passwordField;
    JButton loginBtn;

    public LoginApp() {
        setTitle("⚡ Electricity Billing Login");
        setSize(400, 300);
        setLocationRelativeTo(null); // Center window
        setLayout(null);
        getContentPane().setBackground(new Color(240, 248, 255));

        JLabel heading = new JLabel("Admin Login");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 22));
        heading.setBounds(130, 30, 200, 30);
        add(heading);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        userLabel.setBounds(50, 80, 100, 20);
        add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(150, 80, 180, 25);
        add(usernameField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passLabel.setBounds(50, 120, 100, 20);
        add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(150, 120, 180, 25);
        add(passwordField);

        loginBtn = new JButton("🔐 Login");
        loginBtn.setBounds(150, 170, 100, 35);
        loginBtn.setBackground(new Color(0, 123, 255));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginBtn.addActionListener(this);
        add(loginBtn);

        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent ae) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.equals("admin") && password.equals("admin")) {
            JOptionPane.showMessageDialog(this, "✅ Login Successful");
            setVisible(false);
            new MainMenu(); // Make sure MainMenu.java exists
        } else {
            JOptionPane.showMessageDialog(this, "❌ Invalid Username or Password");
        }
    }

    public static void main(String[] args) {
        new LoginApp();
    }
}
