#version 400 core

in vec2 passTextCoords;

out vec4 fragColor;

uniform sampler2D textureSampler;

void main(void)
{
	vec4 textureColor = texture(textureSampler, passTextCoords);
	if(textureColor.a < 0.5)
	{
		discard;
	}
	
	fragColor = textureColor;
}