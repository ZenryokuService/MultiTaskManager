package zenryokuservice.gui.lwjgl.tutoriral.gitbook.chapter6.game;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;

import zenryokuservice.gui.lwjgl.tutoriral.gitbook.chapter6.engine.GameItem;
import zenryokuservice.gui.lwjgl.tutoriral.gitbook.chapter6.engine.IGameLogic;
import zenryokuservice.gui.lwjgl.tutoriral.gitbook.chapter6.engine.Window;
import zenryokuservice.gui.lwjgl.tutoriral.gitbook.chapter6.engine.graph.Mesh;

public class DummyGame implements IGameLogic {

    private int direction = 0;

    private float color = 0.0f;

    private final Renderer renderer;
    
    private Mesh mesh;
    
    private GameItem[] gameItems;
    
    public DummyGame() {
        renderer = new Renderer();
    }
    
    @Override
    public void init(Window window) throws Exception {
    	// 初期化
        renderer.init(window);
        // 四角形の頂点定義
        float[] positions = new float[]{
            -0.5f,  0.5f, -2.5f,
            -0.5f, -0.5f, -2.5f,
             0.5f, -0.5f, -2.5f,
             0.5f,  0.5f, -2.5f,
        };
        float[] colours = new float[]{
            0.5f, 0.0f, 0.0f,
            0.0f, 0.5f, 0.0f,
            0.0f, 0.0f, 0.5f,
            0.0f, 0.5f, 0.5f,
        };
        int[] indices = new int[]{
            0, 1, 3, 3, 1, 2,
        };
        // 四角形(Mesh)
        mesh = new Mesh(positions, colours, indices);

        // GameItemの作成
        gameItems = new GameItem[] {new GameItem(mesh)};
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
        renderer.render(window, mesh, gameItems);
    }

    @Override
    public void cleanup() {
        renderer.cleanup();
        mesh.cleanUp();
    }

}