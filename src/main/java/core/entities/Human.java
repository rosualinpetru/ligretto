package core.entities;

import core.card.Card;
import core.exception.IllegalTableCallError;
import gui.BoardFrame;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Human extends Player {
    private BoardFrame boardFrame;
    private int currentCardIndex;

    public Human(String name, BoardFrame boardFrame) {
        super(name);
        this.boardFrame = boardFrame;

        facedUpCards.peek(1).ifPresent(card -> boardFrame.setCard1(card.colour(), card.number()));
        facedUpCards.peek(2).ifPresent(card -> boardFrame.setCard2(card.colour(), card.number()));
        facedUpCards.peek(3).ifPresent(card -> boardFrame.setCard3(card.colour(), card.number()));
        shufflingDeck.peek().ifPresent(card -> boardFrame.setShuffle(card.colour(), card.number()));

        boardFrame.setCard1ClickEventListener(this::card1OnClick);

        boardFrame.setCard2ClickEventListener(this::card2OnClick);

        boardFrame.setCard3ClickEventListener(this::card3OnClick);

        boardFrame.setShuffleClickEventListener(this::shuffleDeckOnClick);

        boardFrame.setDeckClickEventListener(this::tableDeckOnClick);

        boardFrame.setPauseButtonClickEventListener(this::pauseOnClick);

        boardFrame.setResumeButtonClickEventListener(this::resumeOnClick);

        boardFrame.setFocusable(true);
        boardFrame.requestFocusInWindow();

        boardFrame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    updateShuffleDeckUI();
                }
            }
        });
    }

    @Override
    public void run() {
        do {
            try {
                while (table.getState() != TableState.ENDED && !Thread.interrupted()) {
                    if (table.getState() == TableState.PAUSED) {
                        table.pause();
                    }
                }
            } catch (IllegalTableCallError ignored) {
            }
        } while (table.getState() == TableState.PAUSED);

//        table.pauseGamePhaser.arriveAndDeregister();
        System.out.println(this.name + " - Finished Deck cards");
    }

    @Override
    public String toString() {
        return name + "{" +
                "facedUpCards=" + facedUpCards +
                ", targetDeck=" + targetDeck +
                ", shufflingDeck=" + shufflingDeck +
                '}';
    }

    private void updateShuffleDeckUI() {
        if (!shufflingDeck.isEmpty()) {

            shufflingDeck.shuffle();

            var cardOpt = shufflingDeck.peek();

            if (cardOpt.isPresent()) {
                Card card = cardOpt.get();
                boardFrame.setShuffle(card.colour(), card.number());
                return;
            }
        }
        boardFrame.setShuffleCardBack();
    }

    private void updateFacedUpCards(int position) {
        var newCard = targetDeck.pop();
        newCard.ifPresent(card -> {
            facedUpCards.put(position, card);

            switch (position) {
                case 1 -> boardFrame.setCard1(card.colour(), card.number());
                case 2 -> boardFrame.setCard2(card.colour(), card.number());
                case 3 -> boardFrame.setCard3(card.colour(), card.number());
            }
        });

        if (targetDeck.isEmpty()) {
            table.end(this);
        }
    }

    private void card1OnClick(MouseEvent mouseEvent) {
        currentCardIndex = 1;
    }

    private void card2OnClick(MouseEvent mouseEvent) {
        currentCardIndex = 2;
    }

    private void card3OnClick(MouseEvent mouseEvent) {
        currentCardIndex = 3;
    }

    private void shuffleDeckOnClick(MouseEvent mouseEvent) {
        currentCardIndex = 4;
    }

    private void tableDeckOnClick(Integer position, MouseEvent mouseEvent) {
        if (currentCardIndex < 1 || currentCardIndex > 4) {
            currentCardIndex = 0;
            return;
        }

        if (currentCardIndex == 4) {
            var cardOpt = shufflingDeck.pick();
            if (cardOpt.isEmpty()) {
                currentCardIndex = 0;
                return;
            }

            var card = cardOpt.get();
            if (card.number().isFirst()) {
                table.newDeck(card);
                updateShuffleDeckUI();
            } else {
                if (table.fitsPosition(card, position)) {
                    var successfullyPlacedCard = table.put(card, position);
                    if (!successfullyPlacedCard) {
                        shufflingDeck.put(card);
                    } else {
                        updateShuffleDeckUI();
                    }
                } else {
                    shufflingDeck.put(card);
                }
            }

            currentCardIndex = 0;
            return;
        }

        var cardOpt = facedUpCards.pick(currentCardIndex);
        if (cardOpt.isEmpty()) {
            currentCardIndex = 0;
            return;
        }

        var card = cardOpt.get();
        if (card.number().isFirst()) {
            table.newDeck(card);
            updateFacedUpCards(currentCardIndex);
        } else {
            if (table.fitsPosition(card, position)) {
                var successfullyPlacedCard = table.put(card, position);
                if (!successfullyPlacedCard) {
                    facedUpCards.put(currentCardIndex, card);
                } else {
                    updateFacedUpCards(currentCardIndex);
                }
            } else {
                facedUpCards.put(currentCardIndex, card);
            }
        }

        currentCardIndex = 0;
    }

    private void resumeOnClick(MouseEvent mouseEvent) {

    }

    private void pauseOnClick(MouseEvent mouseEvent) {

    }
}
