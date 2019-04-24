#version 400 core

in vec2 passTextCoords;
in vec3 toLightVec;
in vec3 surfaceNormal;

out vec4 fragColor;

uniform sampler2D textureSampler;
uniform vec3 lightColor;

void main(void)
{
	vec3 unitNormal = normalize(surfaceNormal);
	vec3 unitLightVector = normalize(toLightVec);
	
	float nDot1 = dot(unitLightVector, unitNormal);
	float brightness = max(nDot1, 0.7);
	vec3 diffuse = brightness * lightColor;
	
	vec4 textureColor = texture(textureSampler, passTextCoords);
	if(textureColor.a < 0.5)
	{
		discard;
	}
	
	fragColor = vec4(diffuse, 1.0) * textureColor;
}