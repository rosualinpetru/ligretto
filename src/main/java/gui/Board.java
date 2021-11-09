/*
 * Created by JFormDesigner on Tue Nov 09 17:33:08 EET 2021
 */

package gui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;
import javax.swing.*;

/**
 * @author Tania Topciov
 */
public class Board extends JFrame {
    public Board() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        panel1 = new JPanel();
        panel2 = new JPanel();
        label2 = new JLabel();
        label3 = new JLabel();
        label6 = new JLabel();
        card1Label = new JLabel();
        card2Label = new JLabel();
        card3Label = new JLabel();
        label9 = new JLabel();
        label10 = new JLabel();
        label1 = new JLabel();

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== panel1 ========
        {
            panel1.setLayout(new GridLayout(4, 6));
        }
        contentPane.add(panel1, BorderLayout.CENTER);

        //======== panel2 ========
        {
            panel2.setMinimumSize(new Dimension(880, 660));
            panel2.setPreferredSize(new Dimension(880, 120));
            panel2.setLayout(new GridLayout(1, 10, 4, 0));
            panel2.add(label2);
            panel2.add(label3);
            panel2.add(label6);

            //---- card1Label ----
            card1Label.setText("card2");
            card1Label.setIcon(new ImageIcon(getClass().getResource("/images/cards/BLUE_EIGHT.png")));
            panel2.add(card1Label);

            //---- card2Label ----
            card2Label.setText("card3");
            card2Label.setIcon(new ImageIcon(getClass().getResource("/images/cards/GREEN_TWO.png")));
            panel2.add(card2Label);

            //---- card3Label ----
            card3Label.setText("shuffle");
            card3Label.setIcon(new ImageIcon(getClass().getResource("/images/cards/YELLOW_FIVE.png")));
            panel2.add(card3Label);
            panel2.add(label9);
            panel2.add(label10);

            //---- label1 ----
            label1.setText("buton");
            panel2.add(label1);
        }
        contentPane.add(panel2, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel panel1;
    private JPanel panel2;
    private JLabel label2;
    private JLabel label3;
    private JLabel label6;
    private JLabel card1Label;
    private JLabel card2Label;
    private JLabel card3Label;
    private JLabel label9;
    private JLabel label10;
    private JLabel label1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    public void setCard1(Image image) {
        Image scaledImage = image.getScaledInstance(card1Label.getWidth(), card1Label.getHeight(), Image.SCALE_SMOOTH);
        card1Label.setIcon(new ImageIcon(scaledImage));
    }

    public void setCard1ClickEventListener(Consumer<MouseEvent> consumer) {
        card1Label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                consumer.accept(e);
            }
        });
    }
}
