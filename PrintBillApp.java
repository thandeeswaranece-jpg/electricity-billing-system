import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class PrintBillApp extends JFrame implements ActionListener {

    Choice meterNumberChoice, monthChoice;
    JTextArea billArea;
    JButton printButton;

    public PrintBillApp() {
        setTitle("📄 Print Electricity Bill");
        setSize(700, 600);
        setLocationRelativeTo(null); // center screen
        setLayout(null);
        getContentPane().setBackground(new Color(245, 250, 255));

        JLabel heading = new JLabel("Electricity Bill Generator");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 22));
        heading.setBounds(200, 10, 350, 30);
        add(heading);

        JLabel lblMeter = new JLabel("Meter No:");
        lblMeter.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblMeter.setBounds(80, 60, 100, 20);
        add(lblMeter);

        meterNumberChoice = new Choice();
        meterNumberChoice.setBounds(180, 60, 180, 25);
        add(meterNumberChoice);

        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("SELECT meter_no FROM customer");
            while (rs.next()) {
                meterNumberChoice.add(rs.getString("meter_no"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JLabel lblMonth = new JLabel("Month:");
        lblMonth.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblMonth.setBounds(80, 100, 100, 20);
        add(lblMonth);

        monthChoice = new Choice();
        monthChoice.setBounds(180, 100, 180, 25);
        String[] months = { "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December" };
        for (String m : months) monthChoice.add(m);
        add(monthChoice);

        JButton generate = new JButton("🧾 Generate Bill");
        generate.setBounds(400, 100, 150, 25);
        generate.setBackground(new Color(0, 123, 255));
        generate.setForeground(Color.WHITE);
        generate.setFont(new Font("Segoe UI", Font.BOLD, 13));
        generate.addActionListener(e -> generateBill());
        add(generate);

        billArea = new JTextArea();
        billArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        billArea.setEditable(false);
        billArea.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scroll = new JScrollPane(billArea);
        scroll.setBounds(50, 150, 580, 320);
        add(scroll);

        printButton = new JButton("🖨️ Print / Save as PDF");
        printButton.setBounds(250, 490, 200, 35);
        printButton.setBackground(Color.DARK_GRAY);
        printButton.setForeground(Color.WHITE);
        printButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        printButton.addActionListener(this);
        add(printButton);

        setVisible(true);
    }

    private void generateBill() {
        String meter = meterNumberChoice.getSelectedItem();
        String month = monthChoice.getSelectedItem();

        try {
            Conn c = new Conn();
            billArea.setText("\n\t     ⚡ ELECTRICITY BILL\n");
            billArea.append("\t---------------------------------------------\n");

            ResultSet rs = c.s.executeQuery("SELECT * FROM customer WHERE meter_no = '" + meter + "'");
            if (rs.next()) {
                billArea.append("Customer Name : " + rs.getString("name") + "\n");
                billArea.append("Meter Number  : " + rs.getString("meter_no") + "\n");
                billArea.append("Address       : " + rs.getString("address") + "\n");
                billArea.append("City          : " + rs.getString("city") + "\n");
                billArea.append("State         : " + rs.getString("state") + "\n");
                billArea.append("Email         : " + rs.getString("email") + "\n");
                billArea.append("Phone         : " + rs.getString("phone") + "\n");
                billArea.append("\t---------------------------------------------\n");
            }

            rs = c.s.executeQuery("SELECT * FROM bill WHERE meter_no = '" + meter + "' AND month = '" + month + "'");
            if (rs.next()) {
                billArea.append("Month         : " + rs.getString("month") + "\n");
                billArea.append("Units Used    : " + rs.getString("units") + "\n");
                billArea.append("Total Amount  : ₹" + rs.getString("total_amount") + "\n");
                billArea.append("Status        : " + rs.getString("status") + "\n");
            } else {
                billArea.append("⚠️ No bill record found for this month.\n");
            }

            billArea.append("\t---------------------------------------------\n");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Error generating bill");
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            billArea.print(); // opens system print dialog
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Print Failed: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new PrintBillApp();
    }
}
