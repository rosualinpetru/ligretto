package model.player;

import model.board.Board;
import model.card.Card;
import model.card.CardNumber;

public class Bot extends Player implements Runnable {

    public Bot(String id) {
        super(id);
    }

    @Override
    public void run() {
        Board board = new Board();
        System.out.println("Started Bot");
        while (true) {
            Card card = shufflingDeck.pick();
            System.out.println("Card: " + card);
            if (card.cardNumber == CardNumber.ONE) {
                System.out.println("Card placed!");
                board.startDeck(card);
            }
            else
                shufflingDeck.putBack(card);

            shufflingDeck.shuffle();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Bot("foo").run();
    }
}
