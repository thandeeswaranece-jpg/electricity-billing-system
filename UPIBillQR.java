import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class UPIBillQR {

    public static void showQR(String meterNo, double amount) {
        String yourUpiId = "thandeeswarano44@oksbi";
        String yourName = "Tamil Nadu EB";

        String upiLink = "upi://pay?pa=" + yourUpiId +
                "&pn=" + yourName +
                "&tn=Bill+MTR+" + meterNo +
                "&am=" + String.format("%.2f", amount) +
                "&cu=INR";

        String qrPath = "upi_" + meterNo + ".png";

        try {
            QRCodeWriter qrWriter = new QRCodeWriter();
            BitMatrix matrix = qrWriter.encode(upiLink, BarcodeFormat.QR_CODE, 300, 300);
            Path path = FileSystems.getDefault().getPath(qrPath);
            MatrixToImageWriter.writeToPath(matrix, "PNG", path);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "❌ QR generation failed.");
            return;
        }

        ImageIcon qrIcon = new ImageIcon(qrPath);
        JLabel qrLabel = new JLabel(qrIcon);
        qrLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JFrame frame = new JFrame("Pay ₹" + amount);
        frame.setSize(350, 420);
        frame.setLayout(new BorderLayout(10, 10));
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.WHITE);

        JLabel title = new JLabel("Scan to Pay ₹" + amount, SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JLabel tip = new JLabel("GPay / PhonePe / Paytm", SwingConstants.CENTER);
        tip.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tip.setForeground(Color.DARK_GRAY);

        JPanel top = new JPanel(new GridLayout(2, 1));
        top.setBackground(Color.WHITE);
        top.add(title);
        top.add(tip);

        frame.add(top, BorderLayout.NORTH);
        frame.add(qrLabel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
