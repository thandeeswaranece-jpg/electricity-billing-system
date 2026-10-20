import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainMenu extends JFrame implements ActionListener {

    JButton addCustomer, generateBill, viewBills, printBill, updateStatus, viewCustomers, usageChart, upiQr;

    public MainMenu() {
        setTitle("⚡ Electricity Billing Dashboard");
        setSize(600, 500);
        setLocationRelativeTo(null); // Center on screen
        setLayout(new GridLayout(4, 2, 20, 20));
        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Menu Bar
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem logout = new JMenuItem("Logout");
        logout.addActionListener(e -> {
            dispose(); // close dashboard
            new LoginApp(); // back to login screen
        });
        file.add(logout);
        menuBar.add(file);
        setJMenuBar(menuBar);

        // Buttons
        addCustomer = createButton("➕ Add Customer");
        generateBill = createButton("🧾 Generate Bill");
        viewBills = createButton("📋 View Bills");
        printBill = createButton("🖨️ Print Bill");
        updateStatus = createButton("✅ Update Bill Status");
        viewCustomers = createButton("👤 View Customers");
        usageChart = createButton("📊 Usage Chart");
        upiQr = createButton("📱 Show UPI QR");

        add(addCustomer);
        add(generateBill);
        add(viewBills);
        add(printBill);
        add(updateStatus);
        add(viewCustomers);
        add(usageChart);
        add(upiQr);

        setVisible(true);
    }

    private JButton createButton(String title) {
        JButton btn = new JButton(title);
        btn.setBackground(new Color(0, 120, 215));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.addActionListener(this);
        return btn;
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == addCustomer) {
            new AddCustomerApp();
        } else if (ae.getSource() == generateBill) {
            new GenerateBillApp();
        } else if (ae.getSource() == viewBills) {
            new ViewBillsApp();
        } else if (ae.getSource() == printBill) {
            new PrintBillApp();
        } else if (ae.getSource() == updateStatus) {
            new UpdateBillStatusApp();
        } else if (ae.getSource() == viewCustomers) {
            new ViewCustomersApp();
        } else if (ae.getSource() == usageChart) {
            new UsageChartApp();
        } else if (ae.getSource() == upiQr) {
            new UPIBillQR();
        }
    }

    public static void main(String[] args) {
        new MainMenu(); // Entry point if testing this class directly
    }
}
