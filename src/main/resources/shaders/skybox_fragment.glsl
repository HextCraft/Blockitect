#version 410 core

in vec3 textureCoords;
out vec4 FINAL_COLOR;

uniform samplerCube skyboxSampler;

void main(void){
    FINAL_COLOR=texture(skyboxSampler,textureCoords);
}