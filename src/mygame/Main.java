package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.audio.AudioNode;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;

import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial.CullHint;
import com.jme3.scene.shape.Box;
import customcontrols.BreakerControl;
import states.AppGuiState;
import states.AppInitState;
import states.AppPlayerState;

/**
 * test
 *
 * @author normenhansen
 */
public class Main extends SimpleApplication /*implements PhysicsCollisionListener*/ {

    private Node bricks;
    private Breaker breaker;
    private BreakerBar breakerBar;

    //Messages
    private BitmapText livesCount;
    private BitmapText stateText;
    private BitmapText scoreCount;
    //Audio nodes
    private AudioNode audio_crash;
    private AudioNode audio_rebound;

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        bricks = new Node("BricksNode");
        rootNode.attachChild(bricks);

        //Init components
        flyCam.setEnabled(false);
        cam.setLocation(new Vector3f(-0.005f, 0.52f, 3.19f));
        //viewPort.setBackgroundColor(new ColorRGBA(236f / 255f, 246f / 255f, 180f / 255f, 1f));
        
        initAudio();
        initKeys();
        initScene();
        makeWall();
        setSceneLights(cam);
        
        //BreakerBar
        breakerBar = new BreakerBar(assetManager); 
        rootNode.attachChild(breakerBar);
        
        //Breaker
        breaker = new Breaker(assetManager);
        breaker.addControl(new BreakerControl(rootNode, breakerBar));
        rootNode.attachChild(breaker);
        
        AppInitState initState = new AppInitState();
        stateManager.attach(initState);
        
        System.out.println(rootNode.getChildren().size());
        
        AppPlayerState playerState = new AppPlayerState();
        stateManager.attach(playerState);
        AppGuiState guiState = new AppGuiState();
        stateManager.attach(guiState);

    }

    private void initAudio() {
        audio_crash = new AudioNode(assetManager, "Sounds/effects/metal-hammer-hit-01.wav", false);
        audio_crash.setPositional(false);
        audio_crash.setLooping(false);
        audio_crash.setVolume(2);
        rootNode.attachChild(audio_crash);

        audio_rebound = new AudioNode(assetManager, "Sounds/effects/bottle-glass-uncork-01.wav", false);
        audio_rebound.setPositional(false);
        audio_rebound.setLooping(false);
        audio_rebound.setVolume(2);
        rootNode.attachChild(audio_rebound);
    }

    private void makeWall() {
        float initialX = -0.60f;
        Vector3f position = new Vector3f(initialX, 0.60f, 1f);
        Brick brick = null;
        //rows
        for (int i = 0; i <= 5; i++) {
            
            //bricks per line
            for (int j = 0; j <= 7; j++) {
                if(i == 5){
                    brick = new MetallicBrick(assetManager, position);
                }else{
                    brick = new CommonBrick(assetManager, position, i);
                }
                
                bricks.attachChild(brick);

                //Calculate next position
                position.setX(brick.getLocalTranslation().getX() + Brick.getWidth() * 2 + 0.02f);
            }
            position.setX(initialX);
            position.setY(position.getY() + Brick.getHeight() * 2 + 0.04f);
        }
        
        
        
        Brick.selectSpecialBrick(assetManager, bricks);
    }

    private void initScene() {
        //Floor
        Box floor = new Box(2.0f, 0.01f, 2.0f);
        Geometry geomFloor = new Geometry("Floor", floor);
        geomFloor.setLocalTranslation(0.0f, -0.1f, 0.0f);
        Material matFloor = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        matFloor.setColor("Color", new ColorRGBA(23f / 255f, 207f / 255f, 246f / 255f, 1f));

        geomFloor.setMaterial(matFloor);
        geomFloor.setCullHint(CullHint.Always);
        rootNode.attachChild(geomFloor);

        //Bars
        Material matBars = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        matBars.setColor("Color", new ColorRGBA(198f / 255f, 9f / 255f, 26f / 255f, 1f));

        Box leftBar = new Box(0.025f, 0.60f, 0.025f);
        Geometry geomLeftBar = new Geometry("LeftBar", leftBar);
        geomLeftBar.setLocalTranslation(new Vector3f(-0.75f, 0.60f, 1f));

        geomLeftBar.setMaterial(matBars);

        rootNode.attachChild(geomLeftBar);

        Box rightBar = new Box(0.025f, 0.60f, 0.025f);
        Geometry geomRightBar = new Geometry("RightBar", rightBar);
        geomRightBar.setLocalTranslation(new Vector3f(0.75f, 0.60f, 1f));

        geomRightBar.setMaterial(matBars);
        rootNode.attachChild(geomRightBar);

        Box topBar = new Box(0.85f, 0.025f, 0.025f);
        Geometry geomTopBar = new Geometry("TopBar", topBar);
        geomTopBar.setLocalTranslation(new Vector3f(0.0f, 1.2f, 1f));

        geomTopBar.setMaterial(matBars);
        rootNode.attachChild(geomTopBar);
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
        if (breakerBar.getLifes() == 0 && !bricks.getChildren().isEmpty()) {
//            stateText.setText("You Lose :(");
            resetBall();
        }

        if (bricks.getChildren().isEmpty()) {
            if (breakerBar.getLifes() != 0) {
//                stateText.setText("You Win :)");
                resetBall();
            }
        }
        
//        livesCount.setText("Vidas: " + breakerBar.getLifes());
//        scoreCount.setText("Puntuación: " + breakerBar.getFormattedScore());  
        
//        System.out.println(cam.getDirection());
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }

    private void initKeys() {
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_LEFT));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_RIGHT));
        inputManager.addMapping("Shoot", new KeyTrigger(KeyInput.KEY_SPACE));

        inputManager.addListener(analogListener, "Left", "Right");
        inputManager.addListener(actionListener, "Shoot");
    }
    
    private AnalogListener analogListener = new AnalogListener() {
        public void onAnalog(String name, float value, float tpf) {
            breakerBar.move(name, value, breaker);
        }
    };
    
    private ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean isPressed, float tpf) {
            if (name.equals("Shoot") && !isPressed && !breakerBar.isBallShooted()) {
                breakerBar.setBallShooted(Boolean.TRUE);
            }
        }
    };


    private void resetBall() {
        //Remove ball from scene
        breaker.removeFromParent();
        //bulletAppState.getPhysicsSpace().remove(breaker.getBreaker_phy());

        //Set ball initial location
        breakerBar.setBallShooted(false);
        breaker = new Breaker(assetManager);
        rootNode.attachChild(breaker);
    }
    
    /**
     * scene lights
     */
    
    private void setSceneLights(Camera cam){
        DirectionalLight sun = new DirectionalLight();
        sun.setColor(ColorRGBA.White.mult(0.7f));
        
        //sun.setDirection(new Vector3f(0.6679365f, -0.04685749f, -0.7427416f));
        sun.setDirection(cam.getDirection().normalizeLocal());
        rootNode.addLight(sun);
        
        AmbientLight al = new AmbientLight();
        al.setColor(ColorRGBA.White);
        rootNode.addLight(al);
    }
}
