package zenryokuservice.gui.lwjgl.tutoriral.gitbook.chapter6.game;

import org.joml.Matrix4f;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import zenryokuservice.gui.lwjgl.tutoriral.gitbook.chapter6.engine.GameItem;
import zenryokuservice.gui.lwjgl.tutoriral.gitbook.chapter6.engine.Utils;
import zenryokuservice.gui.lwjgl.tutoriral.gitbook.chapter6.engine.Window;
import zenryokuservice.gui.lwjgl.tutoriral.gitbook.chapter6.engine.graph.Mesh;
import zenryokuservice.gui.lwjgl.tutoriral.gitbook.chapter6.engine.graph.ShaderProgram;
import zenryokuservice.gui.lwjgl.tutoriral.gitbook.chapter6.engine.graph.Transformation;

public class Renderer {

    /**
     * Field of View in Radians
     */
    private static final float FOV = (float) Math.toRadians(60.0f);

    private static final float Z_NEAR = 0.01f;

    private static final float Z_FAR = 1000.f;

    private Matrix4f projectionMatrix;

    private ShaderProgram shaderProgram;
    
    private Transformation transformation;

    /** 追加 */

    public Renderer() {
    	transformation = new Transformation();
    }

    public void init(Window window) throws Exception {
        // Create shader
        shaderProgram = new ShaderProgram();
        shaderProgram.createVertexShader(Utils.loadResource("/vertex6.vs"));
        shaderProgram.createFragmentShader(Utils.loadResource("/fragment6.fs"));
        shaderProgram.link();
        
        // Create projection matrix
        float aspectRatio = (float) window.getWidth() / window.getHeight();
        projectionMatrix = new Matrix4f().perspective(FOV, aspectRatio, Z_NEAR, Z_FAR);
        shaderProgram.createUniform("projectionMatrix");
        // 追加
        shaderProgram.createUniform("worldMatrix");
        window.setClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    }

    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public void render(Window window, Mesh mesh, GameItem[] gameItems) {
        clear();

        if ( window.isResized() ) {
            glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResized(false);
        }

        shaderProgram.bind();

        // 追加
        Matrix4f projectionMatrix = transformation.getProjectionMatrix(FOV, window.getWidth(), window.getHeight(), Z_NEAR, Z_FAR);
        shaderProgram.setUniform("projectionMatrix", projectionMatrix);
        for(GameItem gameItem : gameItems) {
        	Matrix4f worldMatrix = transformation.getWorldMatrix(
        			gameItem.getPosition()
        			, gameItem.getRotation()
        			, gameItem.getScale());
            shaderProgram.setUniform("worldMatrix", worldMatrix);
            gameItem.getMesh().render();
        }
        shaderProgram.unbind();

        // Draw the mesh
        glBindVertexArray(mesh.getVaoId());
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES, mesh.getVertexCount(), GL_UNSIGNED_INT, 0);

        // Restore state
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);

        shaderProgram.unbind();
    }

    public void cleanup() {
        if (shaderProgram != null) {
            shaderProgram.cleanup();
        }
    }
}
