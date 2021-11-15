package gui.managers;

import core.entities.Bot;
import core.entities.Table;
import gui.BoardFrame;
import gui.EndFrame;
import gui.GameSettingsFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.Semaphore;

public class FrameManager {

    private final static FrameManager instance = new FrameManager();
    private JFrame currentFrame;
    private Table table;

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

        boardFrame.setCard1(getBufferedImage("BLUE_TEN"));
        boardFrame.setCard2(getBufferedImage("BLUE_TEN"));
        boardFrame.setCard3(getBufferedImage("BLUE_TEN"));
        boardFrame.setShuffle(getBufferedImage("BLUE_TEN"));

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

            int NR_OF_PLAYERS = 5;
            for (int i = 0; i < NR_OF_PLAYERS; i++) {
                table.register(new Bot("id" + i, 100L));
            }

            semaphore.release();
        });

        boardFrame.setVisible(true);
        currentFrame.dispose();
        setCurrentFrame(boardFrame);
    }

    public void switchToEndFrame() {
        EndFrame endFrame = new EndFrame();
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
        System.out.println("ceva");
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
