import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class GenerateBillApp extends JFrame implements ActionListener {

    JTextField tfmeter, tfmonth, tfunits;
    JButton submit, payButton;
    double total = 0.0;
    String meter = "", month = "";

    GenerateBillApp() {
        setTitle("Generate Electricity Bill");
        setSize(400, 330);
        setLocation(500, 250);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        JLabel lblhead = new JLabel("Generate Bill");
        lblhead.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblhead.setBounds(130, 10, 200, 30);
        add(lblhead);

        JLabel lblmeter = new JLabel("Meter No:");
        lblmeter.setBounds(50, 60, 100, 20);
        add(lblmeter);

        tfmeter = new JTextField();
        tfmeter.setBounds(150, 60, 180, 20);
        add(tfmeter);

        JLabel lblmonth = new JLabel("Month:");
        lblmonth.setBounds(50, 100, 100, 20);
        add(lblmonth);

        tfmonth = new JTextField();
        tfmonth.setBounds(150, 100, 180, 20);
        add(tfmonth);

        JLabel lblunits = new JLabel("Units Used:");
        lblunits.setBounds(50, 140, 100, 20);
        add(lblunits);

        tfunits = new JTextField();
        tfunits.setBounds(150, 140, 180, 20);
        add(tfunits);

        submit = new JButton("Submit");
        submit.setBounds(150, 180, 100, 30);
        submit.addActionListener(this);
        add(submit);

        payButton = new JButton("Pay Now");
        payButton.setBounds(150, 220, 100, 30);
        payButton.setVisible(false);
        payButton.addActionListener(e -> {
            UPIBillQR.showQR(meter, total); // call QR with current bill
        });
        add(payButton);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        meter = tfmeter.getText();
        month = tfmonth.getText();
        String unitsStr = tfunits.getText();

        try {
            int units = Integer.parseInt(unitsStr);
            double rate = 7.0;
            total = units * rate;
            String status = "Unpaid";

            Conn c = new Conn();
            String query = "INSERT INTO bill(meter_no, month, units, total_amount, status) " +
                    "VALUES('" + meter + "', '" + month + "', " + units + ", " + total + ", '" + status + "')";
            c.s.executeUpdate(query);

            JOptionPane.showMessageDialog(null, "✅ Bill Generated: ₹" + total);
            payButton.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "❌ Error saving bill.");
        }
    }

    public static void main(String[] args) {
        new GenerateBillApp();
    }
}
