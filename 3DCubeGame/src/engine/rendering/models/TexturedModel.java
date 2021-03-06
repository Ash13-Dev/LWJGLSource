package engine.rendering.models;

import org.lwjgl.opengl.GL30;

import engine.rendering.Material;

import org.lwjgl.opengl.GL15;

public class TexturedModel extends Model {
    private int vertexArrayID, vertexBufferID, textureCoordsBufferID, indicesBufferID, normalsBufferID, vertexCount;
    private Material material;
    
    private boolean useFakeLighting = false;
    
    public TexturedModel(float[] vertices, float[] textureCoords, float[] normals, int[] indices, String file) {
    	vertexArrayID = super.createVertexArray();
    	indicesBufferID = super.bindIndicesBuffer(indices);
        vertexBufferID = super.storeData(0, 3, vertices);
        textureCoordsBufferID = super.storeData(1, 2, textureCoords);
        normalsBufferID = super.storeData(2,  3,  normals);
		vertexCount = indices.length;
        GL30.glBindVertexArray(0);
        material = new Material(file);
    }
    
    public void remove() {
    	GL30.glDeleteVertexArrays(vertexArrayID);
    	GL15.glDeleteBuffers(vertexBufferID);
    	GL15.glDeleteBuffers(textureCoordsBufferID);
    	GL15.glDeleteBuffers(indicesBufferID);
    	GL15.glDeleteBuffers(normalsBufferID);
    	material.remove();
    }
    
    public boolean useFakeLighting() {
    	return useFakeLighting;
    }
    
    public void setFakeLighting(boolean useFakeLighting) {
    	this.useFakeLighting = useFakeLighting;
    }
 
    public int getVertexArrayID() {
        return vertexArrayID;
    }
 
    public int getVertexCount() {
        return vertexCount;
    }

	public Material getMaterial() {
		return material;
	}
}