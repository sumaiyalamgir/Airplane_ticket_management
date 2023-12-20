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

public class Flightlog extends JFrame{
    private JPanel panel1;
    private JButton bookingbtn;
    private JButton cancelationctn;
    private JButton statbtn;
    private JButton flightLogButton;
    private JTable flightlist;
    private JButton updateButton;
    private JButton adminbtn;

    private void createTable() {

        DefaultTableModel tableModel = new DefaultTableModel(
                null,
                new String[] {"Flight Id", "Flight Name", "Departure City", "Arrival City", "Departure Date"}
        );

        try  {
            Connection connection = DatabaseConnection.getConnection();
            String query = "SELECT * FROM flights";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Vector<Object> rowData = new Vector<>();
                rowData.add(resultSet.getString("flight_id"));
                rowData.add(resultSet.getString("flight_name"));
                rowData.add(resultSet.getString("departure_city"));
                rowData.add(resultSet.getString("arrival_city"));
                rowData.add(resultSet.getString("departure_date"));

                tableModel.addRow(rowData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        flightlist.setModel(tableModel);
    }

    public Flightlog(){
        this.setContentPane(this.panel1);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(200, 50, 850, 500);
        createTable();
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Updateflight f = new Updateflight();
                dispose();
            }
        });
        adminbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Adminwork f = new Adminwork();
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

    public static void main(String[] args) {
        Flightlog f = new Flightlog();
    }
}
