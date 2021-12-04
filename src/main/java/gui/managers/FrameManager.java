package gui.managers;

import core.entities.Bot;
import core.entities.Table;
import gui.BoardFrame;
import gui.BotCardsFrame;
import gui.EndFrame;
import gui.GameSettingsFrame;
import main.Main;

import javax.swing.*;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class FrameManager {

    private final static FrameManager instance = new FrameManager();
    private JFrame currentFrame;
    private Table table;
    private BotCardsFrame botCardsFrame;
    private String humanPlayerName;
    private int botNumber = 3;
    private boolean withHumanPlayer = false;

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
            String gameMode = gameSettingsFrame.getComboBoxSelectedItem();
            if (gameMode.equals(Main.PLAY_WITH_BOTS)) {
                withHumanPlayer = true;
            }

            botNumber = gameSettingsFrame.getNumberOfSelectedBots();

            humanPlayerName = gameSettingsFrame.getNameFieldContent();
            if (humanPlayerName == null || humanPlayerName.isEmpty()) {
                humanPlayerName = "Player";
            }

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

        boardFrame.setPlayButtonClickEventListener(e -> {
            BoardManager boardManager = new BoardManager(boardFrame);
            table = new Table(boardManager);

            botCardsFrame = new BotCardsFrame();

            for (int i = 0; i < botNumber; i++) {
                Bot bot = new Bot("id" + i, 500L);
                botCardsFrame.addBotCard(bot);

                table.register(bot);
            }

//            if (withHumanPlayer) {
//                table.register(new Human(humanPlayerName, boardFrame));
//            }

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
