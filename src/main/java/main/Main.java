package main;

import gui.StartFrame;
import gui.managers.BoardManager;
import gui.managers.FrameManager;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        StartFrame startFrame = new StartFrame();

        FrameManager frameManager = FrameManager.getInstance();
        frameManager.semaphore.acquire();

        frameManager.startTable();
    }
}
