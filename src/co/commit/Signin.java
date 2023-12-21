package co.commit;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Signin extends JFrame{

    public Signin(){
        this.setContentPane(this.panel1);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(200, 50, 700, 500);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = Emailfield.getText();
                String password = new String(passwordField1.getPassword());

                if (validateAccount(email, password)) {
                    Dashboard f = new Dashboard();
                    dispose();
                    JOptionPane.showMessageDialog(null, "Login Successful!");
                } else {
                    JOptionPane.showMessageDialog(null, "Login Failed. Invalid email or password.");
                }

            }
        });
    }
    private static boolean validateAccount(String email, String password) {
        try  {
            Connection connection = DatabaseConnection.getConnection();
            String query = "SELECT password FROM users WHERE email = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String storedPassword = resultSet.getString("password");
                return password.equals(storedPassword);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database connection error: " + e.getMessage());
        }
        return false;
    }
    private JPanel panel1;
    private JTextField Emailfield;
    private JPasswordField passwordField1;
    private JButton loginButton;

    public static void main(String[] args) {
        Signin f = new Signin();
    }
}
