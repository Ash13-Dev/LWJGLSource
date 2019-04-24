package engine.shaders;

import engine.maths.Matrix4f;
import engine.rendering.lighting.Light;

public class ModelShader extends Shader {
     
    private static final String VERTEX_FILE = "src/engine/shaders/model.vertex.shader";
    private static final String FRAGMENT_FILE = "src/engine/shaders/model.fragment.shader";
    
    private int location_transformationMatrix;
    private int location_projectionMatrix;
    private int location_viewMatrix;
    private int location_lightPosition;
    private int location_lightColor;
    private int location_useFakeLighting;
    
    //private Matrix4f transformationMatrix = new Matrix4f().identity(), projectionMatrix = new Matrix4f().identity(), viewMatrix = new Matrix4f().identity();
 
    public ModelShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }
 
    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textCoords");
        super.bindAttribute(2, "normal");
    }

	@Override
	protected void getAllUniforms() {
		location_transformationMatrix = super.getUniform("transformationMatrix");
        location_projectionMatrix = super.getUniform("projectionMatrix");
        location_viewMatrix = super.getUniform("viewMatrix");
        location_lightPosition = super.getUniform("lightPos");
        location_lightColor = super.getUniform("lightColor");
        location_useFakeLighting = super.getUniform("useFakeLighting");
	}
	
	public void loadFakeLighting(boolean useFakeLighting) {
		super.loadBooleanUniform(location_useFakeLighting, useFakeLighting);
	}
	
	 public void loadLight(Light light) {
	        super.loadVectorUniform(location_lightPosition, light.getPosition());
	        super.loadVectorUniform(location_lightColor, light.getColor());
	 }
	
	public void loadTransformationMatrix(Matrix4f matrix) {
		super.loadMatrixUniform(location_transformationMatrix, matrix);
	}
	
	public void loadProjectionMatrix(Matrix4f matrix) {
		super.loadMatrixUniform(location_projectionMatrix, matrix);
	}
	
	public void loadViewMatrix(Matrix4f matrix) {
		super.loadMatrixUniform(location_viewMatrix, matrix);
	}
}