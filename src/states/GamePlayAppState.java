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
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import customcontrols.BreakerBarControl;
import customcontrols.BreakerControl;
import mygame.Arkanoid;
import mygame.Breaker;
import mygame.Spaceship;

/**
 * Init principal entities, audio effects and key inputs
 * 
 * @author nicolas
 */
public class GamePlayAppState extends AbstractAppState{
    
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
    
    private AudioNode audio_crash;
    private AudioNode audio_rebound;
    
    private static Vector3f CAM_LOCATION = new Vector3f(-0.005f, 0.52f, 3.19f);
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        
        this.stateManager = stateManager;
        this.app = (SimpleApplication)app;
        this.assetManager = ((SimpleApplication)app).getAssetManager();
        
        currentLives = INITIAL_DEFAULT_LIVES;
        
        initMainEntities();
        
        
//        ((SimpleApplication)app).getRootNode().attachChild(ball);
        
        InputAppState inputState = new InputAppState();
        stateManager.attach(inputState);
        
        configureCameraSettings();
        initAudio();
        initSceneLights();
    }
    
    private void initMainEntities(){
        arkanoid = new Arkanoid(assetManager);
        arkanoid.addControl(new BreakerBarControl(app.getRootNode(), stateManager));
        ball = new Breaker(assetManager);
        ball.addControl(new BreakerControl(app.getRootNode(), stateManager));
        
        spaceship = new Spaceship(assetManager);
        //spaceship.addControl(new SpaceshipControl());
        
        
        breakerBarNode.attachChild(arkanoid);
//        spaceship.createTurbo();
        breakerBarNode.attachChild(ball);
        
        app.getRootNode().attachChild(breakerBarNode);

    }
    
    private void configureCameraSettings(){
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
    
    private void initSceneLights(){
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
    
    public void restLife(){
        this.currentLives--;
    }
    
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score += score;
        stateManager.getState(GameGuiAppState.class).updateScoreIndicator();
    }

    public String getFormattedScore(){
        return String.format("%08d", score);
    }
    
    public void reset(){
        restLife();
  
        //Set entities to initial position
        ((Node)app.getRootNode().getChild("BreakerBarNode")).detachAllChildren();
        
        //Call GUI state for update
        stateManager.getState(GameGuiAppState.class).updateLivesIndicator(app, currentLives);
              
        //Hacer algun efecto de explosion
        
        
        ((Node)app.getRootNode().getChild("BreakerBarNode")).attachChild(arkanoid);
//        arkanoid.setLocalTranslation(Arkanoid.getInitialPosition());
        ((Node)app.getRootNode().getChild("BreakerBarNode")).attachChild(ball);
        ball.setLocalTranslation(Breaker.getInitialPosition());
        
        setGameStarted(Boolean.FALSE);
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
    
    
    
}
