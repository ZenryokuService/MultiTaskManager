package zenryokuservice.gui.lwjgl.tutoriral.gitbook.chapter5.game;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import org.joml.Matrix4f;

import zenryokuservice.gui.lwjgl.tutoriral.gitbook.chapter5.engine.Utils;
import zenryokuservice.gui.lwjgl.tutoriral.gitbook.chapter5.engine.Window;
import zenryokuservice.gui.lwjgl.tutoriral.gitbook.chapter5.engine.graph.GameItem;
import zenryokuservice.gui.lwjgl.tutoriral.gitbook.chapter5.engine.graph.Mesh;
import zenryokuservice.gui.lwjgl.tutoriral.gitbook.chapter5.engine.graph.ShaderProgram;
import zenryokuservice.gui.lwjgl.tutoriral.gitbook.chapter5.engine.graph.Transformation;

public class Renderer {

    private ShaderProgram shaderProgram;
    
    // 2018/11/08
    private Transformation transformation;

    public Renderer() {
    	transformation = new Transformation();
    }

    public void init() throws Exception {
        shaderProgram = new ShaderProgram();
        shaderProgram.createVertexShader(Utils.loadResource("/vertex5.vs"));
        shaderProgram.createFragmentShader(Utils.loadResource("/fragment5.fs"));
        shaderProgram.link();
        
        // 2018/11/08追記
        shaderProgram.createUnitform("projectionMatrix");
        shaderProgram.createUnitform("worldMatrix");
        shaderProgram.createUnitform("texture_sampler");
    }

    public void clear() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }

    public void render(Window window, GameItem[] gameItems) {
        clear();

        if (window.isResized()) {
            GL11.glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResized(false);
        }

        shaderProgram.bind();
        int i = -1;
        for (GameItem item : gameItems) {
// Meshクラスに処理を移動
//            // Draw the mesh
//            glBindVertexArray(item.getMesh().getVaoId());
//            glEnableVertexAttribArray(++i);
//            // 追記 2018/10/27
//            glEnableVertexAttribArray(++i);
//            glDrawElements(GL_TRIANGLES, item.getMesh().getVertexCount(), GL_UNSIGNED_INT, 0);
        	// ワールド座標を設定する
        	Matrix4f worldMatrix = transformation.getWorldMatrix(item.getPosition(), item.getRotation(), item.getScale());
        	shaderProgram.setUniform("worldMatrix", worldMatrix);
        	item.getMesh().render();
        }

        // Restore state
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);

        shaderProgram.unbind();
    }

    public void cleanup() {
        if (shaderProgram != null) {
            shaderProgram.cleanup();
        }
    }
}
