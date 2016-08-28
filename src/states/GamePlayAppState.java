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
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import customcontrols.ArkanoidExplosionFXControl;
import customcontrols.BreakerBarControl;
import customcontrols.BreakerControl;
import effects.ArkanoidExplosion;
import effects.SmokeTrail;
import mygame.entities.Arkanoid;
import mygame.entities.Breaker;
import mygame.entities.Spaceship;
import triggers.PlayEffect;
import triggers.Trigger;

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
    private Node breakerNode = new Node("BreakerNode");
    private Arkanoid arkanoid;
    private Breaker ball;
    private Spaceship spaceship;
    
    AppStateManager stateManager;
    SimpleApplication app;
    AssetManager assetManager;
    InputAppState inputState;
    ScriptAppState scriptAppState;
    
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
        ball.addControl(new BreakerControl(app.getRootNode(), stateManager));
        spaceship = new Spaceship(assetManager);

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

    public int getCurrentLives() {
        return currentLives;
    }

    public void setCurrentLives(int currentLives) {
        this.currentLives = currentLives;
    }

    public void restLife() {
        this.currentLives--;
    }
    
    public void addLife(){
        this.currentLives++;
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
//                explosionFX.getExplosionEffect().addControl(new ArkanoidExplosionFXControl());
                
                
                final PlayEffect flashFX = new PlayEffect(explosionFX.getFlash());
                final PlayEffect sparkFX = new PlayEffect(explosionFX.getSpark());
                final PlayEffect smoketrailFX = new PlayEffect(explosionFX.getSmoketrail());
                final PlayEffect debrisFX = new PlayEffect(explosionFX.getDebris());
                final PlayEffect shockwaveFX = new PlayEffect(explosionFX.getShockwave());
                final PlayEffect flameFX = new PlayEffect(explosionFX.getFlame());
                final PlayEffect roundsparkFX = new PlayEffect(explosionFX.getRoundspark());
       
                
                Trigger explosionTrigger = new Trigger();
                explosionTrigger.addTimerEvent(0.25f, new Trigger.TimerEvent() {
                    public Object[] call() {
                        flashFX.trigger();
                        sparkFX.trigger();
                        smoketrailFX.trigger();
                        debrisFX.trigger();
                        shockwaveFX.trigger();
                        
                        return null;
                    }     
                });
                
                explosionTrigger.addTimerEvent(0.3f, new Trigger.TimerEvent() {
                    public Object[] call() {
                        flameFX.trigger();
                        roundsparkFX.trigger();
                        
                        return null;
                    }     
                });
                
                explosionTrigger.addTimerEvent(3f, new Trigger.TimerEvent() {
                    public Object[] call() {
                        flashFX.stop();
                        sparkFX.stop();
                        smoketrailFX.stop();
                        debrisFX.stop();
                        shockwaveFX.stop();
                        flameFX.stop();
                        roundsparkFX.stop();
                        
                        return null;
                    }     
                });
                
                
                scriptAppState = new ScriptAppState();
                stateManager.attach(scriptAppState);
                scriptAppState.addTriggerObject(explosionTrigger);
                
                state = 1;
            }

            if (timeElapsed >= 4f) {
                setStopGame(Boolean.FALSE);
                stateManager.detach(scriptAppState);
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
        breakerNode.attachChild(extraBall1);
        extraBall2.addControl(new BreakerControl(app.getRootNode(), stateManager));
        breakerNode.attachChild(extraBall2);
    }
    
    
    //TODO: LLevarlo a un estado en el que una vez finalizado el effecto lo elimine completamente
    public void executeEffect(Vector3f position, Node parent){
         SmokeTrail effect = new SmokeTrail(assetManager, position);
         parent.attachChild(effect.getSmoketrail());
         effect.execute();
    }

}
