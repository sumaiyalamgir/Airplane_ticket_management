package co.commit;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class Adminsignin extends JFrame{
    private JPanel panel1;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton loginButton;

    public Adminsignin(){
        this.setContentPane(this.panel1);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(200, 50, 500, 350);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = textField1.getText();
                String pass = new String(passwordField1.getPassword());
                if (Objects.equals(username, "ADMIN") && pass.equals("qwerty")){
                    Adminwork f = new Adminwork();
                    dispose();
                }else {
                    JOptionPane.showMessageDialog(null, "Incorrect Password", "Warning", 2);
                }
            }
        });
    }

    public static void main(String[] args) {
        Adminsignin f = new Adminsignin();
    }
}
