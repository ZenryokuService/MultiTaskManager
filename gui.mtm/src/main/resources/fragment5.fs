#version 330

// 追記 2018/10/27
//in  vec3 exColour;
//out vec4 fragColor;

// Texture追加修正 2018/11/08
in vec2 outTextCoord;
out vec4 flagColor;

uniform sampler2D texture_sampler;

void main() {
	flagColor = texture(texture_sampler, outTextCoord);
}