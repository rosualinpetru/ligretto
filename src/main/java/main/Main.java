package main;

import gui.StartFrame;
import gui.managers.FrameManager;
import utils.CardsLoader;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        CardsLoader.getInstance().loadImages();

        StartFrame startFrame = new StartFrame();

        FrameManager frameManager = FrameManager.getInstance();

        while (true) {
            frameManager.semaphore.acquire();

            frameManager.startTable();
        }
    }
}
