package zenryokuservice.gui.lwjgl.tutoriral.gitbook.chapter5.engine.graph;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
// 2018/11/08
import org.lwjgl.opengl.GL13;

import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLUtil;
import org.lwjgl.system.MemoryUtil;

public class Mesh {

	private final int vaoId;

    private final List<Integer> vboIdList;
//    private final int idxVboId;

    private final int vertexCount;
    
    private Texture texture;

    public Mesh(float[] positions, int[] indices, float[] textCoords) {
        FloatBuffer posBuffer = null;
        FloatBuffer textCoordsBuffer = null;
        IntBuffer indicesBuffer = null;
        try {
        	texture = new Texture("/textures/Mon.png");
        } catch(Exception e) {
        	e.printStackTrace();
        }
        vboIdList = new ArrayList<>();
        try {
            vertexCount = indices.length;
            vaoId = GL30.glGenVertexArrays();
            GL30.glBindVertexArray(vaoId);

            // Position VBO
            int vboId = GL15.glGenBuffers();
            vboIdList.add(vboId);
            posBuffer = MemoryUtil.memAllocFloat(positions.length);
            posBuffer.put(positions).flip();
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
            GL15.glBufferData(GL15.GL_ARRAY_BUFFER, posBuffer, GL15.GL_STATIC_DRAW);
            GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);

            // Texutre coordinates VBO
            vboId = GL15.glGenBuffers();
            vboIdList.add(vboId);
            textCoordsBuffer = MemoryUtil.memAllocFloat(textCoords.length);
            textCoordsBuffer.put(textCoords).flip();
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
            GL15.glBufferData(GL15.GL_ARRAY_BUFFER, textCoordsBuffer, GL15.GL_STATIC_DRAW);
            GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 0, 0);
            
            // Index VBO
            vboId = GL15.glGenBuffers();
            vboIdList.add(vboId);
            indicesBuffer = MemoryUtil.memAllocInt(indices.length);
            indicesBuffer.put(indices).flip();
            GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboId);
            GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);
            
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
            GL30.glBindVertexArray(0);
        } finally {
            if (posBuffer != null) {
                MemoryUtil.memFree(posBuffer);
            }
            if (textCoordsBuffer != null) {
                MemoryUtil.memFree(textCoordsBuffer);
            }
            if (indicesBuffer != null) {
                MemoryUtil.memFree(indicesBuffer);
            }
        }
    }

// オーバーロードできないためコメントアウト(元々のコード)
//    public Mesh(float[] positions, int[] indices, float[] colors) {
//    	
//        FloatBuffer posBuffer = null;
//        IntBuffer indicesBuffer = null;
//        try {
//            vertexCount = indices.length;
//
//            int vaoId = glGenVertexArrays();
//            glBindVertexArray(vaoId);
//            // Position VBO
//            posVboId = glGenBuffers();
//            posBuffer = MemoryUtil.memAllocFloat(positions.length);
//            posBuffer.put(positions).flip();
//            glBindBuffer(GL_ARRAY_BUFFER, posVboId);
//            glBufferData(GL_ARRAY_BUFFER, posBuffer, GL_STATIC_DRAW);
//            glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
//
//            // Index VBO
//            idxVboId = glGenBuffers();
//            indicesBuffer = MemoryUtil.memAllocInt(indices.length);
//            indicesBuffer.put(indices).flip();
//            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, idxVboId);
//            glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);
//            
//            // Color VBO この部分を追記する 2018/10/27
//            final int colourVboId = glGenBuffers();
//            FloatBuffer colourBuffer = MemoryUtil.memAllocFloat(positions.length);
//            colourBuffer.put(colors).flip();
//            glBindBuffer(GL_ARRAY_BUFFER, colourVboId);
//            glBufferData(GL_ARRAY_BUFFER, colourBuffer, GL_STATIC_DRAW);
//            MemoryUtil.memFree(colourBuffer);
//            glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
//
//            glBindBuffer(GL_ARRAY_BUFFER, 0);
//            glBindVertexArray(0);
//        } finally {
//            if (posBuffer != null) {
//                MemoryUtil.memFree(posBuffer);
//            }
//            if (indicesBuffer != null) {
//                MemoryUtil.memFree(indicesBuffer);
//            }
//        }
//    }
    
    public int getVaoId() {
        return vaoId;
    }

    public int getVertexCount() {
        return vertexCount;
    }
    
    // 2018/11/08追加
    public void render() {
    	// Texture setting
    	GL13.glActiveTexture(GL13.GL_TEXTURE0);
    	GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getId());
    	
    	// Meshを描く
    	GL30.glBindVertexArray(getVaoId());
    	GL20.glEnableVertexAttribArray(0);
    	GL20.glEnableVertexAttribArray(1);
    	
    	GL11.glDrawElements(GL11.GL_TRIANGLES, getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
    	// Restore state
    	GL20.glDisableVertexAttribArray(0);
    	GL20.glDisableVertexAttribArray(1);
    	GL30.glBindVertexArray(0);
    }

    public void cleanUp() {
    	GL20.glDisableVertexAttribArray(0);

        // Delete the VBOs
    	GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        for(int vbo : vboIdList) {
        	GL15.glDeleteBuffers(vbo);
        }

    	texture.cleanup();

        // Delete the VAO
        GL30.glBindVertexArray(0);
        GL30.glDeleteVertexArrays(vaoId);
    }
}
