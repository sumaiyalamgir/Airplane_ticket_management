package co.commit;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.Objects;

public class Updateflight extends JFrame{
    private JPanel panel1;
    private JTextField Filghtname;
    private JDateChooser JDateChooser1;
    private JComboBox Dcity;
    private JComboBox acity;
    private JButton updateButton;
    private JButton backButton;

    public Updateflight() {
        this.setContentPane(this.panel1);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(200, 50, 850, 500);
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateButton.setEnabled(false);
                String filghtnameText = Filghtname.getText();
                String dcityText = (String) Dcity.getSelectedItem();
                String acityText = (String) acity.getSelectedItem();
                Date date = JDateChooser1.getDate();

                String insertQuery = "INSERT INTO flights (flight_name, departure_city, arrival_city, departure_date) VALUES (?, ?, ?, ?)";


                if (validateForm()) {
                    try  {
                        Connection connection = DatabaseConnection.getConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                        preparedStatement.setString(1, filghtnameText);
                        preparedStatement.setString(2, dcityText);
                        preparedStatement.setString(3, acityText);
                        preparedStatement.setDate(4, new java.sql.Date(date.getTime()));

                        int rowsAffected = preparedStatement.executeUpdate();

                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(null, "Data inserted successfully!");
                            Flightlog f = new Flightlog();
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(null, "Data insertion failed!");
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Fill up all required field!");
                }
            }

        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Adminwork f = new Adminwork();
                dispose();
            }
        });
    }
    private boolean validateForm() {
        String filghtnameText = Filghtname.getText();
        String dcityText = (String) Dcity.getSelectedItem();
        String acityText = (String) acity.getSelectedItem();
        Date date = JDateChooser1.getDate();
        assert filghtnameText != null;
        if (filghtnameText.isEmpty() || Objects.requireNonNull(acityText).isEmpty() || Objects.requireNonNull(dcityText).isEmpty() || date == null) {
            // At least one required field is empty, show an error message
            JOptionPane.showMessageDialog(null, "Please fill in all required fields.", "warning", JOptionPane.WARNING_MESSAGE);
            return false;
        } else {
            return true;
        }
    }
    public void createUIComponents(){
        JDateChooser1 = new JDateChooser();
    }

    public static void main(String[] args) {
        Updateflight f = new Updateflight();
    }
}

