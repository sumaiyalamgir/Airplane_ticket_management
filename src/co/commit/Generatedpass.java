package co.commit;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class Generatedpass extends JFrame {
    private JPanel panel1;
    private JTextArea name;
    private JTextArea flightid;
    private JTextArea frominfo;
    private JTextArea Toinfo;
    private JTextArea seatinfo;
    private JTextArea boardtime;
    private JTextArea Dairport;
    private JTextArea Rairport;
    private JButton homeButton;

    public Generatedpass(Map<String, Object> passInfo) {
        this.setContentPane(this.panel1);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(200, 50, 700, 450);
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Dashboard f = new Dashboard();
                dispose();
            }
        });

        // Populate text areas with data from passInfo map
        name.setText((String) passInfo.get("name"));
        flightid.setText(String.valueOf(passInfo.get("flight_name")));
        frominfo.setText((String) passInfo.get("departure_city"));
        Toinfo.setText((String) passInfo.get("arrival_city"));
        boardtime.setText((String) passInfo.get("board_time"));
        seatinfo.setText((String) passInfo.get("seat_number"));
    }


}
