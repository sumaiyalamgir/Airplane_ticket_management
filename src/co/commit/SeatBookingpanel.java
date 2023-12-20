package co.commit;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SeatBookingpanel extends JFrame{
    public SeatBookingpanel(){
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(childRadioButton);
        buttonGroup.add(adultRadioButton);
        this.setContentPane(this.panel1);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(200, 50,900, 500);

        twoWayTripRadioButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                boolean isSelected = e.getStateChange() == ItemEvent.SELECTED;
                JDateChooser2.setEnabled(isSelected);
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dcity = (String) comboBox1.getSelectedItem();
                String acity = (String) comboBox2.getSelectedItem();
                Date date = JDateChooser1.getDate();

                boolean isTwoWayTrip = twoWayTripRadioButton.isSelected();
                Date returnDate = isTwoWayTrip ? JDateChooser2.getDate() : null;

                String query = "SELECT * FROM flights WHERE departure_city = ? AND arrival_city = ? AND departure_date = ?";
                boolean flightFound = false;
                String flightNumber = null;

                try {
                    Connection connection = DatabaseConnection.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement(query);

                    preparedStatement.setString(1, dcity);
                    preparedStatement.setString(2, acity);
                    preparedStatement.setDate(3, new java.sql.Date(date.getTime()));

                    ResultSet resultSet = preparedStatement.executeQuery();

                    if (resultSet.next()) {
                        flightFound = true;
                        flightNumber = resultSet.getString("flight_name");
                        //Map<String, Object> passInfo = extractPassengerPassInfo(resultSet);
                        //Generatedpass generatedPass = new Generatedpass(passInfo);
                        //generatedPass.setPassengerPassInfo(passInfo);
                    } else {
                        JOptionPane.showMessageDialog(null, "No matching flights found.");
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                if (!flightFound) {
                    for (Component component : panel2.getComponents()) {
                        if (component instanceof JTextField) {
                            JTextField textField = (JTextField) component;
                            textField.setEnabled(false);
                        }
                    }
                }

                if (flightFound) {
                    textField1.setEnabled(true);
                    textField2.setEnabled(true);
                    textField3.setEnabled(true);

                }
            }
        });


        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmButton.setEnabled(false);

                String name = textField1.getText();
                String contact = textField3.getText();
                String email = textField2.getText();
                String pmethod = (String) comboBox3.getSelectedItem();
                String from = (String) comboBox1.getSelectedItem();
                String to = (String) comboBox2.getSelectedItem();
                Date board_date = JDateChooser1.getDate();

                // Search for the flight number
                String query = "SELECT * FROM flights WHERE departure_city = ? AND arrival_city = ? AND departure_date = ?";
                String flightNumber = null;

                try {
                    Connection connection = DatabaseConnection.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement(query);

                    preparedStatement.setString(1, from);
                    preparedStatement.setString(2, to);
                    preparedStatement.setDate(3, new java.sql.Date(board_date.getTime()));

                    ResultSet resultSet = preparedStatement.executeQuery();

                    if (resultSet.next()) {
                        flightNumber = resultSet.getString("flight_name");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                String insertQuery = "INSERT INTO bookings (flight_name, name, contact, payment_method, depart_city, arrival_city, board_date, seat_number) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

                if (validateForm()) {
                    try {
                        Connection connection = DatabaseConnection.getConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                        preparedStatement.setString(1, flightNumber);
                        preparedStatement.setString(2, name);
                        preparedStatement.setString(3, contact);
                        preparedStatement.setString(4, pmethod);
                        preparedStatement.setString(5, from);
                        preparedStatement.setString(6, to);
                        preparedStatement.setDate(7, new java.sql.Date(board_date.getTime()));
                        String seatNumber = allocateSeatNumber(connection, flightNumber);
                        preparedStatement.setString(8, seatNumber);

                        int rowsAffected = preparedStatement.executeUpdate();

                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(null, "Data inserted successfully!");
                            Map<String, Object> passInfo = new HashMap<>();
                            passInfo.put("name", name);
                            passInfo.put("flight_name", flightNumber);
                            passInfo.put("departure_city", from);
                            passInfo.put("arrival_city", to);
                            passInfo.put("board_time", "");
                            passInfo.put("seat_number", seatNumber);

                            Generatedpass generatedPass = new Generatedpass(passInfo);
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(null, "Data insertion failed!");
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Fill up all required fields!");
                }
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Dashboard f = new Dashboard();
                dispose();
            }
        });
    }
    private Map<String, Object> extractPassengerPassInfo(ResultSet resultSet) throws SQLException {
        Map<String, Object> passInfo = new HashMap<>();


        passInfo.put("name", resultSet.getString("name"));
        passInfo.put("flight_name", resultSet.getString("flight_name"));
        passInfo.put("depart_city", resultSet.getString("depart_city"));
        passInfo.put("arrival_city", resultSet.getString("arrival_city"));
        passInfo.put("seat_number", resultSet.getString("seat_number"));
        passInfo.put("board_date", resultSet.getDate("board_date"));


        return passInfo;
    }
    private boolean validateForm() {
        String name = textField1.getText();
        String contact = textField3.getText();
        String email = textField2.getText();
        String pmethod = (String) comboBox3.getSelectedItem();
        String from = (String) comboBox1.getSelectedItem();
        String to = (String) comboBox2.getSelectedItem();
        Date board_date = JDateChooser1.getDate();
        assert name != null;
        if (name.isEmpty() || Objects.requireNonNull(pmethod).isEmpty() ||contact.isEmpty() ||email.isEmpty()
        ||Objects.requireNonNull(from).isEmpty()  || Objects.requireNonNull(to).isEmpty() ||  board_date == null) {
            JOptionPane.showMessageDialog(null, "Please fill in all required fields.", "warning", JOptionPane.WARNING_MESSAGE);
            return false;
        } else {
            return true;
        }
    }
    private String allocateSeatNumber(Connection connection, String flightName) throws SQLException {
        String seatQuery = "SELECT MAX(seat_number) AS last_seat FROM flights WHERE flight_name = ?";
        try (PreparedStatement seatStatement = connection.prepareStatement(seatQuery)) {
            seatStatement.setString(1, flightName);
            ResultSet seatResultSet = seatStatement.executeQuery();
            if (seatResultSet.next()) {
                String lastSeat = seatResultSet.getString("last_seat");
                return generateNextSeat(lastSeat);
            } else {
                return "A1"; // If no seat allocated yet, start from A1
            }
        }
    }


    private String generateNextSeat(String lastSeat) {
        if (lastSeat == null || lastSeat.isEmpty()) {
            return "A1";
        }

            char letter = lastSeat.charAt(0);
            int number = Integer.parseInt(lastSeat.substring(1));

            if (number < 9) {
                number++;
            } else {
                letter++;
                number = 1;
            }


        return String.valueOf(letter) + number;
    }



    private JPanel panel1;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JDateChooser JDateChooser1;
    private JDateChooser JDateChooser2;
    private JRadioButton childRadioButton;
    private JRadioButton adultRadioButton;
    private JButton searchButton;
    private JRadioButton twoWayTripRadioButton;
    private JPanel panel2;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JRadioButton maleRadioButton;
    private JRadioButton femaleRadioButton;
    private JButton confirmButton;
    private JComboBox comboBox3;
    private JButton backButton;
    private JPanel panel3;

    private void createUIComponents(){
        JDateChooser1 = new JDateChooser();
        JDateChooser2 = new JDateChooser();
    }

    public static void main(String[] args) {
        SeatBookingpanel f = new SeatBookingpanel();
    }
}
