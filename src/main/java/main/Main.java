package main;

import gui.StartFrame;
import gui.managers.FrameManager;
import utils.CardsLoader;

public class Main {
    public static final String ONLY_BOTS_PLAYING = "Only bots playing";
    public static final String PLAY_WITH_BOTS = "Play with bots";
    public static final String EASY = "Easy";
    public static final String MEDIUM = "Medium";
    public static final String HARD = "Hard";

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
