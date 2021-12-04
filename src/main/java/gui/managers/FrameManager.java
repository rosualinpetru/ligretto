package gui.managers;

import core.entities.Bot;
import core.entities.Table;
import gui.BoardFrame;
import gui.EndFrame;
import gui.GameSettingsFrame;
import main.Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.Semaphore;

public class FrameManager {

    private final static FrameManager instance = new FrameManager();
    private JFrame currentFrame;
    private Table table;
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

            for (int i = 0; i < botNumber; i++) {
                table.register(new Bot("id" + i, 100L));
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

    private static BufferedImage getBufferedImage(String path) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(Objects.requireNonNull(BoardManager.class.getResource("/images/cards/" + path + ".png")));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return img;
    }
}
