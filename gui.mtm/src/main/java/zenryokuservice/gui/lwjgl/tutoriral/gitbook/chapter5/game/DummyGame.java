package zenryokuservice.gui.lwjgl.tutoriral.gitbook.chapter5.game;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;
import zenryokuservice.gui.lwjgl.tutoriral.gitbook.chapter5.engine.IGameLogic;
import zenryokuservice.gui.lwjgl.tutoriral.gitbook.chapter5.engine.Window;
import zenryokuservice.gui.lwjgl.tutoriral.gitbook.chapter5.engine.graph.GameItem;
import zenryokuservice.gui.lwjgl.tutoriral.gitbook.chapter5.engine.graph.Mesh;

public class DummyGame implements IGameLogic {

    private int direction = 0;

    private float color = 0.0f;

    private final Renderer renderer;

    private GameItem[] gameItems;

    public DummyGame() {
        renderer = new Renderer();
    }

    @Override
    public void init() throws Exception {
        renderer.init();
        gameItems = new GameItem[] {createFloor()};
    }

    /**
     * 床のような土台のメッシュ(3Dモデル)を作成します
     * @return GameItem 床型のメッシュ
     */
    private GameItem createFloor() {
        float[] positions = new float[]{
            	// V0
                0.0f, 0.25f, 0.0f,
                // V1
                -0.0f, -0.0f, 0.0f,
                // V2
                0.5f, -0.0f, 0.0f,
                // V3
                0.5f, 0.25f, 0.0f,};
            int[] indices = new int[]{
                0, 1, 3, 3, 1, 2,};
            // 追記　2018/10/27
            float[] colors = new float[] {
            	    0.5f, 0.0f, 0.0f,
            	    0.0f, 0.5f, 0.0f,
            	    0.0f, 0.0f, 0.5f,
            	    0.8f, 0.8f, 0.0f,};
            return new GameItem(new Mesh(positions, indices, colors));
    }

    
    @Override
    public void input(Window window) {
        if (window.isKeyPressed(GLFW_KEY_UP)) {
            direction = 1;
        } else if (window.isKeyPressed(GLFW_KEY_DOWN)) {
            direction = -1;
        } else {
            direction = 0;
        }
    }

    @Override
    public void update(float interval) {
        color += direction * 0.01f;
        if (color > 1) {
            color = 1.0f;
        } else if (color < 0) {
            color = 0.0f;
        }
    }

    @Override
    public void render(Window window) {
        window.setClearColor(color, color, color, 0.0f);
        renderer.render(window, gameItems);
    }

    @Override
    public void cleanup() {
        renderer.cleanup();
        for(GameItem item : gameItems) {
        	item.getMesh().cleanUp();
        }
    }

}
