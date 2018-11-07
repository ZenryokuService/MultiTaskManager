package zenryokuservice.gui.lwjgl.tutoriral.gitbook.chapter5.engine.graph;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import org.lwjgl.system.MemoryUtil;

public class Mesh {

	private final int vaoId;

    private final int idxVboId;

    private final int vertexCount;
    
    private Texture texture;
    private final List<Integer> vboIdList;

    public Mesh(float[] positions, int[] indices, float[] textCoords, boolean flg) {
        FloatBuffer posBuffer = null;
        IntBuffer indicesBuffer = null;
        FloatBuffer textCoordBuffer = null;
        try {
        	texture = new Texture("/textures/Mon.png");
        } catch(Exception e) {
        	e.printStackTrace();
        }
        vboIdList = new ArrayList<>();
        try {
            vertexCount = indices.length;
            vaoId = glGenVertexArrays();
            vboIdList.add(vaoId);
            glBindVertexArray(vaoId);

            // Position VBO
            int posVboId = glGenBuffers();
            vboIdList.add(posVboId);
            posBuffer = MemoryUtil.memAllocFloat(positions.length);
            posBuffer.put(positions).flip();
            glBindBuffer(GL_ARRAY_BUFFER, posVboId);
            glBufferData(GL_ARRAY_BUFFER, posBuffer, GL_STATIC_DRAW);
            glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

            // Texutre coordinates VBO
            int vboId = glGenBuffers();
            vboIdList.add(vboId);
            textCoordBuffer = MemoryUtil.memAllocFloat(textCoords.length);
            textCoordBuffer.put(textCoords).flip();
            glBindBuffer(GL_ARRAY_BUFFER, vaoId);
            glBufferData(GL_ARRAY_BUFFER, textCoordBuffer, GL_STATIC_DRAW);
            glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
            
            // Index VBO
            idxVboId = glGenBuffers();
            vboIdList.add(idxVboId);
            indicesBuffer = MemoryUtil.memAllocInt(indices.length);
            indicesBuffer.put(indices).flip();
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, idxVboId);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);
            
            glBindBuffer(GL_ARRAY_BUFFER, 0);
            glBindVertexArray(0);
        } finally {
            if (posBuffer != null) {
                MemoryUtil.memFree(posBuffer);
            }
            if (indicesBuffer != null) {
                MemoryUtil.memFree(indicesBuffer);
            }
            if (textCoordBuffer != null) {
                MemoryUtil.memFree(textCoordBuffer);
            }
        }
    }

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

    public void cleanUp() {
    	texture.cleanup();
        glDisableVertexAttribArray(0);

        // Delete the VBOs
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        for(int vbo : vboIdList) {
            glDeleteBuffers(vbo);
        }
        glDeleteBuffers(idxVboId);
        
        // Delete the VAO
        glBindVertexArray(0);
        glDeleteVertexArrays(vaoId);
    }
}
