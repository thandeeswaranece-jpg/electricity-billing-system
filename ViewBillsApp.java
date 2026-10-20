import javax.swing.*;
import net.proteanit.sql.DbUtils;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ViewBillsApp extends JFrame implements ActionListener {

    JTable table;
    JButton print;

    public ViewBillsApp() {
        setTitle("📄 Bill Records");
        setSize(850, 550);
        setLocationRelativeTo(null); // center the window
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.WHITE);

        JLabel heading = new JLabel("All Electricity Bills");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 22));
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(heading, BorderLayout.NORTH);

        table = new JTable();
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(25);

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        add(sp, BorderLayout.CENTER);

        print = new JButton("🖨️ Print / Export PDF");
        print.setFont(new Font("Segoe UI", Font.BOLD, 14));
        print.setBackground(new Color(33, 37, 41));
        print.setForeground(Color.WHITE);
        print.setFocusPainted(false);
        print.setPreferredSize(new Dimension(200, 40));
        print.addActionListener(this);
        add(print, BorderLayout.SOUTH);

        loadBills();

        setVisible(true);
    }

    private void loadBills() {
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("SELECT * FROM bill");
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to load bills");
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            boolean complete = table.print(); // native print dialog
            if (complete) {
                JOptionPane.showMessageDialog(this, "✅ Printed Successfully");
            } else {
                JOptionPane.showMessageDialog(this, "❌ Printing Cancelled");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Print Failed: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new ViewBillsApp();
    }
}
