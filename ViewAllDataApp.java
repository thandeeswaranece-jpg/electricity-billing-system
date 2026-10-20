import javax.swing.*;
import java.awt.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;

public class ViewAllDataApp extends JFrame {

    public ViewAllDataApp() {
        setTitle("Electricity Billing - All Records");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JTabbedPane tabs = new JTabbedPane();

        // Customer Table Tab
        JTable customerTable = new JTable();
        JScrollPane customerScroll = new JScrollPane(customerTable);
        tabs.add("Customers", customerScroll);
        loadCustomerData(customerTable);

        // Bill Table Tab
        JTable billTable = new JTable();
        JScrollPane billScroll = new JScrollPane(billTable);
        tabs.add("Bills", billScroll);
        loadBillData(billTable);

        add(tabs);
        setVisible(true);
    }

    private void loadCustomerData(JTable table) {
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("SELECT * FROM customer");
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading customers: " + e.getMessage());
        }
    }

    private void loadBillData(JTable table) {
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("SELECT * FROM bill");
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading bills: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new ViewAllDataApp();
    }
}
