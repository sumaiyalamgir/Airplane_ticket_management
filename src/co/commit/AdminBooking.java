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

public class AdminBooking extends JFrame{

    private void createTable() {

        DefaultTableModel tableModel = new DefaultTableModel(
                null,
                new String[] {"Serial no.", "Flight id", "Flight Name", "Seat Number", "Name", "Contact", "Payment Method", "" +
                        "departure City", "Arrival City", "Boarding Date"}
        );

        try  {
            Connection connection = DatabaseConnection.getConnection();
            String query = "SELECT * FROM bookings";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Vector<Object> rowData = new Vector<>();
                rowData.add(resultSet.getString("booking_id"));
                rowData.add(resultSet.getString("flight_id"));
                rowData.add(resultSet.getString("flight_name"));
                rowData.add(resultSet.getString("seat_number"));
                rowData.add(resultSet.getString("name"));
                rowData.add(resultSet.getString("contact"));
                rowData.add(resultSet.getString("payment_method"));
                rowData.add(resultSet.getString("depart_city"));
                rowData.add(resultSet.getString("arrival_city"));
                rowData.add(resultSet.getString("board_date"));

                tableModel.addRow(rowData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        table1.setModel(tableModel);
    }

    public AdminBooking(){
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
        cancelationctn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Admincancel f = new Admincancel();
                dispose();
            }
        });
        statbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Feature will be available soon");
            }
        });
    }
    private JButton bookingbtn;
    private JButton cancelationctn;
    private JButton statbtn;
    private JButton flightLogButton;
    private JTable table1;
    private JPanel panel1;

    public static void main(String[] args) {
        AdminBooking f = new AdminBooking();
    }
}
