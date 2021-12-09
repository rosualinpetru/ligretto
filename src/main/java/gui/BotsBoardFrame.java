/*
 * Created by JFormDesigner on Sun Dec 05 11:43:13 EET 2021
 */

package gui;

import javax.swing.border.*;
import core.card.Card;
import core.card.CardColour;
import core.card.CardNumber;
import core.entities.Bot;
import gui.managers.BotCardManager;
import utils.CardsLoader;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.function.Consumer;
import javax.swing.*;

/**
 * @author Ana Ivaschescu
 */
public class BotsBoardFrame extends JFrame {
    public BotsBoardFrame() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Ana Ivaschescu
        botNorthCardsGrid = new JPanel();
        label13 = new JLabel();
        card1North = new JLabel();
        card2North = new JLabel();
        card3North = new JLabel();
        label17 = new JLabel();
        shuffleNorth = new JLabel();
        label20 = new JLabel();
        botEastCardsGrid = new JPanel();
        card1East = new JLabel();
        card2East = new JLabel();
        card3East = new JLabel();
        label24 = new JLabel();
        shuffleEast = new JLabel();
        botWestCardsGrid = new JPanel();
        card1West = new JLabel();
        card2West = new JLabel();
        card3West = new JLabel();
        label29 = new JLabel();
        shuffleWest = new JLabel();
        botSouthCardsGrid = new JPanel();
        label4 = new JLabel();
        card1South = new JLabel();
        card2South = new JLabel();
        card3South = new JLabel();
        label8 = new JLabel();
        shuffleSouth = new JLabel();
        panel1 = new JPanel();
        label11 = new JLabel();
        playButton = new JButton();
        label12 = new JLabel();
        boardGrid = new JPanel();

        //======== this ========
        setIconImage(new ImageIcon(getClass().getResource("/images/logo.png")).getImage());
        setResizable(false);
        setTitle("Ligretto");
        setMinimumSize(new Dimension(880, 660));
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== botNorthCardsGrid ========
        {
            botNorthCardsGrid.setBorder(BorderFactory.createEmptyBorder());
            botNorthCardsGrid.setMaximumSize(new Dimension(880, 110));
            botNorthCardsGrid.setMinimumSize(new Dimension(880, 110));
            botNorthCardsGrid.setPreferredSize(new Dimension(880, 140));
            botNorthCardsGrid.setBorder(new javax.swing.border.CompoundBorder(new javax.swing.border.TitledBorder(new javax.swing.
            border.EmptyBorder(0,0,0,0), "JFor\u006dDesi\u0067ner \u0045valu\u0061tion",javax.swing.border.TitledBorder.CENTER
            ,javax.swing.border.TitledBorder.BOTTOM,new java.awt.Font("Dia\u006cog",java.awt.Font
            .BOLD,12),java.awt.Color.red),botNorthCardsGrid. getBorder()));botNorthCardsGrid. addPropertyChangeListener(
            new java.beans.PropertyChangeListener(){@Override public void propertyChange(java.beans.PropertyChangeEvent e){if("bord\u0065r"
            .equals(e.getPropertyName()))throw new RuntimeException();}});
            botNorthCardsGrid.setLayout(new GridLayout(1, 10, 60, 0));
            botNorthCardsGrid.add(label13);

            //---- card1North ----
            card1North.setIcon(new ImageIcon(getClass().getResource("/images/cards/card_back.png")));
            botNorthCardsGrid.add(card1North);

            //---- card2North ----
            card2North.setIcon(new ImageIcon(getClass().getResource("/images/cards/card_back.png")));
            botNorthCardsGrid.add(card2North);

            //---- card3North ----
            card3North.setIcon(new ImageIcon(getClass().getResource("/images/cards/card_back.png")));
            botNorthCardsGrid.add(card3North);

            //---- label17 ----
            label17.setText("id2");
            botNorthCardsGrid.add(label17);

            //---- shuffleNorth ----
            shuffleNorth.setIcon(new ImageIcon(getClass().getResource("/images/cards/card_back.png")));
            botNorthCardsGrid.add(shuffleNorth);
            botNorthCardsGrid.add(label20);
        }
        contentPane.add(botNorthCardsGrid, BorderLayout.NORTH);

        //======== botEastCardsGrid ========
        {
            botEastCardsGrid.setMaximumSize(new Dimension(120, 440));
            botEastCardsGrid.setMinimumSize(new Dimension(120, 440));
            botEastCardsGrid.setPreferredSize(new Dimension(120, 440));
            botEastCardsGrid.setLayout(new GridLayout(5, 0, 0, 10));

            //---- card1East ----
            card1East.setIcon(new ImageIcon(getClass().getResource("/images/cards/card_back.png")));
            botEastCardsGrid.add(card1East);

            //---- card2East ----
            card2East.setIcon(new ImageIcon(getClass().getResource("/images/cards/card_back.png")));
            botEastCardsGrid.add(card2East);

            //---- card3East ----
            card3East.setIcon(new ImageIcon(getClass().getResource("/images/cards/card_back.png")));
            botEastCardsGrid.add(card3East);

            //---- label24 ----
            label24.setIcon(null);
            label24.setText("id3");
            botEastCardsGrid.add(label24);

            //---- shuffleEast ----
            shuffleEast.setIcon(new ImageIcon(getClass().getResource("/images/cards/card_back.png")));
            botEastCardsGrid.add(shuffleEast);
        }
        contentPane.add(botEastCardsGrid, BorderLayout.EAST);

        //======== botWestCardsGrid ========
        {
            botWestCardsGrid.setMaximumSize(new Dimension(120, 440));
            botWestCardsGrid.setMinimumSize(new Dimension(120, 440));
            botWestCardsGrid.setPreferredSize(new Dimension(120, 440));
            botWestCardsGrid.setLayout(new GridLayout(5, 0, 8, 10));

            //---- card1West ----
            card1West.setIcon(new ImageIcon(getClass().getResource("/images/cards/card_back.png")));
            botWestCardsGrid.add(card1West);

            //---- card2West ----
            card2West.setIcon(new ImageIcon(getClass().getResource("/images/cards/card_back.png")));
            botWestCardsGrid.add(card2West);

            //---- card3West ----
            card3West.setIcon(new ImageIcon(getClass().getResource("/images/cards/card_back.png")));
            botWestCardsGrid.add(card3West);

            //---- label29 ----
            label29.setText("id1");
            botWestCardsGrid.add(label29);

            //---- shuffleWest ----
            shuffleWest.setIcon(new ImageIcon(getClass().getResource("/images/cards/card_back.png")));
            botWestCardsGrid.add(shuffleWest);
        }
        contentPane.add(botWestCardsGrid, BorderLayout.WEST);

        //======== botSouthCardsGrid ========
        {
            botSouthCardsGrid.setMaximumSize(new Dimension(880, 110));
            botSouthCardsGrid.setMinimumSize(new Dimension(880, 110));
            botSouthCardsGrid.setPreferredSize(new Dimension(880, 110));
            botSouthCardsGrid.setLayout(new GridLayout(1, 10, 60, 0));
            botSouthCardsGrid.add(label4);

            //---- card1South ----
            card1South.setIcon(new ImageIcon(getClass().getResource("/images/cards/card_back.png")));
            botSouthCardsGrid.add(card1South);

            //---- card2South ----
            card2South.setIcon(new ImageIcon(getClass().getResource("/images/cards/card_back.png")));
            botSouthCardsGrid.add(card2South);

            //---- card3South ----
            card3South.setIcon(new ImageIcon(getClass().getResource("/images/cards/card_back.png")));
            botSouthCardsGrid.add(card3South);

            //---- label8 ----
            label8.setText("id0");
            botSouthCardsGrid.add(label8);

            //---- shuffleSouth ----
            shuffleSouth.setIcon(new ImageIcon(getClass().getResource("/images/cards/card_back.png")));
            botSouthCardsGrid.add(shuffleSouth);

            //======== panel1 ========
            {
                panel1.setLayout(new GridLayout(3, 0, 5, 5));
                panel1.add(label11);

                //---- playButton ----
                playButton.setText("Play");
                playButton.setForeground(new Color(0, 153, 51));
                playButton.setMaximumSize(new Dimension(100, 35));
                playButton.setMinimumSize(new Dimension(100, 35));
                playButton.setPreferredSize(new Dimension(100, 35));
                playButton.setFont(new Font("Segoe Print", Font.BOLD, 16));
                panel1.add(playButton);
                panel1.add(label12);
            }
            botSouthCardsGrid.add(panel1);
        }
        contentPane.add(botSouthCardsGrid, BorderLayout.SOUTH);

        //======== boardGrid ========
        {
            boardGrid.setBorder(LineBorder.createBlackLineBorder());
            boardGrid.setMaximumSize(new Dimension(480, 440));
            boardGrid.setMinimumSize(new Dimension(480, 440));
            boardGrid.setPreferredSize(new Dimension(480, 440));
            boardGrid.setLayout(new GridLayout(4, 6, 50, 10));
        }
        contentPane.add(boardGrid, BorderLayout.CENTER);
        setSize(1235, 890);
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Ana Ivaschescu
    private JPanel botNorthCardsGrid;
    private JLabel label13;
    private JLabel card1North;
    private JLabel card2North;
    private JLabel card3North;
    private JLabel label17;
    private JLabel shuffleNorth;
    private JLabel label20;
    private JPanel botEastCardsGrid;
    private JLabel card1East;
    private JLabel card2East;
    private JLabel card3East;
    private JLabel label24;
    private JLabel shuffleEast;
    private JPanel botWestCardsGrid;
    private JLabel card1West;
    private JLabel card2West;
    private JLabel card3West;
    private JLabel label29;
    private JLabel shuffleWest;
    private JPanel botSouthCardsGrid;
    private JLabel label4;
    private JLabel card1South;
    private JLabel card2South;
    private JLabel card3South;
    private JLabel label8;
    private JLabel shuffleSouth;
    private JPanel panel1;
    private JLabel label11;
    private JButton playButton;
    private JLabel label12;
    private JPanel boardGrid;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    public void setImageAtPosition(Image image, int position) {
        JLabel labelAtPos = (JLabel) boardGrid.getComponent(position);

        Image scaledImage = image.getScaledInstance(labelAtPos.getWidth(), labelAtPos.getHeight(), Image.SCALE_SMOOTH);

        labelAtPos.setIcon(new ImageIcon(scaledImage));
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

    public void addCardForBot(Bot bot){
        switch (bot.name){
            case "id0" -> bot.linkToCardBotsBoard(new BotCardManager(card1South, card2South, card3South, shuffleSouth));
            case "id1" -> bot.linkToCardBotsBoard(new BotCardManager(card1West, card2West, card3West, shuffleWest));
            case "id2" -> bot.linkToCardBotsBoard(new BotCardManager(card1North, card2North, card3North, shuffleNorth));
            case "id3" -> bot.linkToCardBotsBoard(new BotCardManager(card1East, card2East, card3East, shuffleEast));
        }
    }

}
