package engine.rendering.rendererClasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import engine.io.Loader;
import engine.io.Window;
import engine.maths.Matrix4f;
import engine.maths.Vector3f;
import engine.rendering.Camera;
import engine.rendering.lighting.Light;
import engine.rendering.models.BlockModel;
import engine.rendering.models.TexturedModel;
import engine.shaders.ModelShader;
import engine.shaders.NonLightingShader;

public class MainRenderer {
	
	private static final float FOV = 70; // field of view angle
	private static final float NEAR_PLANE = 0.1f;
	private static final float FAR_PLANE = 1000;
	
	private Matrix4f projectionMatrix;
	
	private int oldW, oldH;
	
	private Window window;
	private Vector3f backgroundColor;
	
	private Camera cam = new Camera();
	private NonLightingShader nlShader = new NonLightingShader();
	private ModelShader mShader = new ModelShader();
	private NonLightRenderer nlRenderer = new NonLightRenderer(nlShader);
	private ModelRenderer modelRenderer = new ModelRenderer(mShader);
	private static Light sun = new Light(new Vector3f(500, 500, 500), new Vector3f(0.8f, 0.8f, 0.6f));
	private Loader loader = new Loader();
	
	private Map<TexturedModel, List<BlockModel>> blocks = new HashMap<>();
	private Map<TexturedModel, List<BlockModel>> nlObjs = new HashMap<>();
	
	public MainRenderer(Window window) {
		this.window = window;
		adjustProjectionMatrix();
		backgroundColor = new Vector3f(0.5f, 0.5f, 0.5f);
	}
	
	public void MainRender() {
		//loader.LoadModels(this, nlRenderer, modelRenderer);
		nlShader.create();
		mShader.create();
	}
	
	public void update() {
		prepare();
		cam.update(window);
		nlShader.bind();
		nlShader.loadViewMatrix(cam.getViewMatrix());
		nlRenderer.render(nlObjs);
		nlShader.unbind();
		mShader.bind();
		mShader.loadLight(sun);
		mShader.loadViewMatrix(cam.getViewMatrix());
		modelRenderer.render(blocks);
		mShader.unbind();
		nlObjs.clear();
		blocks.clear();
	}
	
	public void prepare() {
		adjustProjectionMatrix();
		//GL11.glClearColor(backgroundColor.getX(), backgroundColor.getY(), backgroundColor.getZ(), 1.0f);
		//GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}
	
	private void adjustProjectionMatrix() {
		
		if(oldW != window.getWidth() || oldH != window.getHeight()) {
			oldW = window.getWidth();
			oldH = window.getHeight();
			System.out.println("OUTPUT #001 @Main Renderer: CHANGED PROJETION MATRIX");
			createProjectionMatrix();
			nlRenderer.loadProjMatrix(projectionMatrix);
			modelRenderer.loadProjMatrix(projectionMatrix);
		}
	}
	
	private void createProjectionMatrix() {
		projectionMatrix = new Matrix4f().projection(FOV, (float) window.getWidth() / (float) window.getHeight() , NEAR_PLANE, FAR_PLANE);
	}
	
	public void processNonLightingModel(BlockModel model) {
		TexturedModel texModel = model.getModel();
		List<BlockModel> nlObjs = this.nlObjs.get(texModel);
		
		if (nlObjs != null) {
			nlObjs.add(model);
		} else {
			List<BlockModel> newNlList = new ArrayList<>();
			newNlList.add(model);
			this.nlObjs.put(texModel, newNlList);
		}
	}

	public void processModel(BlockModel model) {
		TexturedModel texModel = model.getModel();
		List<BlockModel> blocks = this.blocks.get(texModel);
		
		if (blocks != null) {
			blocks.add(model);
		} else {
			List<BlockModel> newList = new ArrayList<>();
			newList.add(model);
			this.blocks.put(texModel, newList);
		}
	}
	
}
