package engine.io;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import engine.io.obj.ModelData;
import engine.io.obj.OBJLoader;
import engine.maths.Vector3f;
import engine.rendering.models.BlockModel;
import engine.rendering.models.TexturedModel;
import engine.rendering.rendererClasses.MainRenderer;
import engine.rendering.rendererClasses.ModelRenderer;
import engine.rendering.rendererClasses.NonLightRenderer;

public class Loader {
	
	public void LoadModels(MainRenderer mRend, NonLightRenderer nlR, ModelRenderer mR) {
		TexturedModel skybox = Loader.loadModel("skybox/skybox.obj", "skyboxes/morning/template.png");
		TexturedModel grass = Loader.loadModel("blocks/blockbaseTwo.obj", "blocks/TextureMap.png");
		
		BlockModel skyboxModel = new BlockModel(skybox, new Vector3f(0, 0, 1), new Vector3f(0, 0, 0), new Vector3f(300f, 300f, 300f));
        BlockModel grassModel = new BlockModel(grass, new Vector3f(0, 0, 1), new Vector3f(0, 0, 0), new Vector3f(0.35f, 0.35f, 0.35f));
		
        mRend.processNonLightingModel(skyboxModel);
		mRend.processModel(grassModel);
		System.out.println("LOADING DONE...");
	}
	
	 public static Image loadImage(String path) {
	        ByteBuffer image;
	        int width, heigh;
	        try (MemoryStack stack = MemoryStack.stackPush()) {
	            IntBuffer comp = stack.mallocInt(1);
	            IntBuffer w = stack.mallocInt(1);
	            IntBuffer h = stack.mallocInt(1);

	            image = STBImage.stbi_load(path, w, h, comp, 4);
	            if (image == null) {
	                System.err.println("Couldn't load " + path);
	            }
	            width = w.get();
	            heigh = h.get();
	        }
	        return new Image(width, heigh, image);
	    }
	 
	 public static TexturedModel loadModel(String objPath, String TexturePath) {
		 ModelData data = OBJLoader.loadOBJ(objPath);
		 return new TexturedModel(data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getIndices(), TexturePath);
	 }

}
