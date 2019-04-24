package main;

import org.lwjgl.glfw.GLFW;

import engine.io.Loader;
import engine.io.Window;
import engine.maths.Vector3f;
import engine.rendering.models.BlockModel;
import engine.rendering.models.TexturedModel;
import engine.rendering.rendererClasses.MainRenderer;

public class Main {
	
	private static Window window = new Window(500, 500, 60, "3DCubeGame");
	private static MainRenderer renderer = new MainRenderer(window);

	public static void main(String[] args) {
		window.setCursor("Default.png");
		window.setIcon("dirt.jpg");
		window.create();
		window.lockMouse("Default.png");
		window.setBackgroundColor(0.0f, 0.2f, 0.8f);
		renderer.MainRender();
		
		TexturedModel skybox = Loader.loadModel("skybox/skybox.obj", "skyboxes/morning/template.png");
		TexturedModel grass = Loader.loadModel("blocks/blockbaseTwo.obj", "blocks/TextureMap.png");
		
		BlockModel skyboxModel = new BlockModel(skybox, new Vector3f(0, 0, 1), new Vector3f(0, 0, 0), new Vector3f(300f, 300f, 300f));
        BlockModel grassModel = new BlockModel(grass, new Vector3f(0, 0, 1), new Vector3f(0, 0, 0), new Vector3f(0.35f, 0.35f, 0.35f));
		
        renderer.processNonLightingModel(skyboxModel);
        
		while (!window.closed()) {
			if (window.isUpdating()) {
				//Updaters
				window.update();
				//Rendering
				renderer.update();
				
				//Just general game stuffs
				if (window.isKeyPressed(GLFW.GLFW_KEY_ESCAPE)) window.unlockMouse();
				
				//Other things
				window.swapBuffers();
			}
		}

		window.stop();
		
	}

}
