/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package states;

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
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.debug.WireBox;
import customcontrols.ArkanoidExplosionFXControl;
import customcontrols.BreakerBarControl;
import customcontrols.BreakerControl;
import effects.ArkanoidExplosion;
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
    private static int INITIAL_DEFAULT_LIVES = 3;
    private int currentLives;
    private int score;
    
    private Node breakerBarNode = new Node("BreakerBarNode");
    private Arkanoid arkanoid;
    private Breaker ball;
    private Spaceship spaceship;
    
    AppStateManager stateManager;
    SimpleApplication app;
    AssetManager assetManager;
    InputAppState inputState;
    
    private AudioNode audio_crash;
    private AudioNode audio_rebound;
    private static Vector3f CAM_LOCATION = new Vector3f(-0.005f, 0.52f, 3.19f);
    
    private int state;
    private float timeElapsed;
    private boolean stopGame;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);

        this.stateManager = stateManager;
        this.app = (SimpleApplication) app;
        this.assetManager = ((SimpleApplication) app).getAssetManager();

        currentLives = INITIAL_DEFAULT_LIVES;

        initMainEntities();

        inputState = new InputAppState();
        stateManager.attach(inputState);

        configureCameraSettings();
        initAudio();
        initSceneLights();
    }

    private void initMainEntities() {
        arkanoid = new Arkanoid(assetManager);
        arkanoid.addControl(new BreakerBarControl(app.getRootNode(), stateManager));
        ball = new Breaker(assetManager);
        ball.addControl(new BreakerControl(app.getRootNode(), stateManager, assetManager));
        spaceship = new Spaceship(assetManager);

        breakerBarNode.attachChild(arkanoid);
        breakerBarNode.attachChild(ball);
        
        putBox(arkanoid.getLocalTranslation(), 0.5f, ColorRGBA.Yellow);
        
        app.getRootNode().attachChild(breakerBarNode);
        
        
        
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

    public int getCurrentLives() {
        return currentLives;
    }

    public void setCurrentLives(int currentLives) {
        this.currentLives = currentLives;
    }

    public void restLife() {
        this.currentLives--;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score += score;
        stateManager.getState(GameGuiAppState.class).updateScoreIndicator();
    }

    public String getFormattedScore() {
        return String.format("%08d", score);
    }

    public void reset() {
        restLife();
        
        //Call GUI state for update
        stateManager.getState(GameGuiAppState.class).updateLivesIndicator(app, currentLives);

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

    @Override
    public void update(float tpf) {
        if (isStopGame()) {
            timeElapsed += tpf / 1;

            if (state == 0) {

                Vector3f position = ((Geometry) ((Node) app.getRootNode().getChild("BreakerBarNode")).getChild(0)).getWorldTranslation();
                
                ((Node) app.getRootNode().getChild("BreakerBarNode")).detachAllChildren();
                app.getRootNode().detachChildNamed("Breaker");

                ArkanoidExplosion explosionFX = new ArkanoidExplosion(assetManager, position, app.getRootNode());
                explosionFX.getExplosionEffect().addControl(new ArkanoidExplosionFXControl());

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
    
    
    
       /**
     * TEST
     */
    
    
    public void putBox(Vector3f pos, float size, ColorRGBA color){
        putShape(new WireBox(0.10f, 0.02f, 0.08f), color).setLocalTranslation(pos);
    }
    
     public Geometry putShape(Mesh shape, ColorRGBA color){
        Geometry g = new Geometry("shape", shape);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.getAdditionalRenderState().setWireframe(true);
        mat.setColor("Color", color);
        g.setMaterial(mat);
        breakerBarNode.attachChild(g);
        return g;
    }
}
