import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class UpdateBillStatusApp extends JFrame implements ActionListener {

    Choice meterChoice, monthChoice;
    JLabel statusLabel;
    JButton checkStatusBtn, updateStatusBtn;

    public UpdateBillStatusApp() {
        setTitle("🧾 Update Bill Status");
        setSize(500, 350);
        setLocationRelativeTo(null); // Center screen
        setLayout(null);
        getContentPane().setBackground(new Color(245, 250, 255));

        JLabel heading = new JLabel("Update Payment Status");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 20));
        heading.setBounds(120, 20, 300, 30);
        add(heading);

        JLabel meterLabel = new JLabel("Meter No:");
        meterLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        meterLabel.setBounds(60, 80, 100, 20);
        add(meterLabel);

        meterChoice = new Choice();
        meterChoice.setBounds(160, 80, 200, 25);
        add(meterChoice);

        JLabel monthLabel = new JLabel("Month:");
        monthLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        monthLabel.setBounds(60, 120, 100, 20);
        add(monthLabel);

        monthChoice = new Choice();
        monthChoice.setBounds(160, 120, 200, 25);
        String[] months = {
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        };
        for (String m : months) monthChoice.add(m);
        add(monthChoice);

        checkStatusBtn = new JButton("🔍 Check Status");
        checkStatusBtn.setBounds(60, 170, 150, 30);
        checkStatusBtn.setBackground(new Color(0, 123, 255));
        checkStatusBtn.setForeground(Color.WHITE);
        checkStatusBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        checkStatusBtn.addActionListener(this);
        add(checkStatusBtn);

        updateStatusBtn = new JButton("✔️ Mark as Paid");
        updateStatusBtn.setBounds(220, 170, 150, 30);
        updateStatusBtn.setBackground(new Color(40, 167, 69));
        updateStatusBtn.setForeground(Color.WHITE);
        updateStatusBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        updateStatusBtn.addActionListener(this);
        add(updateStatusBtn);

        statusLabel = new JLabel("Status: ");
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        statusLabel.setBounds(60, 220, 350, 30);
        add(statusLabel);

        loadMeterNumbers();
        setVisible(true);
    }

    private void loadMeterNumbers() {
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("SELECT meter_no FROM customer");
            while (rs.next()) {
                meterChoice.add(rs.getString("meter_no"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent ae) {
        String meter = meterChoice.getSelectedItem();
        String month = monthChoice.getSelectedItem();
        Conn c = new Conn();

        if (ae.getSource() == checkStatusBtn) {
            try {
                ResultSet rs = c.s.executeQuery(
                        "SELECT status FROM bill WHERE meter_no = '" + meter + "' AND month = '" + month + "'"
                );
                if (rs.next()) {
                    statusLabel.setText("Status: " + rs.getString("status"));
                } else {
                    statusLabel.setText("Status: ❌ No bill found.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (ae.getSource() == updateStatusBtn) {
            try {
                int updated = c.s.executeUpdate(
                        "UPDATE bill SET status = 'Paid' WHERE meter_no = '" + meter + "' AND month = '" + month + "'"
                );
                if (updated > 0) {
                    JOptionPane.showMessageDialog(this, "✅ Bill marked as Paid.");
                    statusLabel.setText("Status: Paid");
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Bill not found.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new UpdateBillStatusApp();
    }
}
