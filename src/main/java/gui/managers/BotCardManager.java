package gui.managers;

import core.card.Card;
import core.card.CardColour;
import core.card.CardNumber;
import utils.CardsLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class BotCardManager {
    private JLabel card1;
    private JLabel card2;
    private JLabel card3;
    private JLabel shuffle;

    public BotCardManager(JLabel card1, JLabel card2, JLabel card3, JLabel shuffle) {
        this.card1 = card1;
        this.card2 = card2;
        this.card3 = card3;
        this.shuffle = shuffle;
    }

    public void setCardAtPosition(int position, Card card) {
        switch (position) {
            case 1 -> setCard1(card.colour(), card.number());
            case 2 -> setCard2(card.colour(), card.number());
            case 3 -> setCard3(card.colour(), card.number());
        }
    }

    public void setCard1(CardColour colour, CardNumber number) {
        BufferedImage image = CardsLoader.getInstance().getCard(colour, number);
        Image scaledImage = image.getScaledInstance(card1.getWidth(), card1.getHeight(), Image.SCALE_SMOOTH);
        card1.setIcon(new ImageIcon(scaledImage));
    }

    public void setCard2(CardColour colour, CardNumber number) {
        BufferedImage image = CardsLoader.getInstance().getCard(colour, number);
        Image scaledImage = image.getScaledInstance(card2.getWidth(), card2.getHeight(), Image.SCALE_SMOOTH);
        card2.setIcon(new ImageIcon(scaledImage));
    }

    public void setCard3(CardColour colour, CardNumber number) {
        BufferedImage image = CardsLoader.getInstance().getCard(colour, number);
        Image scaledImage = image.getScaledInstance(card3.getWidth(), card3.getHeight(), Image.SCALE_SMOOTH);
        card3.setIcon(new ImageIcon(scaledImage));
    }

    public void setShuffle(Card card ) {
        CardColour colour = card.colour();
        CardNumber number = card.number();
        BufferedImage image = CardsLoader.getInstance().getCard(colour, number);
        Image scaledImage = image.getScaledInstance(shuffle.getWidth(), shuffle.getHeight(), Image.SCALE_SMOOTH);
        shuffle.setIcon(new ImageIcon(scaledImage));
    }
}
