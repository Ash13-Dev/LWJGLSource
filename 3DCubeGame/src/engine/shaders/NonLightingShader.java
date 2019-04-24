package engine.shaders;

import engine.maths.Matrix4f;

public class NonLightingShader extends Shader {
     
    private static final String VERTEX_FILE = "src/engine/shaders/nonlighting.vertex.shader";
    private static final String FRAGMENT_FILE = "src/engine/shaders/nonlighting.fragment.shader";
    
    private int location_transformationMatrix;
    private int location_projectionMatrix;
    private int location_viewMatrix;
    
    //private Matrix4f transformationMatrix = new Matrix4f().identity(), projectionMatrix = new Matrix4f().identity(), viewMatrix = new Matrix4f().identity();
 
    public NonLightingShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }
 
    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textCoords");
    }

	@Override
	protected void getAllUniforms() {
		location_transformationMatrix = super.getUniform("transformationMatrix");
        location_projectionMatrix = super.getUniform("projectionMatrix");
        location_viewMatrix = super.getUniform("viewMatrix");
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