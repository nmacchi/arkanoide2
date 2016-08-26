/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import customcontrols.PowerupControl;
import effects.SmokeTrail;
import java.util.Random;
import states.GamePlayAppState;
import states.ScriptAppState;
import triggers.PlayEffect;
import triggers.Trigger;

/**
 *
 * @author nicolas
 */
public class Brick extends Geometry {
    
    protected AppStateManager stateManager;
    protected int points;
    protected int hardness;
    
    //Dimentions
    private static float width = 0.075f;
    private static float height = 0.025f;
    
    private static int count = 0;
    
    private boolean hasPowerup;
    private int countHits;
    
    //FX
    private SmokeTrail FX;
    
    Brick(){}
    
    Brick(AssetManager assetManager, Vector3f position, AppStateManager stateManager) {
        this.stateManager = stateManager;
        
        Geometry brick = (Geometry) ((Node) assetManager.loadModel("Models/brick/Cube.mesh.j3o")).getChild(0);
        
        material = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        material.setBoolean("UseMaterialColors", true);
        material.setColor("Specular", ColorRGBA.White);
        material.setColor("Ambient", ColorRGBA.White);
        material.setFloat("Shininess", 32f);
        
        setMesh(brick.getMesh());
        setLocalTranslation(position);
        
        scale(0.02f, 0.03f, 0.08f);
        rotate(0f,1.60f,0f);
        
        setName("Brick" + count);
        count++;
        
        //create FX
        FX = new SmokeTrail(assetManager, position);
    }

    public static float getWidth() {
        return width;
    }

    public static float getHeight() {
        return height;
    }

    public boolean isHasPowerup() {
        return hasPowerup;
    }

    public void setHasPowerup(boolean hasPowerup) {
        this.hasPowerup = hasPowerup;
    }

    public static void selectSpecialBrick(AssetManager assetManager, Node bricksNode) {
        int numSurprises = 6;
        Random rnd = new Random();

        boolean hasPowerup = false;

        for (int i = 0; i <= numSurprises; i++) {

            while (!hasPowerup) {
                //Brick brick = (Brick) bricksNode.getChild(rnd.nextInt(count));
                Brick brick = (Brick) bricksNode.getChild(i);
                
                //Only commonBrick could have rewards
                if(brick instanceof CommonBrick){
                    hasPowerup = brick.isHasPowerup();
                    if (!hasPowerup) {
                        brick.setHasPowerup(Boolean.TRUE);
                        hasPowerup = true;
                        ((CommonBrick)brick).setPowerup(new Powerup(assetManager, brick));
                    }
                }
                
            }

            hasPowerup = false;
        }
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getHardness() {
        return hardness;
    }

    public void setHardness(int hardness) {
        this.hardness = hardness;
    }
    
    public void countHits(){
        this.countHits++;
    }

    public int getCountHits() {
        return countHits;
    }

    public SmokeTrail getFX() {
        return FX;
    }
    
    public void removeBrick(Node rootNode/*, Brick brick*/){
        countHits();

        if (getCountHits() >= getHardness()) {
            
            stateManager.getState(GamePlayAppState.class).setScore(getPoints());
            
            if (this instanceof CommonBrick) {
                if (((CommonBrick) this).isHasPowerup()) {
                    Powerup powerup = ((CommonBrick) this).getPowerup();
                    powerup.addControl(new PowerupControl(rootNode, stateManager));
                    ((Node)rootNode.getChild("PowerupsNode")).attachChild(powerup);
                }
            }
            
            Node parent = this.getParent();
            removeFromParent();
            
            executeEffect(parent, this.getLocalTranslation());
//            brick.getFX().executeFX(rootNode);
        }
    }
    
    private void executeEffect(Node parent, Vector3f position){
        final PlayEffect smoketrailEffect = new PlayEffect();
        smoketrailEffect.setEffect(FX.getSmoketrail());
        smoketrailEffect.setEmitAllParticles(true);
        
        parent.attachChild(smoketrailEffect.getEffect());
        
        Trigger explosionTimer = new Trigger();
        explosionTimer.addTimerEvent(0f, new Trigger.TimerEvent() {

            public Object[] call() {
                smoketrailEffect.trigger();
                return null;
            }
        });
        
        explosionTimer.addTimerEvent(2.5f, new Trigger.TimerEvent() {

            public Object[] call() {
                smoketrailEffect.stop();
                return null;
            }
        });
        
        ScriptAppState scriptAppState = new ScriptAppState();
        stateManager.attach(scriptAppState);
        
        scriptAppState.addTriggerObject(explosionTimer);
    }
    
    
}
