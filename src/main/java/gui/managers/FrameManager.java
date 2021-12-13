package gui.managers;

import core.entities.Bot;
import core.entities.Human;
import core.entities.Table;
import gui.*;
import main.Main;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class FrameManager {

    private final List<String> names = List.of("Siri", "Alexa", "Cortana", "Google", "Jarvis", "Cyd");
    private final static FrameManager instance = new FrameManager();
    private JFrame currentFrame;
    private Table table;
    private BotCardsFrame botCardsFrame;
    private BotCardManager botCardManager;
    private String humanPlayerName;
    private int botNumber = 4;
    private boolean withHumanPlayer = false;
    private long delay = 2000L;

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
            String gameMode = gameSettingsFrame.getGameModeComboBoxSelectedItem();
            if (gameMode.equals(Main.PLAY_WITH_BOTS)) {
                withHumanPlayer = true;
            }

            String difficulty = gameSettingsFrame.getDifficultyComboBoxSelectedItem();
            switch (difficulty) {
                case Main.EASY -> delay = 4500L;
                case Main.MEDIUM -> delay = 3000L;
                case Main.HARD -> delay = 1500L;
            }

            botNumber = gameSettingsFrame.getNumberOfSelectedBots();

            humanPlayerName = gameSettingsFrame.getNameFieldContent();
            if (humanPlayerName == null || humanPlayerName.isEmpty()) {
                humanPlayerName = "Player";
            }

            if(withHumanPlayer){
                switchToBoardFrame();
            }
            else{
                switchToBotsBoardFrame();
            }

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
            table = new Table(boardManager, withHumanPlayer);

            botCardsFrame = new BotCardsFrame();

            for (int i = 0; i < botNumber; i++) {
                Bot bot = new Bot(names.get(i), delay);
                botCardsFrame.addBotCard(bot);

                table.register(bot);
            }

            if (withHumanPlayer) {
                table.register(new Human(humanPlayerName, boardFrame));
            }

            boardFrame.setAlwaysOnTop(true);

            semaphore.release();
        });

        boardFrame.setVisible(true);
        currentFrame.dispose();
        setCurrentFrame(boardFrame);
    }

    public void switchToBotsBoardFrame() {
        BotsBoardFrame boardFrame = new BotsBoardFrame();
        boardFrame.setLocationRelativeTo(currentFrame);
        boardFrame.addLabels();

        boardFrame.setBackCards();

        boardFrame.setPlayButtonClickEventListener(e -> {
            BotsBoardManager botsBoardManager = new BotsBoardManager(boardFrame);
            table = new Table(botsBoardManager, false);

            Bot bot;
            for (int i = 0; i < 4; i++) {
                if((i+2)%2 == 0){
                    bot = new Bot(names.get(i), 1500L, true);
                }else{
                    bot = new Bot(names.get(i), 1500L, false);
                }

                boardFrame.addCardForBot(bot, i);


                table.register(bot);
            }

            boardFrame.setAlwaysOnTop(true);

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
            if(withHumanPlayer){
                switchToBoardFrame();
            }else{
                switchToBotsBoardFrame();
            }
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
