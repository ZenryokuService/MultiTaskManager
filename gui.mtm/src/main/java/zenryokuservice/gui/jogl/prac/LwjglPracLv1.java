package zenryokuservice.gui.jogl.prac;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

/**
 * LWJGL初めの練習用クラスです。
 * This Class is Hello LWJGL
 * 
 * @see https://github.com/SilverTiger/lwjgl3-tutorial/blob/master/src/silvertiger/tutorial/lwjgl/Introduction.java
 * 
 * @author takunoji
 */
public class LwjglPracLv1 {
	/** ウィンドウハンドラ */
	long window;

	/**
	 * 初めに動くメソッドです。全てはここから始まる。。。<br/>
	 * Java program start from main method...<br/>
	 * <ul>
	 * <li>LWJGLの起動には"-XstartOnFirstThread"のVM引数がいるようだ</li>
	 * <li>Javaのバージョンは1.8以降を使用する</li>
	 * </ul>
	 * @param args Array of String
	 */
	public static void main(String[] args) {
		LwjglPracLv1 lv1 = new LwjglPracLv1();
		// 2018/10/12 このメソッドを起動していないから動かなかった。。。
		lv1.run();
	}

	public void run() {
		System.out.println("Hello LWJGL " + Version.getVersion());
		// 初期処理
		init();
		// ゲームループ
		gameLoop();
		
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);
System.out.println("終了処理");
		glfwTerminate();
		glfwSetErrorCallback(null).free();		
	}

	/**
	 *  初期処理
	 */
	private void init() {
		// エラー時のコールバックに標準エラー出力を設定する
		GLFWErrorCallback.createPrint(System.err).set();

		// GLFWの初期化
		if (!glfwInit()) {
			throw new IllegalStateException("Unalbe to Initialize GLFW");
		}
		
		// GLFWの設定処理
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
		
		window = glfwCreateWindow(300, 300, "Hello LWJGL", NULL, NULL);
		if(window == NULL) {
			throw new RuntimeException("Failed to create GLFW Window");
		}
		
		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
				glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
			});
		// 2018/10/12 実装漏れ
		try (MemoryStack stack = stackPush()) {
			IntBuffer pWidth = stack.mallocInt(1);
			IntBuffer pHeight = stack.mallocInt(1);
			// ウィンドウサイズ指定
			glfwGetWindowSize(window, pWidth, pHeight);
			// 
			GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
			// 画面の中央座標を設定する
			glfwSetWindowPos(
					window
					, (vidMode.width() - pWidth.get(0) / 2)
					, (vidMode.height() - pHeight.get(0)) / 2
			);
			// OpenGLコンテキストを作成する
			glfwMakeContextCurrent(window);
			// enable v-sync
			glfwSwapInterval(1);
			// 表示処理
			glfwShowWindow(window);
		}
	}
	// 写経元のコードでは「loop()」になっている
	private void gameLoop() {
		// 初期描画
		drawInitView();
		while ( !glfwWindowShouldClose(window) ) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

			glfwSwapBuffers(window); // swap the color buffers
			glfwPollEvents();
		}
	}

	/**
	 * 初期画面を描く<br/>
	 */
	private void drawInitView() {
		GL.createCapabilities();
		// 画面の初期化
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
//	     glLoadIdentity();  
//
//	     glTranslatef(0f,0.0f,-7f);             
//	     glRotatef(45f,0.0f,1.0f,0.0f);               
//	     glColor3f(0.5f,0.5f,1.0f);  
//
	     glBegin(GL_QUADS);    
	        glColor3f(1.0f,1.0f,0.0f);           
	        glVertex3f( 1.0f, 1.0f,-1.0f);        
	        glVertex3f(-1.0f, 1.0f,-1.0f);   
	        glVertex3f(-1.0f, 1.0f, 1.0f);
	        glVertex3f( 1.0f, 1.0f, 1.0f);  
//	        glColor3f(1.0f,0.5f,0.0f);            
//	        glVertex3f( 1.0f,-1.0f, 1.0f);
//	        glVertex3f(-1.0f,-1.0f, 1.0f);
//	        glVertex3f(-1.0f,-1.0f,-1.0f);
//	        glVertex3f( 1.0f,-1.0f,-1.0f);
//	        glColor3f(1.0f,0.0f,0.0f);
//	        glVertex3f( 1.0f, 1.0f, 1.0f);
//	        glVertex3f(-1.0f, 1.0f, 1.0f);
//	        glVertex3f(-1.0f,-1.0f, 1.0f);
//	        glVertex3f( 1.0f,-1.0f, 1.0f);
//	        glColor3f(1.0f,1.0f,0.0f);
//	        glVertex3f( 1.0f,-1.0f,-1.0f);
//	        glVertex3f(-1.0f,-1.0f,-1.0f);
//	        glVertex3f(-1.0f, 1.0f,-1.0f);
//	        glVertex3f( 1.0f, 1.0f,-1.0f);
//	        glColor3f(0.0f,0.0f,1.0f);
//	        glVertex3f(-1.0f, 1.0f, 1.0f);
//	        glVertex3f(-1.0f, 1.0f,-1.0f);
//	        glVertex3f(-1.0f,-1.0f,-1.0f);
//	        glVertex3f(-1.0f,-1.0f, 1.0f);
//	        glColor3f(1.0f,0.0f,1.0f);
//	        glVertex3f( 1.0f, 1.0f,-1.0f);
//	        glVertex3f( 1.0f, 1.0f, 1.0f);
//	        glVertex3f( 1.0f,-1.0f, 1.0f);
//	        glVertex3f( 1.0f,-1.0f,-1.0f);
	    glEnd();    

	}
}
