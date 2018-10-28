package zenryokuservice.gui.lwjgl.tutoriral.gitbook.chapter5.engine;

public interface IGameLogic {

    void init() throws Exception;

    void input(Window window);

    void update(float interval);

    void render(Window window);

    void cleanup();
}
