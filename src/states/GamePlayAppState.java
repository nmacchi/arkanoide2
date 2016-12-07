/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package states;

import effects.ScriptAppState;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import customcontrols.BreakerControl;
import effects.AudioEffects;
import effects.VisualEffects;
import factories.BreakerBarFactory;
import mygame.entities.Arkanoid;
import mygame.entities.Breaker;

/**
 * Init principal entities, audio effects and key inputs
 *
 * @author nicolas
 */
public class GamePlayAppState extends AbstractAppState {

    private boolean gameStarted;
    private boolean gameFinished;
    private boolean gameStop;
    
    private Node breakerBarNode = new Node("BreakerBarNode");
    private Node breakerNode = new Node("BreakerNode");
    private Node powerupsNode = new Node("PowerupsNode");
    private Node gamefield = new Node("Gamefield");
    
    
//    private Arkanoid arkanoid;
//    private Breaker ball;
//    private Spaceship spaceship;
    
    AppStateManager stateManager;
    SimpleApplication app;
    AssetManager assetManager;
    
    GameGuiAppState guiAppState;
    InputAppState inputState;
    PlayerState playerState;
    ScriptAppState scriptAppState;
    LostLifeState lostLifeState;
    
    BreakerBarFactory breakerBarCreator;
    
    private AudioNode audio_crash;
    private AudioNode audio_rebound;
    private static Vector3f CAM_LOCATION = new Vector3f(-0.005f, 0.52f, 3.19f);
    
//    private int state;
//    private float timeElapsed;
    
//    private VisualEffects visualEffects;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);

        this.stateManager = stateManager;
        this.app = (SimpleApplication) app;
        this.assetManager = ((SimpleApplication) app).getAssetManager();
        this.breakerBarCreator = new BreakerBarFactory();
        
        this.app.getRootNode().attachChild(breakerBarNode);
        this.app.getRootNode().attachChild(breakerNode);
        this.app.getRootNode().attachChild(powerupsNode);
        this.app.getRootNode().attachChild(gamefield);
        
        initScene();
        
        initVisualEffects();
        
        initMainEntities();
        
        lostLifeState = new LostLifeState(); 
        guiAppState = new GameGuiAppState();
        playerState = new PlayerState();
        inputState = new InputAppState();
        
        stateManager.attachAll(playerState, inputState, guiAppState, lostLifeState);
        
        configureCameraSettings();
        initAudio();
        initSceneLights();
        
    }
    
    private void initVisualEffects(){
       VisualEffects ve = new VisualEffects();
       ve.initVisualEffect(assetManager, app.getRootNode());
    }

    private void initMainEntities() {
        breakerBarCreator.createrBar(Arkanoid.class.getSimpleName(), breakerBarNode, app, null);
        breakerBarNode.attachChild(new Breaker(assetManager));
        
//        arkanoid = new Arkanoid(assetManager);
//        ball = new Breaker(assetManager);
//        spaceship = new Spaceship(assetManager);
//        
//        arkanoid.addControl(new BreakerBarControl(app.getRootNode(), stateManager));
//        
//        breakerBarNode.attachChild(arkanoid);
//        breakerBarNode.attachChild(ball);
 
        
    }

    private void configureCameraSettings() {
        app.getFlyByCamera().setEnabled(false);
        app.getCamera().setLocation(CAM_LOCATION);
    }

    private void initAudio() {
        AudioEffects audioEffects = new AudioEffects(assetManager, app.getRootNode());
        audioEffects.loadAudioFXs();
        
//        audio_crash = new AudioNode(assetManager, "Sounds/effects/metal-hammer-hit-01.wav", false);
//        audio_crash.setPositional(false);
//        audio_crash.setLooping(false);
//        audio_crash.setVolume(2);
//        app.getRootNode().attachChild(audio_crash);
//
//        audio_rebound = new AudioNode(assetManager, "Sounds/effects/bottle-glass-uncork-01.wav", false);
//        audio_rebound.setPositional(false);
//        audio_rebound.setLooping(false);
//        audio_rebound.setVolume(2);
//        app.getRootNode().attachChild(audio_rebound);
    }

    private void initSceneLights() {
        DirectionalLight sun = new DirectionalLight();
        sun.setColor(ColorRGBA.White.mult(0.7f));

        sun.setDirection(app.getCamera().getDirection().normalizeLocal());
        app.getRootNode().addLight(sun);

        AmbientLight al = new AmbientLight();
        al.setColor(ColorRGBA.White);
        app.getRootNode().addLight(al);
    }
    
//    public Arkanoid getArkanoid() {
//        return arkanoid;
//    }
//
//    public Breaker getBall() {
//        return ball;
//    }

    public void reset() {
        playerState.restLife();
        
//        ((Node) app.getRootNode().getChild("BreakerBarNode")).attachChild(arkanoid);
//        arkanoid.setLocalTranslation(Arkanoid.getInitialPosition());
//        ((Node) app.getRootNode().getChild("BreakerBarNode")).attachChild(new Breaker(assetManager));
        
        initMainEntities();
        VisualEffects.getChangeEffect(((Geometry)breakerBarNode.getChild(0)).getWorldTranslation());
        
        stateManager.attach(inputState);
        this.setGameStop(Boolean.FALSE);

    }

//    public Spaceship getSpaceship() {
//        return spaceship;
//    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }

    public SimpleApplication getApp() {
        return app;
    }

    @Override
    public void update(float tpf) {
        
        //Exceute state when player lose a life
        if (isGameStop() && !lostLifeState.isEnabled()) {
            lostLifeState.setEnabled(Boolean.TRUE);
        }    
        
        //Check if all bricks were removed
        //TODO: Cuando no haya mas ladrillos pasar al proximo nivel
        if(((Node)app.getRootNode().getChild("BricksNode")).getChildren().isEmpty()){
            stateManager.detach(stateManager.getState(InputAppState.class));
            gameStarted = Boolean.FALSE;
        }
        
        //Game is finished when player lose all his lifes
        //TODO: Permitir cntinuar o salir (implementar un menu)
        if(isGameFinished()){
            stateManager.detach(stateManager.getState(InputAppState.class));
        }
    }

    public InputAppState getInputState() {
        return inputState;
    }

    public boolean isGameStop() {
        return gameStop;
    }

    public void setGameStop(boolean gameStop) {
        this.gameStop = gameStop;
    }

    public boolean isGameFinished() {
        return gameFinished;
    }

    public void setGameFinished(boolean gameFinished) {
        this.gameFinished = gameFinished;
    }
    
    
    
    public void addExtraBalls(){ 
        Node node = (Node) app.getRootNode().getChild("BreakerNode");
        Breaker breaker = (Breaker) app.getRootNode().getChild("Breaker");

        for (int i = 0; i < 2; i++) {
            Quaternion quat = new Quaternion();
            Vector3f direction = new Vector3f();

            int rotationDegree;
            if (node.getChildren().size() < 1) {
                rotationDegree = 10;
            } else {
                rotationDegree = -10;
            }

            quat.fromAngleAxis(FastMath.PI * rotationDegree / 180, Vector3f.UNIT_Z);
            quat.mult(breaker.getDirection(), direction);

            Breaker extraBall = new Breaker(stateManager.getApplication().getAssetManager(), breaker.getLocalTranslation(), breaker.getSpeed(), direction);
            extraBall.addControl(new BreakerControl(app.getRootNode(), stateManager));
            node.attachChild(extraBall);
        }
    }
    
    
    public void removeExtraballsFromScene(){
        //Node breakerNode = (Node) app.getRootNode().getChild("BreakerNode");

        //Hay mas de una bolita activa
        //if (breakerNode.getChildren().size() > 1) {

//            List<Spatial> extraBalls = breakerNode.getChildren();
//            
            int i = 0;
            while(breakerNode.getChildren().size() > 1){
                Breaker extraBall = (Breaker)breakerNode.getChild(i);
                extraBall.executeExplosionEffect(extraBall.getWorldTranslation());
                extraBall.removeFromParent();
                
                i++;
            }
            
    }
    
     private void initScene() {
        //Floor
        Box floor = new Box(2.0f, 0.01f, 2.0f);
        Geometry geomFloor = new Geometry("Floor", floor);
        geomFloor.setLocalTranslation(0.0f, -0.12f, 0.0f);
        Material matFloor = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        matFloor.setColor("Color", new ColorRGBA(23f / 255f, 207f / 255f, 246f / 255f, 1f));

        geomFloor.setMaterial(matFloor);
        geomFloor.setCullHint(Spatial.CullHint.Always);
        gamefield.attachChild(geomFloor);

        //Bars
        Material matBars = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        matBars.setColor("Color", new ColorRGBA(198f / 255f, 9f / 255f, 26f / 255f, 1f));

        Box leftBar = new Box(0.025f, 0.66f, 0.025f);
        Geometry geomLeftBar = new Geometry("LeftBar", leftBar);
        geomLeftBar.setLocalTranslation(new Vector3f(-0.75f, 0.64f, 1f));

        geomLeftBar.setMaterial(matBars);

        gamefield.attachChild(geomLeftBar);

        Box rightBar = new Box(0.025f, 0.66f, 0.025f);
        Geometry geomRightBar = new Geometry("RightBar", rightBar);
        geomRightBar.setLocalTranslation(new Vector3f(0.80f, 0.64f, 1f));

        geomRightBar.setMaterial(matBars);
        gamefield.attachChild(geomRightBar);

        Box topBar = new Box(0.85f, 0.025f, 0.025f);
        Geometry geomTopBar = new Geometry("TopBar", topBar);
        geomTopBar.setLocalTranslation(new Vector3f(0.0f, 1.33f, 1f));

        geomTopBar.setMaterial(matBars);
        gamefield.attachChild(geomTopBar);
    }
}
