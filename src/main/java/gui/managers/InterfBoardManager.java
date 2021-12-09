package gui.managers;

import core.card.Card;

public interface InterfBoardManager {
    void putCardAtPosition(Card card, int position);
    void showMessageDialog(String s);
}
