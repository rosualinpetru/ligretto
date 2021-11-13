/*
 * Created by JFormDesigner on Tue Nov 09 17:33:08 EET 2021
 */

package gui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author Tania Topciov
 */
public class Board extends JFrame {
    public Board() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        boardGrid = new JPanel();
        playerCardsGrid = new JPanel();
        label3 = new JLabel();
        card1Label = new JLabel();
        card2Label = new JLabel();
        card3Label = new JLabel();
        panel1 = new JPanel();
        shuffle = new JLabel();
        label9 = new JLabel();
        panel3 = new JPanel();
        label4 = new JLabel();
        button1 = new JButton();

        //======== this ========
        setVisible(true);
        setMinimumSize(new Dimension(880, 660));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setForeground(SystemColor.controlText);
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== boardGrid ========
        {
            boardGrid.setBorder(new EmptyBorder(12, 20, 12, 20));
            boardGrid.setLayout(new GridLayout(4, 6, 40, 12));
        }
        contentPane.add(boardGrid, BorderLayout.CENTER);

        //======== playerCardsGrid ========
        {
            playerCardsGrid.setMinimumSize(new Dimension(880, 660));
            playerCardsGrid.setPreferredSize(new Dimension(880, 120));
            playerCardsGrid.setBorder(new EmptyBorder(0, 0, 4, 8));
            playerCardsGrid.setLayout(new GridLayout(1, 10, 12, 0));
            playerCardsGrid.add(label3);

            //---- card1Label ----
            card1Label.setIcon(new ImageIcon(getClass().getResource("/images/cards/RED_SEVEN.png")));
            playerCardsGrid.add(card1Label);

            //---- card2Label ----
            card2Label.setIcon(new ImageIcon(getClass().getResource("/images/cards/BLUE_EIGHT.png")));
            playerCardsGrid.add(card2Label);

            //---- card3Label ----
            card3Label.setIcon(new ImageIcon(getClass().getResource("/images/cards/GREEN_TWO.png")));
            playerCardsGrid.add(card3Label);

            //======== panel1 ========
            {
                panel1.setLayout(new GridLayout(1, 1));
            }
            playerCardsGrid.add(panel1);

            //---- shuffle ----
            shuffle.setIcon(new ImageIcon(getClass().getResource("/images/cards/YELLOW_FIVE.png")));
            playerCardsGrid.add(shuffle);
            playerCardsGrid.add(label9);

            //======== panel3 ========
            {
                panel3.setLayout(new GridLayout(3, 1));
                panel3.add(label4);

                //---- button1 ----
                button1.setText("Pause");
                button1.setFont(new Font("Segoe Print", Font.BOLD, 14));
                button1.setForeground(new Color(0, 153, 51));
                panel3.add(button1);
            }
            playerCardsGrid.add(panel3);
        }
        contentPane.add(playerCardsGrid, BorderLayout.SOUTH);
        setSize(880, 660);
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents


    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel boardGrid;
    private JPanel playerCardsGrid;
    private JLabel label3;
    private JLabel card1Label;
    private JLabel card2Label;
    private JLabel card3Label;
    private JPanel panel1;
    private JLabel shuffle;
    private JLabel label9;
    private JPanel panel3;
    private JLabel label4;
    private JButton button1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    public void setCard1(Image image) {
        Image scaledImage = image.getScaledInstance(card1Label.getWidth(), card1Label.getHeight(), Image.SCALE_SMOOTH);
        card1Label.setIcon(new ImageIcon(scaledImage));
    }

    public void setCard2(Image image) {
        Image scaledImage = image.getScaledInstance(card2Label.getWidth(), card1Label.getHeight(), Image.SCALE_SMOOTH);
        card2Label.setIcon(new ImageIcon(scaledImage));
    }

    public void setCard3(Image image) {
        Image scaledImage = image.getScaledInstance(card3Label.getWidth(), card1Label.getHeight(), Image.SCALE_SMOOTH);
        card3Label.setIcon(new ImageIcon(scaledImage));
    }

    public void setShuffle(Image image) {
        Image scaledImage = image.getScaledInstance(shuffle.getWidth(), card1Label.getHeight(), Image.SCALE_SMOOTH);
        shuffle.setIcon(new ImageIcon(scaledImage));
    }

    public void setImageAtPosition(Image image, int position) {
        JLabel labelAtPos = (JLabel) boardGrid.getComponent(position);

        Image scaledImage = image.getScaledInstance(labelAtPos.getWidth(), labelAtPos.getHeight(), Image.SCALE_SMOOTH);

        labelAtPos.setIcon(new ImageIcon(scaledImage));
    }

    public void setCard1ClickEventListener(Consumer<MouseEvent> consumer) {
        card1Label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                consumer.accept(e);
            }
        });
    }

    public void setCard2ClickEventListener(Consumer<MouseEvent> consumer) {
        card2Label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                consumer.accept(e);
            }
        });
    }

    public void setCard3ClickEventListener(Consumer<MouseEvent> consumer) {
        card3Label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                consumer.accept(e);
            }
        });
    }

    public void setShuffleClickEventListener(Consumer<MouseEvent> consumer) {
        shuffle.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                consumer.accept(e);
            }
        });
    }

    public void addLabels() {
        for (int i = 0; i < 24; i++) {
            boardGrid.add(new JLabel());
        }
        pack();
    }
}
