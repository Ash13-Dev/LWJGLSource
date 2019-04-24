#version 400 core

in vec3 position;
in vec2 textCoords;
in vec3 normal;

out vec2 passTextCoords;
out vec3 toLightVec;
out vec3 surfaceNormal;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform vec3 lightPos;
uniform float useFakeLighting;

void main(void) {

	vec4 worldPos = transformationMatrix * vec4(position, 1.0);
	gl_Position = projectionMatrix * viewMatrix * worldPos;
	passTextCoords = textCoords;
	
	vec3 actualNormal = normal;
	if(useFakeLighting > 0.5)
	{
		actualNormal = vec3(1.0, 1.0, 1.0);
	}
	
	toLightVec = lightPos - worldPos.xyz;
	surfaceNormal = (transformationMatrix * vec4(actualNormal, 0.0)).xyz;
}