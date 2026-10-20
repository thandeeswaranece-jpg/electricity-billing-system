import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AddCustomerApp extends JFrame implements ActionListener {

    JTextField tfname, tfaddress, tfcity, tfstate, tfemail, tfphone;
    JLabel lblmeter;
    JButton submit;

    String meterNumber; // auto-generated meter number

    public AddCustomerApp() {
        setTitle("Add New Customer");
        setSize(500, 450);
        setLocation(450, 200);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        JLabel heading = new JLabel("Add Customer");
        heading.setBounds(160, 10, 200, 30);
        heading.setFont(new Font("Tahoma", Font.BOLD, 20));
        add(heading);

        JLabel lblname = new JLabel("Customer Name:");
        lblname.setBounds(50, 60, 150, 20);
        add(lblname);

        tfname = new JTextField();
        tfname.setBounds(200, 60, 230, 20);
        add(tfname);

        JLabel lblmeterText = new JLabel("Meter Number:");
        lblmeterText.setBounds(50, 100, 150, 20);
        add(lblmeterText);

        // Auto-generate meter number
        meterNumber = "MTR" + System.currentTimeMillis(); // Unique
        lblmeter = new JLabel(meterNumber);
        lblmeter.setBounds(200, 100, 230, 20);
        lblmeter.setFont(new Font("Tahoma", Font.BOLD, 13));
        add(lblmeter);

        JLabel lbladdress = new JLabel("Address:");
        lbladdress.setBounds(50, 140, 150, 20);
        add(lbladdress);

        tfaddress = new JTextField();
        tfaddress.setBounds(200, 140, 230, 20);
        add(tfaddress);

        JLabel lblcity = new JLabel("City:");
        lblcity.setBounds(50, 180, 150, 20);
        add(lblcity);

        tfcity = new JTextField();
        tfcity.setBounds(200, 180, 230, 20);
        add(tfcity);

        JLabel lblstate = new JLabel("State:");
        lblstate.setBounds(50, 220, 150, 20);
        add(lblstate);

        tfstate = new JTextField();
        tfstate.setBounds(200, 220, 230, 20);
        add(tfstate);

        JLabel lblemail = new JLabel("Email:");
        lblemail.setBounds(50, 260, 150, 20);
        add(lblemail);

        tfemail = new JTextField();
        tfemail.setBounds(200, 260, 230, 20);
        add(tfemail);

        JLabel lblphone = new JLabel("Phone:");
        lblphone.setBounds(50, 300, 150, 20);
        add(lblphone);

        tfphone = new JTextField();
        tfphone.setBounds(200, 300, 230, 20);
        add(tfphone);

        submit = new JButton("Submit");
        submit.setBounds(200, 350, 100, 30);
        submit.addActionListener(this);
        add(submit);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        String name = tfname.getText();
        String meter = meterNumber;
        String address = tfaddress.getText();
        String city = tfcity.getText();
        String state = tfstate.getText();
        String email = tfemail.getText();
        String phone = tfphone.getText();

        try {
            Conn c = new Conn();
            String query = "INSERT INTO customer (meter_no, name, address, city, state, email, phone) VALUES " +
                    "('" + meter + "', '" + name + "', '" + address + "', '" + city + "', '" + state + "', '" + email + "', '" + phone + "')";
            c.s.executeUpdate(query);

            JOptionPane.showMessageDialog(null, "✅ Customer Added Successfully!");
            setVisible(false);
        } catch (SQLIntegrityConstraintViolationException dupEx) {
            JOptionPane.showMessageDialog(this, "❌ Meter number already exists.");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "❌ Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new AddCustomerApp();
    }
}
