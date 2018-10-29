package zenryokuservice.gui.lwjgl.tutoriral.gitbook.chapter7_1.game;

import zenryokuservice.gui.lwjgl.tutoriral.gitbook.chapter7_1.engine.GameEngine;
import zenryokuservice.gui.lwjgl.tutoriral.gitbook.chapter7_1.engine.IGameLogic;
 
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