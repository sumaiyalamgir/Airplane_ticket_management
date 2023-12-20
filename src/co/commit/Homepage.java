package co.commit;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Homepage extends JFrame{
    private JPanel panel1;
    private JButton signInButton;
    private JButton signUpButton;
    private JButton adminButton;

    public Homepage() {
        this.setContentPane(this.panel1);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(200, 200, 1000, 470);
        this.setTitle("Login");
        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Signin f = new Signin();
                dispose();
            }
        });
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Signup f = new Signup();
                dispose();
            }
        });
        adminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Adminsignin f = new Adminsignin();
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        Homepage frame = new Homepage();
        

    }
}
