package co.commit;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class Admincancel extends JFrame{

    private void createTable() {

        DefaultTableModel tableModel = new DefaultTableModel(
                null,
                new String[] {"Serial no", "Name", "Flight Name", "Departure Date", "Reason of Cancellation"}
        );

        try  {
            Connection connection = DatabaseConnection.getConnection();
            String query = "SELECT * FROM cancellations";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Vector<Object> rowData = new Vector<>();
                rowData.add(resultSet.getString("id"));
                rowData.add(resultSet.getString("name"));
                rowData.add(resultSet.getString("flight_name"));
                rowData.add(resultSet.getString("flight_date"));
                rowData.add(resultSet.getString("cancellation_reason"));

                tableModel.addRow(rowData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        table1.setModel(tableModel);
    }

    public Admincancel(){
        this.setContentPane(this.panel1);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(200, 50, 850, 500);
        createTable();
        flightLogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Flightlog f = new Flightlog();
                dispose();
            }
        });
        bookingbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminBooking f = new AdminBooking();
                dispose();
            }
        });
        statbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Feature will be available soon");
            }
        });
        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteMatchingBookings();
            }
        });
    }
    private void deleteMatchingBookings() {
        String deleteBookingQuery = "DELETE FROM bookings WHERE (name, flight_name, board_date) IN (SELECT c.name, c.flight_name, c.flight_date FROM cancellations c)";

        try  {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement deleteBookingStatement = connection.prepareStatement(deleteBookingQuery);

            int rowsAffected = deleteBookingStatement.executeUpdate();

            if (rowsAffected > 0) {
                // Bookings deleted successfully
                JOptionPane.showMessageDialog(null, "Bookings cancelled!");
            } else {
                // No matching bookings found, or deletion failed
                JOptionPane.showMessageDialog(null, "No matching bookings found");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    private JPanel panel1;
    private JButton bookingbtn;
    private JButton cancelationctn;
    private JButton statbtn;
    private JButton flightLogButton;
    private JTable table1;
    private JButton acceptButton;

    public static void main(String[] args) {
        Admincancel f = new Admincancel();
    }
}

