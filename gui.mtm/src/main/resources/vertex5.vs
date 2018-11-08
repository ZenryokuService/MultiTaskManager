#version 330

//layout (location =0) in vec3 position;
// 追記 2018/10/27
//layout(location=1) in vec3 inColour;
//out vec3 exColour;

// Texture追加修正 2018/11/08
layout(location=0) in vec3 position;
layout(location=1) in vec2 textCoord;

out vec2 outTextCoord;

uniform mat4 worldMatrix;
uniform mat4 projectionMatrix;

void main()
{
//	gl_Position = vec4(position, 1.0);
	// 追記 2018/10/27
//	exColour = inColour;
	gl_Position = projectionMatrix * worldMatrix * vec4(position, 1.0);
	outTextCoord = textCoord;

}
