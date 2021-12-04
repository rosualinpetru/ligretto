package gui.managers;

import core.card.CardColour;
import core.card.CardNumber;
import core.entities.Bot;
import core.entities.Table;
import gui.BoardFrame;
import gui.BotCardsFrame;
import gui.EndFrame;
import gui.GameSettingsFrame;
import utils.CardsLoader;

import javax.swing.*;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class FrameManager {

    private final static FrameManager instance = new FrameManager();
    private JFrame currentFrame;
    private Table table;
    private BotCardsFrame botCardsFrame;

    public Semaphore semaphore = new Semaphore(0);

    private FrameManager() {
    }

    public static FrameManager getInstance() {
        return instance;
    }

    public void setCurrentFrame(JFrame frame) {
        currentFrame = frame;

        currentFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
            }
        });
    }

    public void switchToGameSettingsFrame() {
        GameSettingsFrame gameSettingsFrame = new GameSettingsFrame();
        gameSettingsFrame.setLocationRelativeTo(currentFrame);

        gameSettingsFrame.setStartButtonClickEventListener(event -> {
            switchToBoardFrame();
        });

        gameSettingsFrame.setVisible(true);
        currentFrame.dispose();
        setCurrentFrame(gameSettingsFrame);
    }

    public void switchToBoardFrame() {
        BoardFrame boardFrame = new BoardFrame();
        boardFrame.setLocationRelativeTo(currentFrame);
        boardFrame.addLabels();

        CardsLoader cardsLoader = CardsLoader.getInstance();

        boardFrame.setCard1(cardsLoader.getCard(CardColour.BLUE, CardNumber.TEN));
        boardFrame.setCard2(cardsLoader.getCard(CardColour.BLUE, CardNumber.TEN));
        boardFrame.setCard3(cardsLoader.getCard(CardColour.BLUE, CardNumber.TEN));
        boardFrame.setShuffle(cardsLoader.getCard(CardColour.BLUE, CardNumber.TEN));

        boardFrame.setCard1ClickEventListener(e -> {
            System.out.println("Card1 was clicked");
        });

        boardFrame.setCard2ClickEventListener(e -> {
            System.out.println("Card2 was clicked");
        });

        boardFrame.setCard3ClickEventListener(e -> {
            System.out.println("Card3 was clicked");
        });

        boardFrame.setShuffleClickEventListener(e -> {
            System.out.println("Shuffle was clicked");
        });

        boardFrame.setDeckClickEventListener((position, e) -> {
            System.out.println("Deck" + position + " was clicked");
        });

        boardFrame.setPauseButtonClickEventListener(e -> {
        });

        boardFrame.setPlayButtonClickEventListener(e -> {
            BoardManager boardManager = new BoardManager(boardFrame);
            table = new Table(boardManager);

            botCardsFrame = new BotCardsFrame();

            int NR_OF_PLAYERS = 4;
            for (int i = 0; i < NR_OF_PLAYERS; i++) {
                Bot bot = new Bot("id" + i, 500L);
                botCardsFrame.addBotCard(bot);

                table.register(bot);
            }

            semaphore.release();
        });

        boardFrame.setVisible(true);
        currentFrame.dispose();
        setCurrentFrame(boardFrame);
    }

    public void switchToEndFrame(ArrayList<String> data) {
        if (botCardsFrame != null) {
            botCardsFrame.dispose();
            botCardsFrame = null;
        }

        EndFrame endFrame = new EndFrame(data);
        endFrame.setLocationRelativeTo(currentFrame);

        endFrame.setNextRoundButtonClickEventListener(event -> {
            switchToBoardFrame();
        });

        endFrame.setEndGameButtonClickEventListener(event -> {
            System.exit(0);
        });

        endFrame.setVisible(true);
        currentFrame.dispose();
        setCurrentFrame(endFrame);
    }

    public void startTable() {
        if (table == null) {
            return;
        }
        try {
            table.start();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
