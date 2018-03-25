package net.thegaminghuskymc.sandboxgame.engine;

import net.thegaminghuskymc.sandboxgame.engine.blocks.GameBlock;
import net.thegaminghuskymc.sandboxgame.engine.graph.InstancedMesh;
import net.thegaminghuskymc.sandboxgame.engine.graph.Mesh;
import net.thegaminghuskymc.sandboxgame.engine.graph.particles.IParticleEmitter;
import net.thegaminghuskymc.sandboxgame.engine.graph.weather.Fog;
import net.thegaminghuskymc.sandboxgame.engine.items.GameItem;
import net.thegaminghuskymc.sandboxgame.engine.items.SkyBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scene {

    private final Map<Mesh, List<GameItem>> meshMapItem;

    private final Map<InstancedMesh, List<GameItem>> instancedMeshMapItem;

    private final Map<Mesh, List<GameBlock>> meshMapBlock;

    private final Map<InstancedMesh, List<GameBlock>> instancedMeshMapBlock;

    private SkyBox skyBox;

    private SceneLight sceneLight;

    private Fog fog;

    private boolean renderShadows;
    
    private IParticleEmitter[] particleEmitters;

    public Scene() {
        meshMapItem = new HashMap<>();
        instancedMeshMapItem = new HashMap<>();
        meshMapBlock = new HashMap<>();
        instancedMeshMapBlock = new HashMap<>();
        fog = Fog.NOFOG;
        renderShadows = true;
    }

    public Map<Mesh, List<GameItem>> getGameMeshes() {
        return meshMapItem;
    }

    public Map<InstancedMesh, List<GameItem>> getGameInstancedMeshes() {
        return instancedMeshMapItem;
    }

    public Map<Mesh, List<GameBlock>> getGameMeshesBlocks() {
        return meshMapBlock;
    }

    public Map<InstancedMesh, List<GameBlock>> getGameInstancedMeshesBlocks() {
        return instancedMeshMapBlock;
    }

    public boolean isRenderShadows() {
        return renderShadows;
    }

    public void setGameItems(GameItem[] gameItems) {
        // Create a map of meshes to speed up rendering
        int numGameItems = gameItems != null ? gameItems.length : 0;
        for (int i = 0; i < numGameItems; i++) {
            GameItem gameItem = gameItems[i];
            Mesh[] meshes = gameItem.getMeshes();
            for (Mesh mesh : meshes) {
                boolean instancedMesh = mesh instanceof InstancedMesh;
                List<GameItem> list = instancedMesh ? instancedMeshMapItem.get(mesh) : meshMapItem.get(mesh);
                if (list == null) {
                    list = new ArrayList<>();
                    if (instancedMesh) {
                        instancedMeshMapItem.put((InstancedMesh)mesh, list);
                    } else {
                        meshMapItem.put(mesh, list);
                    }
                }
                list.add(gameItem);
            }
        }
    }

    public void setBlocks(GameBlock[] gameItems) {
        // Create a map of meshes to speed up rendering
        int numGameItems = gameItems != null ? gameItems.length : 0;
        for (int i = 0; i < numGameItems; i++) {
            GameBlock gameItem = gameItems[i];
            Mesh[] meshes = gameItem.getMeshes();
            for (Mesh mesh : meshes) {
                boolean instancedMesh = mesh instanceof InstancedMesh;
                List<GameBlock> list = instancedMesh ? instancedMeshMapBlock.get(mesh) : meshMapBlock.get(mesh);
                if (list == null) {
                    list = new ArrayList<>();
                    if (instancedMesh) {
                        instancedMeshMapBlock.put((InstancedMesh)mesh, list);
                    } else {
                        meshMapBlock.put(mesh, list);
                    }
                }
                list.add(gameItem);
            }
        }
    }

    public void cleanup() {
        for (Mesh mesh : meshMapItem.keySet()) {
            mesh.cleanUp();
        }
        for (Mesh mesh : instancedMeshMapItem.keySet()) {
            mesh.cleanUp();
        }
        for (Mesh mesh : meshMapBlock.keySet()) {
            mesh.cleanUp();
        }
        for (Mesh mesh : instancedMeshMapBlock.keySet()) {
            mesh.cleanUp();
        }
        if (particleEmitters != null) {
            for (IParticleEmitter particleEmitter : particleEmitters) {
                particleEmitter.cleanup();
            }
        }
    }

    public SkyBox getSkyBox() {
        return skyBox;
    }

    public void setRenderShadows(boolean renderShadows) {
        this.renderShadows = renderShadows;
    }

    public void setSkyBox(SkyBox skyBox) {
        this.skyBox = skyBox;
    }

    public SceneLight getSceneLight() {
        return sceneLight;
    }

    public void setSceneLight(SceneLight sceneLight) {
        this.sceneLight = sceneLight;
    }

    /**
     * @return the fog
     */
    public Fog getFog() {
        return fog;
    }

    /**
     * @param fog the fog to set
     */
    public void setFog(Fog fog) {
        this.fog = fog;
    }

    public IParticleEmitter[] getParticleEmitters() {
        return particleEmitters;
    }

    public void setParticleEmitters(IParticleEmitter[] particleEmitters) {
        this.particleEmitters = particleEmitters;
    }

}
