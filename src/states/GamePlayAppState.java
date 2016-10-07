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
import com.jme3.effect.ParticleEmitter;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import customcontrols.BreakerBarControl;
import customcontrols.BreakerControl;
import effects.VisualEffects;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mygame.entities.Arkanoid;
import mygame.entities.Breaker;
import mygame.entities.Spaceship;

/**
 * Init principal entities, audio effects and key inputs
 *
 * @author nicolas
 */
public class GamePlayAppState extends AbstractAppState {

    private boolean gameStarted;
    
    private Node breakerBarNode = new Node("BreakerBarNode");
    private Node breakerNode = new Node("BreakerNode");
    private Arkanoid arkanoid;
    private Breaker ball;
    private Spaceship spaceship;
    
    AppStateManager stateManager;
    SimpleApplication app;
    AssetManager assetManager;
    
    InputAppState inputState;
    PlayerState playerState;
    ScriptAppState scriptAppState;
    
    private AudioNode audio_crash;
    private AudioNode audio_rebound;
    private static Vector3f CAM_LOCATION = new Vector3f(-0.005f, 0.52f, 3.19f);
    
    private int state;
    private float timeElapsed;
    private boolean stopGame;
//    private VisualEffects visualEffects;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);

        this.stateManager = stateManager;
        this.app = (SimpleApplication) app;
        this.assetManager = ((SimpleApplication) app).getAssetManager();

        initMainEntities();

        inputState = new InputAppState();
        stateManager.attach(inputState);
        
        playerState = new PlayerState();
        stateManager.attach(playerState);
        
        configureCameraSettings();
        initAudio();
        initSceneLights();
    }

    private void initMainEntities() {
        arkanoid = new Arkanoid(assetManager);
        ball = new Breaker(assetManager);
        
        arkanoid.addControl(new BreakerBarControl(app.getRootNode(), stateManager));
        ball.addControl(new BreakerControl(app.getRootNode(), stateManager));
//        spaceship = new Spaceship(assetManager);

        breakerBarNode.attachChild(arkanoid);
        breakerBarNode.attachChild(ball);
 
        app.getRootNode().attachChild(breakerBarNode);
        app.getRootNode().attachChild(breakerNode);
    }

    private void configureCameraSettings() {
        app.getFlyByCamera().setEnabled(false);
        app.getCamera().setLocation(CAM_LOCATION);
    }

    private void initAudio() {
        audio_crash = new AudioNode(assetManager, "Sounds/effects/metal-hammer-hit-01.wav", false);
        audio_crash.setPositional(false);
        audio_crash.setLooping(false);
        audio_crash.setVolume(2);
        app.getRootNode().attachChild(audio_crash);

        audio_rebound = new AudioNode(assetManager, "Sounds/effects/bottle-glass-uncork-01.wav", false);
        audio_rebound.setPositional(false);
        audio_rebound.setLooping(false);
        audio_rebound.setVolume(2);
        app.getRootNode().attachChild(audio_rebound);
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
    
    public Arkanoid getArkanoid() {
        return arkanoid;
    }

    public Breaker getBall() {
        return ball;
    }

    public void reset() {
        playerState.restLife();
        
        ((Node) app.getRootNode().getChild("BreakerBarNode")).attachChild(arkanoid);
        arkanoid.setLocalTranslation(Arkanoid.getInitialPosition());
        ((Node) app.getRootNode().getChild("BreakerBarNode")).attachChild(ball);
        ball.setLocalTranslation(Breaker.getInitialPosition());

        stateManager.attach(inputState);
        timeElapsed = 0;
        state = 0;
    }

    public Spaceship getSpaceship() {
        return spaceship;
    }

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
        
        if (isStopGame()) {
          
            timeElapsed += tpf / 1;

            if (state == 0) {

                Vector3f position = ((Geometry) ((Node) app.getRootNode().getChild("BreakerBarNode")).getChild(0)).getWorldTranslation();
                
                //Reset entites
                ((Node) app.getRootNode().getChild("BreakerBarNode")).detachAllChildren();
                app.getRootNode().detachChildNamed("Breaker");
                
                //Build multiple effect trigger
                Map<Float, List<ParticleEmitter>> customTriggerEffect = new HashMap<Float, List<ParticleEmitter>>();
                customTriggerEffect.put(0.25f, Arrays.asList(VisualEffects.getFlash(position), VisualEffects.getSpark(position),VisualEffects.getSmoketrail(position), VisualEffects.getDebris(position), VisualEffects.getShockwave(position)));
                customTriggerEffect.put(0.3f, Arrays.asList(VisualEffects.getFlash(position), VisualEffects.getRoundspark(position)));
                
                ScriptAppState appState = new ScriptAppState(stateManager);
                appState.setCustomTriggerEffect(customTriggerEffect);
                stateManager.attach(appState);
                
                state = 1;
            }

            if (timeElapsed >= 4f) {
                setStopGame(Boolean.FALSE);
                app.getRootNode().detachChildNamed("explosionFX");
                reset();
            }
        }
    }

    public InputAppState getInputState() {
        return inputState;
    }

    public boolean isStopGame() {
        return stopGame;
    }

    public void setStopGame(boolean stopGame) {
        this.stopGame = stopGame;
    }
    
    public void addExtraBalls(){ 
        Node extraBallsNode = new Node("ExtraBalls");
        
        Vector3f position = ball.getWorldTranslation(); 
        Vector3f currentDirection = ball.getDirection();
        Vector3f direction1 = new Vector3f();
        Vector3f direction2 = new Vector3f();
        
        Quaternion quat = new Quaternion();
        quat.fromAngleAxis(FastMath.PI * 10 / 180, Vector3f.UNIT_Z);
        quat.mult(currentDirection, direction1);

        quat.fromAngleAxis(FastMath.PI * -10 / 180, Vector3f.UNIT_Z);
        quat.mult(currentDirection, direction2);
        
        Breaker extraBall1 = new Breaker(assetManager, position, ball.getSpeed(), direction1);
        Breaker extraBall2 = new Breaker(assetManager, position, ball.getSpeed(), direction2);
        
        extraBall1.addControl(new BreakerControl(app.getRootNode(), stateManager));
        extraBallsNode.attachChild(extraBall1);
        extraBall2.addControl(new BreakerControl(app.getRootNode(), stateManager));
        extraBallsNode.attachChild(extraBall2);
        
        app.getRootNode().attachChild(extraBallsNode);
    }
    
}
