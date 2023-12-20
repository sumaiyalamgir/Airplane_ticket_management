package co.commit;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Adminwork extends JFrame{
    private JButton flightLogButton;
    private JButton bookingbtn;
    private JButton cancelationctn;
    private JButton statbtn;
    private JPanel panel1;

    public Adminwork(){
        this.setContentPane(this.panel1);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(200, 50, 850, 500);
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
        Adminwork f = new Adminwork();
    }
}
