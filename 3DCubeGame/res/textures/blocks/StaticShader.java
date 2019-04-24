package com.weebly.beewyka819.projectmatrix.engine.shaders;

import com.weebly.beewyka819.projectmatrix.engine.maths.Matrix4f;
import com.weebly.beewyka819.projectmatrix.engine.renderengine.entities.Light;

public class StaticShader extends ShaderProgram
{
    private static final String VERTEX_FILE = "./src/main/java/com/weebly/beewyka819/projectmatrix/engine/shaders/staticVertexShader.glsl";
    private static final String FRAGMENT_FILE = "./src/main/java/com/weebly/beewyka819/projectmatrix/engine/shaders/staticFragmentShader.glsl";
    
    private int location_transformationMatrix;
    private int location_projectionMatrix;
    private int location_viewMatrix;
    private int location_invViewMatrix;
    private int location_lightPosition;
    private int location_lightColor;
    private int location_shineDamper;
    private int location_reflectivity;
    private int location_useFakeLighting;
    
    public StaticShader()
    {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }
    
    @Override
    protected void bindAttributes() 
    {
        super.bindAttribute(0, "vertices");
        super.bindAttribute(1, "textureCoords");
    }
    
    @Override
    protected void getAllUniformLocations()
    {
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
        location_invViewMatrix = super.getUniformLocation("invViewMatrix");
        location_lightPosition = super.getUniformLocation("lightPosition");
        location_lightColor = super.getUniformLocation("lightColor");
        location_shineDamper = super.getUniformLocation("shineDamper");
        location_reflectivity = super.getUniformLocation("reflectivity");
        location_useFakeLighting = super.getUniformLocation("useFakeLighting");
    }
    
    public void loadFakeLightingVariable(boolean useFake)
    {
        super.loadBoolean(location_useFakeLighting, useFake);
    }
    
    public void loadShineVariables(float damper, float reflectivity)
    {
        super.loadFloat(location_shineDamper, damper);
        super.loadFloat(location_reflectivity, reflectivity);
    }
    
    public void loadLight(Light light)
    {
        super.loadVector(location_lightPosition, light.getPosition());
        super.loadVector(location_lightColor, light.getColor());
    }
    
    public void loadTransformationMatrix(Matrix4f matrix)
    {
        super.loadMatrix(location_transformationMatrix, matrix);
    }
    
    public void loadProjectionMatrix(Matrix4f matrix)
    {
        super.loadMatrix(location_projectionMatrix, matrix);
    }
    
    public void loadViewMatrix(Matrix4f matrix)
    {
        super.loadMatrix(location_viewMatrix, matrix);
        Matrix4f.invert(matrix, matrix);
        super.loadMatrix(location_invViewMatrix, matrix);
    }
}