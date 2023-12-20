package co.commit;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class Dashboard extends JFrame{


    private JButton bookSeatButton;
    private JPanel Bookseat_panel;
    private JPanel panel1;
    private JLabel date;
    private JButton cancelBoookingButton;
    private JButton backButton;

    public Dashboard(){
        this.setContentPane(this.panel1);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(200, 50, 700, 450);
        updateDateTime();
        bookSeatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SeatBookingpanel f = new SeatBookingpanel();
            }
        });
        cancelBoookingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CancelBooking f = new CancelBooking();
                dispose();
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Homepage f = new Homepage();
                dispose();
            }
        });
    }
    private void updateDateTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
        LocalDateTime now = LocalDateTime.now();
        date.setText(dtf.format(now));
    }

    public static void main(String[] args) {
        Dashboard f = new Dashboard();
    }
}
