package gui;

import core.card.Card;
import core.card.CardColour;
import core.card.CardNumber;
import utils.CardsLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class BotCardsPanel extends JPanel {

    private final JLabel card1Label;
    private final JLabel card2Label;
    private final JLabel card3Label;

    public BotCardsPanel(String botName) {
        setLayout(new GridLayout(1, 4, 5, 5));
        setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 10));


        JLabel nameLabel = new JLabel(botName);
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(nameLabel);

        card1Label = new JLabel();
        card2Label = new JLabel();
        card3Label = new JLabel();

        add(card1Label);
        add(card2Label);
        add(card3Label);
    }

    public void setCard1(CardColour colour, CardNumber number) {
        BufferedImage image = CardsLoader.getInstance().getCard(colour, number);
        Image scaledImage = image.getScaledInstance(3 * image.getWidth() / 4, 3 * image.getHeight() / 4, Image.SCALE_SMOOTH);
        card1Label.setIcon(new ImageIcon(scaledImage));
    }

    public void setCard2(CardColour colour, CardNumber number) {
        BufferedImage image = CardsLoader.getInstance().getCard(colour, number);
        Image scaledImage = image.getScaledInstance(3 * image.getWidth() / 4, 3 * image.getHeight() / 4, Image.SCALE_SMOOTH);
        card2Label.setIcon(new ImageIcon(scaledImage));
    }

    public void setCard3(CardColour colour, CardNumber number) {
        BufferedImage image = CardsLoader.getInstance().getCard(colour, number);
        Image scaledImage = image.getScaledInstance(3 * image.getWidth() / 4, 3 * image.getHeight() / 4, Image.SCALE_SMOOTH);
        card3Label.setIcon(new ImageIcon(scaledImage));
    }

    public void setCardAtPosition(int position, Card card) {
        switch (position) {
            case 1 -> setCard1(card.colour(), card.number());
            case 2 -> setCard2(card.colour(), card.number());
            case 3 -> setCard3(card.colour(), card.number());
        }
    }
}
