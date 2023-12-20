package co.commit;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Signup extends JFrame{
    public Signup(){
        this.setContentPane(this.panel1);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(200, 50, 700, 400);
        signUpButton.addActionListener(e -> {
            String fullname = textField1.getText();
            String email = textField2.getText();
            String password = new String(passwordField1.getPassword());
            if (fullname.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Field Empty!", "Warning", JOptionPane.WARNING_MESSAGE);
            }else if (signupUser(fullname, email, password) && !isEmailTaken(email)) {
                //hh
                JOptionPane.showMessageDialog(null, "Signup Successful!");
            } else {
                dispose();
                JOptionPane.showMessageDialog(null, "Signup Failed. User with the same ID or Email already exists.");
            }
        });
    }
    Connection connection = DatabaseConnection.getConnection();
    private boolean isEmailTaken(String email) {
        String query = "SELECT * FROM users WHERE email = ?";
        try  {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next(); // If a record is found, the username is taken
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Handle the error as needed
        }
    }

    private static boolean signupUser(String fullname, String email, String password) {
        if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(null, "Invalid email format");
            return false;
        }
        if (password.length() < 8) {
            JOptionPane.showMessageDialog(null, "Password must be at least 8 characters long");
            return false;
        }
        try  {
            Connection connection = DatabaseConnection.getConnection();
            String query = "INSERT INTO users (fullname, email, password) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, fullname);
                preparedStatement.setString(2, email);
                preparedStatement.setString(3, password);
                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database connection error: " + e.getMessage());
        }
        return false;
    }
    private static boolean isValidEmail(String email) {
        // You can use a regular expression for basic email format validation
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(emailRegex);
    }
    private JPanel panel1;
    private JTextField textField1;
    private JTextField textField2;
    private JPasswordField passwordField1;
    private JButton signUpButton;
}
