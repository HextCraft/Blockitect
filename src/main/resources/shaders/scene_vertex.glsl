#version 410 core

layout (location = 0) in vec3 vxPosition;
layout (location = 1) in vec3 vxNormal;
layout (location = 2) in vec2 vxTexture;
layout (location = 3) in mat4 transformationMatrixInstanced;
layout (location = 7) in vec2 surfaceBehaviorInstanced;
layout (location = 8) in vec4 textureAtlasSettingsInstanced;
layout (location = 9) in vec3 perObjectColorInstanced;
layout (location = 10) in uint highlightedInstanced;
layout (location = 11) in vec3 selectedTriangleInstanced;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform int materialType;

out VS_OUT{
    vec3 vxPosition;
    vec2 vxTexture;
    vec3 vxNormalVector;
    vec3 vxToCameraVector;
    vec2 surfaceBehaviorInstanced;
    flat int materialType;
    flat vec3 perObjectColorInstanced;
    flat uint highlightedInstanced;
} vs_out;

out gl_PerVertex {
    vec4 gl_Position;
};

void main()
{
    vec4 vertexWorldPosition=transformationMatrixInstanced*vec4(vxPosition,1.0);
    vec3 cameraPosition=(inverse(viewMatrix)*vec4(0.0,0.0,0.0,1.0)).xyz;

    vs_out.highlightedInstanced=highlightedInstanced;

    if(materialType == 0) { // textured
        vs_out.vxTexture=(vxTexture/textureAtlasSettingsInstanced.zw)+vec2(textureAtlasSettingsInstanced.xy);
    }
    else if(materialType == 1) { // per object colored
        if(gl_VertexID == int(selectedTriangleInstanced.x) || gl_VertexID==int(selectedTriangleInstanced.y) || gl_VertexID==int(selectedTriangleInstanced.z)){
            vs_out.perObjectColorInstanced=vec3(1.0f,0.0f,0.0f);
            vs_out.highlightedInstanced=0; // if the triangle is hit, we dont need to highlight those fragments
        }
        else{
            vs_out.perObjectColorInstanced=perObjectColorInstanced;
            vs_out.highlightedInstanced=highlightedInstanced;
        }
    }

    vs_out.vxPosition=vertexWorldPosition.xyz;
    vs_out.materialType=materialType;
    vs_out.vxNormalVector=normalize((transformationMatrixInstanced*vec4(vxNormal,0.0)).xyz); // 0.0, because we need rotation, scale, but do not translate. We need it's direction, not position
    vs_out.vxToCameraVector=cameraPosition - vertexWorldPosition.xyz;
    vs_out.surfaceBehaviorInstanced=surfaceBehaviorInstanced;

    gl_Position = projectionMatrix * viewMatrix * vertexWorldPosition;
}