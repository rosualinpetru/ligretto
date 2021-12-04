package main;

import gui.StartFrame;
import gui.managers.FrameManager;

public class Main {
    public static final String ONLY_BOTS_PLAYING = "Only bots playing";
    public static final String PLAY_WITH_BOTS = "Play with bots";

    public static void main(String[] args) throws InterruptedException {

        StartFrame startFrame = new StartFrame();

        FrameManager frameManager = FrameManager.getInstance();

        while (true) {
            frameManager.semaphore.acquire();

            frameManager.startTable();
        }
    }
}
