package engine.rendering.rendererClasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjglx.debug.opengl.GL15;

import engine.io.Window;
import engine.maths.Matrix4f;
import engine.rendering.Camera;
import engine.rendering.models.BlockModel;
import engine.rendering.models.TexturedModel;
import engine.shaders.NonLightingShader;

public class NonLightRenderer {

	private NonLightingShader shader;
	
	public NonLightRenderer(NonLightingShader nlShader) {
		this.shader = nlShader;
	}
	
	public void loadProjMatrix(Matrix4f projMatrix) {
		shader.bind();
		shader.loadProjectionMatrix(projMatrix);
		shader.unbind();
	}
	
	public void render(Map<TexturedModel, List<BlockModel>> nlObjs){
		for (TexturedModel texModel : nlObjs.keySet()) {
			
		    prepareModel(texModel);
		    List<BlockModel> NlList = nlObjs.get(texModel);
			for(BlockModel model : NlList) {
				prepareInstance(model);
		        GL11.glDrawElements(GL11.GL_TRIANGLES, model.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			}
			
			 unbindModel();
		}
    }
	
	private void prepareInstance(BlockModel bModel) {
		shader.loadTransformationMatrix(bModel.getTransformationMatrix());
	}
	
	private void prepareModel(TexturedModel model) {
		GL30.glBindVertexArray(model.getVertexArrayID());
		GL20.glEnableVertexAttribArray(0);
	    GL20.glEnableVertexAttribArray(1);
	    GL13.glActiveTexture(GL13.GL_TEXTURE0);
	    GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getMaterial().getTextureID());
	}
	
	private void unbindModel() {
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
	    GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		GL20.glDisableVertexAttribArray(0);
	    GL20.glDisableVertexAttribArray(1);
	    GL30.glBindVertexArray(0);
	}
	
	public void removeAllNlModels(Map<TexturedModel, List<BlockModel>> nlObjs) {
		for (TexturedModel texModel : nlObjs.keySet()) {
			texModel.remove();
		}
	}

}
