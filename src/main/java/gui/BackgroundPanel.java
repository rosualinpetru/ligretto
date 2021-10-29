package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BackgroundPanel extends JPanel implements ActionListener {

    private Image image;
    private JLabel label;
    private JButton startButton;

    public BackgroundPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.white);

        this.image = new ImageIcon(getClass().getResource("/images/bgnd.jpg")).getImage();

        label = new JLabel("Ligretto");
        label.setVerticalAlignment(JLabel.TOP);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setForeground(Color.ORANGE);
        label.setFont(new Font("MV Boli", Font.BOLD, 56));
        add(label, BorderLayout.PAGE_START);

        startButton = new JButton();
        startButton.setPreferredSize(new Dimension(40, 40));
        startButton.setBackground(Color.ORANGE);
        startButton.setVisible(true);
        startButton.setText("Start Game");
        startButton.addActionListener(this::actionPerformed);
        add(startButton, BorderLayout.PAGE_END);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(this.image, 112, 0, getWidth() - 220, getHeight(), this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == startButton)
        {
            label.setText("A mers");
        }
    }
}
