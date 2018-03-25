#version 410 core

layout (location = 0) out vec4 FINAL_COLOR;
uniform sampler2D mainTextureSampler;
uniform bool polygonMode;

in VS_OUT {
    vec3 vxPosition;
    vec2 vxTexture;
    vec3 vxNormalVector;
    vec3 vxToCameraVector;
    vec2 surfaceBehaviorInstanced;
    flat int materialType;
    flat vec3 perObjectColorInstanced;
    flat uint highlightedInstanced;
} fs_in;

struct Attenuation{
    float constant;
    float linear;
    float exponent;
};

struct AmbientLight{
    vec3 color;
    float strength;
};

struct DirectionalLight{
    vec3 color;
    float strength;
    vec3 direction;
};

struct PointLight{
    vec3 color;
    float strength;
    vec3 position;
    Attenuation attenuation;
};

struct SpotLight{
    vec3 color;
    float strength;
    vec3 position;
    vec3 coneDirection;
    float cutOff;
    Attenuation attenuation;
};

struct Light{
    int type;
    PointLight pointLight;
    DirectionalLight directionalLight;
    AmbientLight ambientLight;
    SpotLight spotLight;
};

uniform Light lights[10];

// toLightDirection is normalized
vec4 calculateLightColor(vec3 color, float strength, vec3 toLightDirection){
    vec4 diffuseColor=vec4(0.0);
    vec4 specularColor=vec4(0.0);

    // diffuse calculation
    float diffuseFactor=max(dot(fs_in.vxNormalVector,toLightDirection),0.0);
    diffuseColor=vec4(color,1.0 )*strength*diffuseFactor;

    // specular calculation
    vec3 cameraDirection=normalize(-fs_in.vxToCameraVector);

    // Blinn-Phong
    vec3 halfwayDir = normalize(toLightDirection + cameraDirection);
    float specularFactor = max(dot(fs_in.vxNormalVector, halfwayDir), 0.0);
    specularFactor=pow(specularFactor,fs_in.surfaceBehaviorInstanced.x);
    specularColor=strength*specularFactor*fs_in.surfaceBehaviorInstanced.y*vec4(color,1.0);

    if(diffuseColor==vec4(0.0)){
        specularColor=vec4(0.0);
    }

    return (diffuseColor+specularColor);
}

vec4 calculateAmbientLight(AmbientLight light){
    return vec4(light.strength*light.color,1.0);
}

vec4 calculateDirectionalLight(DirectionalLight light){
    return calculateLightColor(light.color, light.strength, normalize(light.direction));
}

vec4 calculatePointLight(PointLight light){
    vec3 distanceFromLightVector=light.position-fs_in.vxPosition;
    vec3 toLightDirection=normalize(distanceFromLightVector);
    vec4 color=calculateLightColor(light.color, light.strength, toLightDirection);

    // attenuation
    float distance=length(distanceFromLightVector);
    float attenuationFactor=light.attenuation.constant + (distance*light.attenuation.linear) + (distance*distance*light.attenuation.exponent);

    return color/attenuationFactor;
}

vec4 calculateSpotLight(SpotLight light){
    vec3 distanceFromLightVector=light.position-fs_in.vxPosition;
    vec3 toLightDirection=normalize(distanceFromLightVector);
    vec3 fromLightDirection=-toLightDirection;

    // how close are we to being in the spot
    float spotCos=dot(fromLightDirection,normalize(light.coneDirection));

    if(spotCos>light.cutOff){
        vec4 color=calculateLightColor(light.color, light.strength, toLightDirection);

        float distance=length(distanceFromLightVector);
        float attenuationFactor=light.attenuation.constant + (distance*light.attenuation.linear) + (distance*distance*light.attenuation.exponent);
        color=color/attenuationFactor;
        color*=(1.0-(1.0-spotCos)/(1.0-light.cutOff));
        return color;
    }

    return vec4(0.0);
}

void main()
{
    if(polygonMode){
        FINAL_COLOR=vec4(0.5f,0.5f,0.5f,1.0f);
    }
    else{
        vec4 totalLight=vec4(0.0);
        vec4 ambientLightTotal=vec4(0.0);

        for(int i=0;i<10;i++){
            Light light=lights[i];
            if(light.type==1){
                ambientLightTotal+=calculateAmbientLight(light.ambientLight);
            }
            else if(light.type==2){
                totalLight+=calculateDirectionalLight(light.directionalLight);
            }
            else if(light.type==3){
                totalLight+=calculatePointLight(light.pointLight);
            }
            else if(light.type==4){
                totalLight+=calculateSpotLight(light.spotLight);
            }
        }

        vec4 baseColor=vec4(0.5f);
        if(fs_in.materialType == 0) { // textured
            baseColor=texture(mainTextureSampler,fs_in.vxTexture);
        }
        else if(fs_in.materialType == 1){ // per object colored
            baseColor=vec4(fs_in.perObjectColorInstanced.xyz,1.0f);
        }

        float gamma = 2.2;
        FINAL_COLOR = min(baseColor*(totalLight+ambientLightTotal),vec4(1.0)); // min saturates at white
        FINAL_COLOR.rgb=pow(FINAL_COLOR.rgb, vec3(1.0/gamma)); // gamma correction

        if(fs_in.highlightedInstanced!=0){
            FINAL_COLOR=vec4(FINAL_COLOR.rgb*vec3(0.0,0.6,0.77),FINAL_COLOR.w);
        }
    }
}