#version 410 core

layout (location = 0) in vec3 vxPosition;

out vec3 textureCoords;
out gl_PerVertex {
    vec4 gl_Position;
};

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

void main(void){
    textureCoords=vxPosition;
    gl_Position=projectionMatrix*viewMatrix*vec4(vxPosition,1.0);
}