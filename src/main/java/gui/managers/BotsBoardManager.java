package gui.managers;

import core.card.Card;
import gui.BotsBoardFrame;
import utils.CardsLoader;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class BotsBoardManager implements InterfBoardManager{
    private final BotsBoardFrame botsBoardFrame;

    public BotsBoardManager(BotsBoardFrame boardFrame) {
        this.botsBoardFrame = boardFrame;
    }

    public void putCardAtPosition(Card card, int position) {
        BufferedImage image = CardsLoader.getInstance().getCard(card.colour(), card.number());
        botsBoardFrame.setImageAtPosition(image, position - 1);
    }

    public void showMessageDialog(String s) {
        JOptionPane.showMessageDialog(botsBoardFrame,
                s,
                "Game Over",
                JOptionPane.PLAIN_MESSAGE);
    }
}
