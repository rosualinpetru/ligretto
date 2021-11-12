package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BackgroundPanel extends JPanel {

    private Image image;
    private JLabel label;

    public BackgroundPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.white);

        this.image = new ImageIcon(getClass().getResource("/images/background.jpg")).getImage();

        label = new JLabel("Ligretto");
        label.setVerticalAlignment(JLabel.TOP);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setForeground(Color.decode("0x009933"));
        label.setFont(new Font("Segoe Print", Font.BOLD, 48));
        add(label, BorderLayout.PAGE_START);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(this.image, 112, 0, getWidth() - 220, getHeight(), this);
    }

}
