package zenryokuservice.gui.lwjgl.tutoriral.gitbook.chapter5.game;

import org.lwjgl.opengl.GLUtil;

import zenryokuservice.gui.lwjgl.tutoriral.gitbook.chapter5.engine.GameEngine;
import zenryokuservice.gui.lwjgl.tutoriral.gitbook.chapter5.engine.IGameLogic;

public class Main {

    public static void main(String[] args) {
        try {
            boolean vSync = true;
            IGameLogic gameLogic = new DummyGame();
            GameEngine gameEng = new GameEngine("GAME", 600, 480, vSync, gameLogic);
            gameEng.start();
        } catch (Exception excp) {
            excp.printStackTrace();
            System.exit(-1);
        }
    }
}
