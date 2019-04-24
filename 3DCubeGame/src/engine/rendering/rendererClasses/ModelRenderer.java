package engine.rendering.rendererClasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import engine.io.Window;
import engine.maths.Matrix4f;
import engine.maths.Vector3f;
import engine.rendering.Camera;
import engine.rendering.lighting.Light;
import engine.rendering.models.BlockModel;
import engine.rendering.models.TexturedModel;
import engine.shaders.ModelShader;
import engine.shaders.NonLightingShader;
 
public class ModelRenderer {
	
	private ModelShader shader;
	
	public ModelRenderer(ModelShader shader) {
		this.shader = shader;
	}
	
	public void loadProjMatrix(Matrix4f projMatrix) {
		shader.unbind();
		shader.bind();
		shader.loadProjectionMatrix(projMatrix);
		shader.unbind();
	}
	
	public void render(Map<TexturedModel, List<BlockModel>> blocks){
		for (TexturedModel texModel : blocks.keySet()) {
			GL30.glBindVertexArray(texModel.getVertexArrayID());
			GL20.glEnableVertexAttribArray(0);
		    GL20.glEnableVertexAttribArray(1);
		    GL20.glEnableVertexAttribArray(2);
		    
		    List<BlockModel> list = blocks.get(texModel);
			for(BlockModel model : list) {
				shader.loadTransformationMatrix(model.getTransformationMatrix());
		        GL13.glActiveTexture(GL13.GL_TEXTURE0);
		        GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getModel().getMaterial().getTextureID());
		        GL11.glDrawElements(GL11.GL_TRIANGLES, model.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			}
			
			 GL20.glDisableVertexAttribArray(0);
		     GL20.glDisableVertexAttribArray(1);
		     GL20.glDisableVertexAttribArray(2);
		     GL30.glBindVertexArray(0);
		}
		
		shader.unbind();
		
    }
	
	public void removeAllModels(Map<TexturedModel, List<BlockModel>> blocks) {
		for (TexturedModel texModel : blocks.keySet()) {
			texModel.remove();
		}
	}
	
}