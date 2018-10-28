#version 330

layout (location =0) in vec3 position;

// 追記 2018/10/27
layout(location=1) in vec3 inColour;
out vec3 exColour;

void main()
{
	gl_Position = vec4(position, 1.0);
	// 追記 2018/10/27
	exColour = inColour;
}