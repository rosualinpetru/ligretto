package main;

import core.entities.Bot;
import core.entities.Table;
import gui.Board;
import gui.managers.BoardManager;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Main {
    public static void main(String[] args) throws InterruptedException {

//        EndFrame endFrame = new EndFrame();
        Board board = new Board();
        board.addLabels();

        board.setCard1(getBufferedImage("BLUE_TEN"));
        board.setCard2(getBufferedImage("BLUE_TEN"));
        board.setCard3(getBufferedImage("BLUE_TEN"));
        board.setShuffle(getBufferedImage("BLUE_TEN"));

        board.setCard1ClickEventListener(e -> {
            System.out.println("Card1 was clicked");
        });

        board.setCard2ClickEventListener(e -> {
            System.out.println("Card2 was clicked");
        });

        board.setCard3ClickEventListener(e -> {
            System.out.println("Card3 was clicked");
        });

        board.setShuffleClickEventListener(e -> {
            System.out.println("Shuffle was clicked");
        });

        board.setDeckClickEventListener((position, e) ->
        {
            System.out.println("Deck" + position + " was clicked");
        });

        BoardManager boardManager = new BoardManager(board);

        var table = new Table(boardManager);

        int NR_OF_PLAYERS = 3;
        for (int i = 0; i < NR_OF_PLAYERS; i++) {
            table.register(new Bot("id" + i, 1000L));
        }

        table.start();
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
