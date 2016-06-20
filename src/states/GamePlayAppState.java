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
import customcontrols.BreakerControl;
import customcontrols.SpaceshipControl;
import mygame.Arkanoid;
import mygame.Breaker;
import mygame.BreakerBar;
import mygame.Spaceship;

/**
 * Init principal entities, audio effects and key inputs
 * 
 * @author nicolas
 */
public class GamePlayAppState extends AbstractAppState{
    
    private static int INITIAL_DEFAULT_LIVES = 3;
    
    private int currentLives;
    private int score;
    
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
        
//        System.out.println(assetManager.loadModel("Models/spaceship/Cube.mesh.j3o"));
        
//        Geometry geometry = (Geometry)((Node)assetManager.loadModel("Models/spaceship/Cube.mesh.j3o")).getChild(0);
////        
//        geometry.scale(0.10f, 0.07f, 0.055f);
//        geometry.rotate(0f,1.60f,0f);
//        
//        ((SimpleApplication)app).getRootNode().attachChild(geometry);
        
        arkanoid = new Arkanoid(app.getAssetManager());
        ball = new Breaker(app.getAssetManager());
        ball.addControl(new BreakerControl(((SimpleApplication)app).getRootNode(), arkanoid, stateManager));
        spaceship = new Spaceship(app.getAssetManager());
        spaceship.addControl(new SpaceshipControl());
        
        ((SimpleApplication)app).getRootNode().attachChild(spaceship);
        ((SimpleApplication)app).getRootNode().attachChild(ball);
        
        InputAppState inputState = new InputAppState();
        stateManager.attach(inputState);
        
        configureCameraSettings();
        initAudio();
        initSceneLights();
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
    
    public BreakerBar getArkanoid() {
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
        ((BreakerBar)app.getRootNode().getChild("BreakerBar")).removeFromParent();
        ((Breaker)app.getRootNode().getChild("Breaker")).removeFromParent();
        
        
        //Call GUI state for update
        stateManager.getState(GameGuiAppState.class).updateLivesIndicator(app, currentLives);
              
        //Hacer algun efecto de explosion
        
        app.getRootNode().attachChild(getArkanoid());
        arkanoid.setLocalTranslation(Arkanoid.getInitialPosition());
        arkanoid.setBallShooted(Boolean.FALSE);
        app.getRootNode().attachChild(getBall());
        ball.setLocalTranslation(Breaker.getInitialPosition());

    }

    public Spaceship getSpaceship() {
        return spaceship;
    }
    
    
}
