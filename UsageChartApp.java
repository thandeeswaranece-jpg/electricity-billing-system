import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class UsageChartApp extends JFrame {

    Choice meterChoice;
    JButton showBtn;

    public UsageChartApp() {
        setTitle("📊 Monthly Electricity Usage Chart");
        setSize(800, 600);
        setLocationRelativeTo(null); // Center the window
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topPanel.setBackground(Color.WHITE);

        JLabel selectLabel = new JLabel("🔌 Select Meter No:");
        selectLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        topPanel.add(selectLabel);

        meterChoice = new Choice();
        meterChoice.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        topPanel.add(meterChoice);
        loadMeterNumbers();

        showBtn = new JButton("📈 Show Chart");
        showBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        showBtn.setBackground(new Color(0, 120, 215));
        showBtn.setForeground(Color.WHITE);
        topPanel.add(showBtn);

        showBtn.addActionListener(e -> loadChart());

        add(topPanel, BorderLayout.NORTH);
        setVisible(true);
    }

    private void loadMeterNumbers() {
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("SELECT DISTINCT meter_no FROM bill");
            while (rs.next()) {
                meterChoice.add(rs.getString("meter_no"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading meter numbers: " + e.getMessage());
        }
    }

    private void loadChart() {
        String meter = meterChoice.getSelectedItem();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery(
                    "SELECT month, units FROM bill WHERE meter_no = '" + meter + "' " +
                            "ORDER BY FIELD(month, 'January','February','March','April','May','June'," +
                            "'July','August','September','October','November','December')"
            );
            while (rs.next()) {
                dataset.addValue(rs.getInt("units"), "Units Used", rs.getString("month"));
            }

            JFreeChart chart = ChartFactory.createBarChart(
                    "Monthly Usage for Meter: " + meter,
                    "Month",
                    "Units",
                    dataset
            );

            ChartPanel chartPanel = new ChartPanel(chart);
            setContentPane(chartPanel);
            validate();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading chart data: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new UsageChartApp();
    }
}
