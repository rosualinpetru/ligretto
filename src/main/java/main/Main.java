package main;

import gui.GameSettingsFrame;
import gui.managers.BoardManager;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Main {
    public static void main(String[] args) throws InterruptedException {

//        EndFrame endFrame = new EndFrame();

        GameSettingsFrame gameSettingsFrame = new GameSettingsFrame();
        gameSettingsFrame.addItemsToShapeComboBox();

        gameSettingsFrame.createRadioButtonGroup();

        gameSettingsFrame.setRadioButton3ClickEventListener(e -> {
            System.out.println("RadioButton3 was clicked");
        });

        gameSettingsFrame.setRadioButton4ClickEventListener(e -> {
            System.out.println("RadioButton4 was clicked");
        });

        gameSettingsFrame.setRadioButton5ClickEventListener(e -> {
            System.out.println("RadioButton5 was clicked");
        });

        gameSettingsFrame.setRadioButton6ClickEventListener(e -> {
            System.out.println("RadioButton6 was clicked");
        });

        gameSettingsFrame.setStartButtonClickEventListener(e -> {
            System.out.println("StartButton was clicked");
            System.out.println(gameSettingsFrame.getNamFieldContent());
            System.out.println(gameSettingsFrame.getComboBoxSelectedItem());
        });
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
