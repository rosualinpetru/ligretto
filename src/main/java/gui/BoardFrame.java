/*
 * Created by JFormDesigner on Tue Nov 09 17:33:08 EET 2021
 */

package gui;

import core.card.CardColour;
import core.card.CardNumber;
import gui.managers.PairConsumer;
import utils.CardsLoader;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author Tania Topciov
 */
public class BoardFrame extends JFrame {
    public BoardFrame() {
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
        label1 = new JLabel();
        playButton = new JButton();
        pauseButton = new JButton();
        resumeButton = new JButton();

        //======== this ========
        setVisible(true);
        setMinimumSize(new Dimension(880, 660));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setForeground(SystemColor.controlText);
        setResizable(false);
        setTitle("Ligretto");
        setIconImage(new ImageIcon(getClass().getResource("/images/logo.png")).getImage());
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== boardGrid ========
        {
            boardGrid.setBorder(new EmptyBorder(12, 20, 12, 20));
            boardGrid.setMaximumSize(new Dimension(880, 520));
            boardGrid.setPreferredSize(new Dimension(880, 520));
            boardGrid.setMinimumSize(new Dimension(880, 520));
            boardGrid.setLayout(new GridLayout(4, 6, 40, 12));
        }
        contentPane.add(boardGrid, BorderLayout.CENTER);

        //======== playerCardsGrid ========
        {
            playerCardsGrid.setMinimumSize(new Dimension(880, 120));
            playerCardsGrid.setPreferredSize(new Dimension(880, 120));
            playerCardsGrid.setBorder(new EmptyBorder(0, 0, 4, 8));
            playerCardsGrid.setMaximumSize(new Dimension(880, 120));
            playerCardsGrid.setLayout(new GridLayout(1, 10, 12, 0));
            playerCardsGrid.add(label3);

            try {
                BufferedImage cardBackImage = ImageIO.read(Objects.requireNonNull(CardsLoader.class.getResource("/images/cards/card_back.png")));
                Image scaledImage = cardBackImage.getScaledInstance(3 * cardBackImage.getWidth() / 4, 3 * cardBackImage.getHeight() / 4, Image.SCALE_SMOOTH);
                card1Label.setIcon(new ImageIcon(scaledImage));
                card2Label.setIcon(new ImageIcon(scaledImage));
                card3Label.setIcon(new ImageIcon(scaledImage));
                shuffle.setIcon(new ImageIcon(scaledImage));
            } catch (IOException e) {
                e.printStackTrace();
            }

            //---- card1Label ----
            playerCardsGrid.add(card1Label);

            //---- card2Label ----
            playerCardsGrid.add(card2Label);

            //---- card3Label ----
            playerCardsGrid.add(card3Label);

            //======== panel1 ========
            {
                panel1.setLayout(new GridLayout(1, 1));
            }
            playerCardsGrid.add(panel1);

            //---- shuffle ----
            playerCardsGrid.add(shuffle);
            playerCardsGrid.add(label9);

            //======== panel3 ========
            {
                panel3.setLayout(new GridLayout(3, 1, 0, 10));

                //---- playButton ----
                playButton.setText("Play");
                playButton.setFocusable(false);
                playButton.setFont(new Font("Segoe Print", Font.BOLD, 16));
                playButton.setForeground(new Color(0, 153, 51));
                playButton.setMaximumSize(new Dimension(100, 35));
                playButton.setMinimumSize(new Dimension(100, 35));
                playButton.setPreferredSize(new Dimension(100, 35));
                panel3.add(playButton);

                //---- pauseButton ----
                pauseButton.setText("Pause");
                pauseButton.setFocusable(false);
                pauseButton.setFont(new Font("Segoe Print", Font.BOLD, 16));
                pauseButton.setForeground(new Color(0, 153, 51));
                pauseButton.setMaximumSize(new Dimension(100, 40));
                pauseButton.setMinimumSize(new Dimension(100, 40));
                pauseButton.setPreferredSize(new Dimension(100, 40));
                panel3.add(pauseButton);

                //---- resumeButton ----
                resumeButton.setText("Resume");
                resumeButton.setFocusable(false);
                resumeButton.setFont(new Font("Segoe Print", Font.BOLD, 16));
                resumeButton.setForeground(new Color(0, 153, 51));
                resumeButton.setMaximumSize(new Dimension(100, 40));
                resumeButton.setMinimumSize(new Dimension(100, 40));
                resumeButton.setPreferredSize(new Dimension(100, 40));
                panel3.add(resumeButton);
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
    private JLabel label1;
    private JButton playButton;
    private JButton pauseButton;
    private JButton resumeButton;

    // JFormDesigner - End of variables declaration  //GEN-END:variables

    public void setCard1(CardColour colour, CardNumber number) {
        BufferedImage image = CardsLoader.getInstance().getCard(colour, number);
        Image scaledImage = image.getScaledInstance(card1Label.getWidth(), card1Label.getHeight(), Image.SCALE_SMOOTH);
        card1Label.setIcon(new ImageIcon(scaledImage));
    }

    public void setCard2(CardColour colour, CardNumber number) {
        BufferedImage image = CardsLoader.getInstance().getCard(colour, number);
        Image scaledImage = image.getScaledInstance(card2Label.getWidth(), card2Label.getHeight(), Image.SCALE_SMOOTH);
        card2Label.setIcon(new ImageIcon(scaledImage));
    }

    public void setCard3(CardColour colour, CardNumber number) {
        BufferedImage image = CardsLoader.getInstance().getCard(colour, number);
        Image scaledImage = image.getScaledInstance(card3Label.getWidth(), card3Label.getHeight(), Image.SCALE_SMOOTH);
        card3Label.setIcon(new ImageIcon(scaledImage));
    }

    public void setShuffle(CardColour colour, CardNumber number) {
        BufferedImage image = CardsLoader.getInstance().getCard(colour, number);
        Image scaledImage = image.getScaledInstance(shuffle.getWidth(), shuffle.getHeight(), Image.SCALE_SMOOTH);
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

    public void setPauseButtonClickEventListener(Consumer<MouseEvent> consumer) {
        pauseButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                consumer.accept(e);
            }
        });
    }

    public void setResumeButtonClickEventListener(Consumer<MouseEvent> consumer) {
        resumeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                consumer.accept(e);
            }
        });
    }

    public void setPlayButtonClickEventListener(Consumer<MouseEvent> consumer) {
        playButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (playButton.isEnabled()) {
                    consumer.accept(e);
                    playButton.setEnabled(false);
                }
            }
        });
    }

    public void addLabels() {
        for (int i = 0; i < 24; i++) {
            boardGrid.add(new JLabel());
        }
        pack();
    }

    public void setDeckClickEventListener(PairConsumer<Integer, MouseEvent> consumer) {
        for (int i = 0; i < 24; i++) {
            int finalI = i + 1;
            boardGrid.getComponent(i).addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    consumer.accept(finalI, e);
                }
            });
        }
    }

    public void setShuffleCardBack() {
        BufferedImage image = CardsLoader.getInstance().getCardBack();
        Image scaledImage = image.getScaledInstance(shuffle.getWidth(), shuffle.getHeight(), Image.SCALE_SMOOTH);
        shuffle.setIcon(new ImageIcon(scaledImage));
    }
}
