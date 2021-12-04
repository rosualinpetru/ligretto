package gui.managers;

import core.card.Card;
import gui.BoardFrame;
import utils.CardsLoader;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class BoardManager {

    private final BoardFrame boardFrame;

    public BoardManager(BoardFrame boardFrame) {
        this.boardFrame = boardFrame;
    }

    public void putCardAtPosition(Card card, int position) {
        BufferedImage image = CardsLoader.getInstance().getCard(card.colour(), card.number());
        boardFrame.setImageAtPosition(image, position - 1);
    }

    public void showMessageDialog(String s) {
        JOptionPane.showMessageDialog(boardFrame,
                s,
                "Game Over",
                JOptionPane.PLAIN_MESSAGE);
    }
}
