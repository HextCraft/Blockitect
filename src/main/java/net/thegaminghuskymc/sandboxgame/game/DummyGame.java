package net.thegaminghuskymc.sandboxgame.game;

import de.matthiasmann.twl.utils.PNGDecoder;
import java.nio.ByteBuffer;

import net.thegaminghuskymc.sandboxgame.engine.*;
import net.thegaminghuskymc.sandboxgame.engine.blocks.GameBlock;
import net.thegaminghuskymc.sandboxgame.engine.graph.*;
import net.thegaminghuskymc.sandboxgame.engine.graph.lights.DirectionalLight;
import net.thegaminghuskymc.sandboxgame.engine.graph.particles.FlowParticleEmitter;
import net.thegaminghuskymc.sandboxgame.engine.graph.particles.Particle;
import net.thegaminghuskymc.sandboxgame.engine.graph.weather.Fog;
import net.thegaminghuskymc.sandboxgame.engine.items.GameItem;
import net.thegaminghuskymc.sandboxgame.engine.items.SkyBox;
import net.thegaminghuskymc.sandboxgame.engine.loaders.obj.OBJLoader;
import net.thegaminghuskymc.sandboxgame.engine.sound.SoundBuffer;
import net.thegaminghuskymc.sandboxgame.engine.sound.SoundListener;
import net.thegaminghuskymc.sandboxgame.engine.sound.SoundManager;
import net.thegaminghuskymc.sandboxgame.engine.sound.SoundSource;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.openal.AL11;

public class DummyGame implements IGameLogic {

    private static final float MOUSE_SENSITIVITY = 0.2f;

    private final Vector3f cameraInc;

    private final Renderer renderer;

    private final SoundManager soundMgr;

    private final Camera camera;

    private Scene scene;

    private Hud hud;

    private static final float CAMERA_POS_STEP = 0.10f;

    private float angleInc;

    private float lightAngle;

    private FlowParticleEmitter particleEmitter;

    private MouseBoxSelectionDetector selectDetector;

    private boolean leftButtonPressed;

    private boolean firstTime;

    private boolean sceneChanged;

    private static State state = State.LOADING;

    private enum Sounds {
        FIRE
    }

    private GameItem[] items;

    DummyGame() {
        renderer = new Renderer();
        hud = new Hud();
        soundMgr = new SoundManager();
        camera = new Camera();
        cameraInc = new Vector3f(0.0f, 0.0f, 0.0f);
        angleInc = 0;
        lightAngle = 90;
        firstTime = true;
        state = State.LOADING;
    }

    @Override
    public void init(Window window) throws Exception {
        hud.init(window);
        renderer.init(window);
        soundMgr.init();

        leftButtonPressed = false;

        scene = new Scene();

        float reflectance = 1f;

        float blockScale = 0.5f;
        float skyBoxScale = 100.0f;
        float extension = 2.0f;

        float startx = extension * (-skyBoxScale + blockScale);
        float startz = extension * (skyBoxScale - blockScale);
        float starty = -1.0f;
        float inc = blockScale * 2;

        float posx = startx;
        float posz = startz;
        float incy;

        selectDetector = new MouseBoxSelectionDetector();

        PNGDecoder decoder = new PNGDecoder(getClass().getResourceAsStream("/assets/blockitect/textures/heightmap.png"));
        int height = decoder.getHeight();
        int width = decoder.getWidth();
        ByteBuffer buf = ByteBuffer.allocateDirect(4 * width * height);
        decoder.decode(buf, width * 4, PNGDecoder.Format.RGBA);
        buf.flip();

        int instances = height * width;
        Mesh mesh = OBJLoader.loadMesh("/assets/blockitect/models/cube.obj", instances);
        mesh.setBoundingRadius(1);
        Texture texture = new Texture("/assets/blockitect/textures/terrain_textures.png", 2, 1);
        Material material = new Material(texture, reflectance);
        mesh.setMaterial(material);
        items = new GameItem[instances];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                GameItem gameItem = new GameItem(mesh);
                gameItem.setScale(blockScale);
                int rgb = HeightMapMesh.getRGB(i, j, width, buf);
                incy = rgb / (10 * 255 * 255);
                gameItem.setPosition(posx, starty + incy, posz);
                int textPos = Math.random() > 0.5f ? 0 : 1;
                gameItem.setTextPos(textPos);
                items[i * width + j] = gameItem;

                if(window.isKeyPressed(GLFW_KEY_K)) {
                    gameItem.setPosition(camera.getPosition().x, camera.getPosition().y + incy, camera.getPosition().z);
                }

                posx += inc;
            }
            posx = startx;
            posz -= inc;
        }
        scene.setGameItems(items);

        // Shadows
        scene.setRenderShadows(true);

        // Fog
        Vector3f fogColour = new Vector3f(0.5f, 0.5f, 0.5f);
        scene.setFog(new Fog(true, fogColour, 0.02f));

        // Setup  SkyBox
        SkyBox skyBox = new SkyBox("/assets/blockitect/models/skybox.obj", new Vector4f(0.65f, 0.65f, 0.65f, 1.0f));
        skyBox.setScale(skyBoxScale);
        scene.setSkyBox(skyBox);

        // Setup Lights
        setupLights();

        camera.getPosition().x = 0.25f;
        camera.getPosition().y = 6.5f;
        camera.getPosition().z = 6.5f;
        camera.getRotation().x = 25;
        camera.getRotation().y = -1;

        // Sounds
        this.soundMgr.init();
        this.soundMgr.setAttenuationModel(AL11.AL_EXPONENT_DISTANCE);
        setupSounds();
    }

    private void setupSounds() {
        soundMgr.setListener(new SoundListener(new Vector3f(0, 0, 0)));
    }

    private void setupLights() {
        SceneLight sceneLight = new SceneLight();
        scene.setSceneLight(sceneLight);

        // Ambient Light
        sceneLight.setAmbientLight(new Vector3f(0.3f, 0.3f, 0.3f));
        sceneLight.setSkyBoxLight(new Vector3f(1.0f, 1.0f, 1.0f));

        // Directional Light
        float lightIntensity = 1.0f;
        Vector3f lightDirection = new Vector3f(0, 1, 1);
        DirectionalLight directionalLight = new DirectionalLight(new Vector3f(1, 1, 1), lightDirection, lightIntensity);
        sceneLight.setDirectionalLight(directionalLight);
    }

    @Override
    public void input(Window window, MouseInput mouseInput) {
        sceneChanged = false;
        cameraInc.set(0, 0, 0);
        if (window.isKeyPressed(GLFW_KEY_W)) {
            sceneChanged = true;
            cameraInc.z = -4;
        } else if (window.isKeyPressed(GLFW_KEY_S)) {
            sceneChanged = true;
            cameraInc.z = 4;
        }
        if (window.isKeyPressed(GLFW_KEY_A)) {
            sceneChanged = true;
            cameraInc.x = -4;
        } else if (window.isKeyPressed(GLFW_KEY_D)) {
            sceneChanged = true;
            cameraInc.x = 4;
        }
        if (window.isKeyPressed(GLFW_KEY_LEFT_SHIFT) || window.isKeyPressed(GLFW_KEY_RIGHT_SHIFT)) {
            sceneChanged = true;
            cameraInc.y = -4;
        } else if (window.isKeyPressed(GLFW_KEY_SPACE)) {
            sceneChanged = true;
            cameraInc.y = 4;
        }
        if (window.isKeyPressed(GLFW_KEY_LEFT)) {
            sceneChanged = true;
            angleInc -= 1.05f;
        } else if (window.isKeyPressed(GLFW_KEY_RIGHT)) {
            sceneChanged = true;
            angleInc += 1.05f;
        } else {
            angleInc = 0;
        }
        if(window.isKeyPressed(GLFW_KEY_LEFT_ALT)) {
            if (state == State.LOADING)
                state = State.INTRO;
            else if (state == State.INTRO)
                state = State.MAIN_MENU;
            else if (state == State.MAIN_MENU)
                state = State.GAME;
            else if (state == State.GAME)
                state = State.INTRO;
        }
    }

    @Override
    public void update(float interval, MouseInput mouseInput, Window window) {
        if (mouseInput.isRightButtonPressed()) {
            // Update camera based on mouse            
            Vector2f rotVec = mouseInput.getDisplVec();
            camera.moveRotation(rotVec.x * MOUSE_SENSITIVITY, rotVec.y * MOUSE_SENSITIVITY, 0);
            sceneChanged = true;
        }

        // Update camera position
        camera.movePosition(cameraInc.x * CAMERA_POS_STEP, cameraInc.y * CAMERA_POS_STEP, cameraInc.z * CAMERA_POS_STEP);

        lightAngle += angleInc;
        if (lightAngle < 0) {
            lightAngle = 0;
        } else if (lightAngle > 180) {
            lightAngle = 180;
        }
        float zValue = (float) Math.cos(Math.toRadians(lightAngle));
        float yValue = (float) Math.sin(Math.toRadians(lightAngle));
        Vector3f lightDirection = this.scene.getSceneLight().getDirectionalLight().getDirection();
        lightDirection.x = 0;
        lightDirection.y = yValue;
        lightDirection.z = zValue;
        lightDirection.normalize();

//        particleEmitter.update((long) (interval * 1000));

        // Update view matrix
        camera.updateViewMatrix();

        // Update sound listener position;
        soundMgr.updateListenerPosition(camera);
    }

    @Override
    public void render(Window window) {
        if (firstTime) {
            sceneChanged = true;
            firstTime = false;
        }

        renderer.render(window, camera, scene, sceneChanged);

        /*switch (state) {
            case LOADING:
                glColor3f(0.3f, 0.3f, 0.3f);
                glRectf(-480, -854, 854, 480);
                glBegin(GL_FOG_MODE);
                break;
            case INTRO:
                glColor3f(1.0f, 0.0f, 0.0f);
                glRectf(0, 0, 854, 480);
                break;
            case GAME:
                break;
            case MAIN_MENU:
                glColor3f(0.0f, 0.0f, 1.0f);
                glRectf(0, 0, 854, 480);
                break;
        }*/

        hud.render(window);

    }

    @Override
    public void cleanup() {
        renderer.cleanup();
        soundMgr.cleanup();

        scene.cleanup();
        if (hud != null) {
            hud.cleanup();
        }
    }
}
