package zenryokuservice.gui.lwjgl.tutoriral.gitbook.chapter5.game;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import zenryokuservice.gui.lwjgl.tutoriral.gitbook.chapter5.engine.Utils;
import zenryokuservice.gui.lwjgl.tutoriral.gitbook.chapter5.engine.Window;
import zenryokuservice.gui.lwjgl.tutoriral.gitbook.chapter5.engine.graph.GameItem;
import zenryokuservice.gui.lwjgl.tutoriral.gitbook.chapter5.engine.graph.Mesh;
import zenryokuservice.gui.lwjgl.tutoriral.gitbook.chapter5.engine.graph.ShaderProgram;

public class Renderer {

    private ShaderProgram shaderProgram;

    public Renderer() {
    }

    public void init() throws Exception {
        shaderProgram = new ShaderProgram();
        shaderProgram.createVertexShader(Utils.loadResource("/vertex5.vs"));
        shaderProgram.createFragmentShader(Utils.loadResource("/fragment5.fs"));
        shaderProgram.link();
    }

    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public void render(Window window, GameItem[] gameItems) {
        clear();

        if (window.isResized()) {
            glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResized(false);
        }

        shaderProgram.bind();
        int i = -1;
        for (GameItem item : gameItems) {
            // Draw the mesh
            glBindVertexArray(item.getMesh().getVaoId());
            glEnableVertexAttribArray(++i);
            // 追記 2018/10/27
            glEnableVertexAttribArray(++i);
            glDrawElements(GL_TRIANGLES, item.getMesh().getVertexCount(), GL_UNSIGNED_INT, 0);
        }

        // Restore state
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);

        shaderProgram.unbind();
    }

    public void cleanup() {
        if (shaderProgram != null) {
            shaderProgram.cleanup();
        }
    }
}
