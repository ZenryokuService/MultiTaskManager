package zenryokuservice.gui.lwjgl.tutoriral.gitbook.chapter2.engine;

public interface IGameLogic {

    void init() throws Exception;

    void input(Window window);

    void update(float interval);
    
    void render(Window window);
}