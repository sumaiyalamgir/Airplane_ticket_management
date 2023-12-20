package co.commit;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class CancelBooking extends JFrame{
    private JPanel panel1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton backButton;
    private JButton submitButton;
    private JDateChooser JDateChooser2;

    private void createUIComponents(){
        JDateChooser2 = new JDateChooser();
    }

    public CancelBooking(){
        this.setContentPane(this.panel1);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(200, 50, 700, 450);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Dashboard f = new Dashboard();
                dispose();
            }
        });
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitButton.setEnabled(false);

                String name = textField1.getText();
                String flightname  = textField2.getText();
                String reason = textField3.getText();
                Date board_date = JDateChooser2.getDate();

                // Check if a booking with the specified name exists
                String bookingCheckQuery = "SELECT * FROM bookings WHERE name = ? AND flight_name = ? AND  board_date = ?";
                try{
                    Connection connection = DatabaseConnection.getConnection();
                    PreparedStatement bookingCheckStatement = connection.prepareStatement(bookingCheckQuery);

                    bookingCheckStatement.setString(1, name);
                    bookingCheckStatement.setString(2, flightname);
                    bookingCheckStatement.setDate(3, new java.sql.Date(board_date.getTime()));

                    ResultSet bookingCheckResult = bookingCheckStatement.executeQuery();

                    if (bookingCheckResult.next()) {
                        // A booking with the specified details exists, proceed with cancellation

                        // Insert a new entry into the cancellations table
                        String cancellationInsertQuery = "INSERT INTO cancellations (name, flight_name, flight_date, cancellation_reason) VALUES (?, ?, ?, ?)";
                        try {
                            PreparedStatement cancellationInsertStatement = connection.prepareStatement(cancellationInsertQuery);

                            cancellationInsertStatement.setString(1, name);
                            cancellationInsertStatement.setString(2, flightname);
                            cancellationInsertStatement.setDate(3, new java.sql.Date(board_date.getTime()));
                            cancellationInsertStatement.setString(4, reason);

                            int rowsAffected = cancellationInsertStatement.executeUpdate();

                            if (rowsAffected > 0) {
                                JOptionPane.showMessageDialog(null, "Cancellation entry inserted successfully!");
                            } else {
                                JOptionPane.showMessageDialog(null, "Cancellation entry insertion failed!");
                            }

                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }

                    } else {
                        // No booking found, show an error message
                        JOptionPane.showMessageDialog(null, "No matching booking found for cancellation.");
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }
        });
    }

    public static void main(String[] args) {
        CancelBooking f = new CancelBooking();
    }
}
