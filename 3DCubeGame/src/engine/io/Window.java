package engine.io;

import java.nio.DoubleBuffer;
import java.nio.IntBuffer;

import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class Window {
	
	private int width, height;
	private String title;
	private double fps_cap, time, processedTime = 0;;
	private long window;
	private GLFWImage cursorBuffer, crosshairBuffer;
	private GLFWImage.Buffer iconBuffer;
	private Vector3f backgroundColor;
	private boolean[] keys = new boolean[GLFW.GLFW_KEY_LAST];
	private boolean[] mouseButtons = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];
	
	public Window(int width, int height, int fps, String title) {
		this.width = width;
		this.height = height;
		this.title = title;
		fps_cap = fps;
		backgroundColor = new Vector3f(0.0f, 0.0f, 0.0f);
		cursorBuffer = null;
		iconBuffer = null;
	}
	
	// Function to be called
	
	public void create() {
		if(!GLFW.glfwInit()) {
			System.err.println("Error: Couldn't initalize GLFW");
			System.exit(-1);
		}
		
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);
		
		window = GLFW.glfwCreateWindow(width, height, title, 0, 0);
	
		if(window == 0) {
			System.err.println("Error: Window couldn't be created");
			System.exit(-1);
		}
		
		GLFW.glfwMakeContextCurrent(window);
		GL.createCapabilities();
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		
		GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		GLFW.glfwSetWindowPos(window, (videoMode.width() - width) / 2, (videoMode.height() - height) / 2);
		
		if (iconBuffer != null) {
			GLFW.glfwSetWindowIcon(window, iconBuffer);
		}
		
		GLFW.glfwShowWindow(window);
		
		time = getTime();
		
	}
	
	//Important Stuffs
	
	public void update() {
		for (int i = 0; i < GLFW.GLFW_KEY_LAST; i++) keys[i] = isKeyDown(i);
		for (int i = 0; i < GLFW.GLFW_MOUSE_BUTTON_LAST; i++) mouseButtons[i] = isMouseDown(i);
		
		IntBuffer widthBuffer = BufferUtils.createIntBuffer(1); IntBuffer heightBuffer = BufferUtils.createIntBuffer(1);
		GLFW.glfwGetWindowSize(window, widthBuffer, heightBuffer);
		width = widthBuffer.get(0);	height = heightBuffer.get(0);
		GL11.glViewport(0, 0, width, height);
		
		if (cursorBuffer != null) {
			long cursor = GLFW.glfwCreateCursor(cursorBuffer, 0, 0);
			GLFW.glfwSetCursor(window, cursor);
		}
		
		GL11.glClearColor(backgroundColor.x, backgroundColor.y, backgroundColor.z, 1.0f);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GLFW.glfwPollEvents();
	}
	
	public boolean isUpdating() {
		double nextTime = getTime();
		double passedTime = nextTime - time;
		processedTime += passedTime;
		time = nextTime;
		
		while (processedTime > 1.0/fps_cap) {
			processedTime -= 1.0/fps_cap;
			return true;
		}
		
		return false;
	}
	
	public void stop() {
		GLFW.glfwSetWindowSizeCallback(window, null);
		GLFW.glfwTerminate();
	}
	
	public void swapBuffers() {
		GLFW.glfwSwapBuffers(window);
	}
	
	public boolean closed() {
		return GLFW.glfwWindowShouldClose(window);
	}
	
	public double getTime() {
		return (double) System.nanoTime() / (double) 1000000000;
	}
	
	//Input things
	
	public boolean isKeyDown(int keyCode) {
		return GLFW.glfwGetKey(window, keyCode) == 1;
	}
	
	public boolean isMouseDown(int mouseButton) {
		return GLFW.glfwGetMouseButton(window, mouseButton) == 1;
	}
	
	public boolean isKeyPressed(int keyCode) {
		return isKeyDown(keyCode) && !keys[keyCode];
	}
	
	public boolean isKeyReleased(int keyCode) {
		return !isKeyDown(keyCode) && keys[keyCode];
	}
	
	public boolean isMousePressed(int mouseButton) {
		return isMouseDown(mouseButton) && !mouseButtons[mouseButton];
	}
	
	public boolean isMouseReleased(int mouseButton) {
		return !isMouseDown(mouseButton) && mouseButtons[mouseButton];
	}
	
	public double getMouseX() {
		DoubleBuffer buffer = BufferUtils.createDoubleBuffer(1);
		GLFW.glfwGetCursorPos(window, buffer, null);
		return buffer.get(0);
	}
	
	public double getMouseY() {
		DoubleBuffer buffer = BufferUtils.createDoubleBuffer(1);
		GLFW.glfwGetCursorPos(window, null, buffer);
		return buffer.get(0);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public String getTitle() {
		return title;
	}

	public double getFPS() {
		return fps_cap;
	}

	public long getWindow() {
		return window;
	}
	
	public void setBackgroundColor(float r, float g, float b) {
		backgroundColor = new Vector3f(r, g, b);
	}
	
	public void setIcon(String path) {
		Image icon = Loader.loadImage("res/images/program/" + path);
		GLFWImage iconImage = GLFWImage.malloc(); iconBuffer = GLFWImage.malloc(1);
		iconImage.set(icon.getWidth(), icon.getHeight(), icon.getImage());
		iconBuffer.put(0, iconImage);
	}
	
	public void setCursor(String path) {
		Image cursor = Loader.loadImage("res/images/cursors/" + path);
		cursorBuffer = GLFWImage.malloc();
		cursorBuffer.set(cursor.getWidth(), cursor.getHeight(), cursor.getImage());
	}
	
	public void lockMouse(String path) {
		GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);
		Image crossHair = Loader.loadImage("res/images/cursors/" + path);
		crosshairBuffer = GLFWImage.malloc();
		crosshairBuffer.set(crossHair.getWidth(), crossHair.getHeight(), crossHair.getImage());
	}
	
	public void unlockMouse() {
		GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_NORMAL);
	}

}
