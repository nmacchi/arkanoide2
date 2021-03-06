/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.bounding.BoundingBox;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import customcontrols.PowerupControl;
import effects.SmokeTrail;
import effects.VisualEffects;
import java.util.Random;
import states.PlayerState;

/**
 *
 * @author nicolas
 */
public class Brick extends Geometry {

    protected AppStateManager stateManager;
    protected int points;
    protected int hardness;
    //Dimentions
//    private static float width = 0.075f;
//    private static float height = 0.025f;
    //private static int count = 0;
    private boolean hasPowerup;
    private int countHits;
    //FX
    private SmokeTrail FX;
    
    
    //Test 
    protected AssetManager assetManager;
    
    public Brick() {
    }

    Brick(AssetManager assetManager, Vector3f position, AppStateManager stateManager) {
        //super("Brick" + count);
        super("Brick");
        this.stateManager = stateManager;
        this.assetManager = assetManager;
        
        Geometry brick = (Geometry) ((Node) assetManager.loadModel("Models/brick/Cube.mesh.j3o")).getChild(0);

        material = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        material.setBoolean("UseMaterialColors", true);
        material.setColor("Specular", ColorRGBA.White);
        material.setColor("Ambient", ColorRGBA.White);
        material.setFloat("Shininess", 32f);
        
        mesh = brick.getMesh();
        
        setLocalTranslation(position);
        scale(0.02f, 0.03f, 0.07f);
        rotate(0f, 1.60f, 0f);

        //count++;

        //create FX
        //FX = new SmokeTrail(assetManager, position);
    }

    public float getWidth() {
        return ((BoundingBox) this.getWorldBound()).getXExtent();
//        return width;
    }

    public float getHeight() {
        return ((BoundingBox) this.getWorldBound()).getYExtent();
//        return height;
    }

    public boolean isHasPowerup() {
        return hasPowerup;
    }

    public void setHasPowerup(boolean hasPowerup) {
        this.hasPowerup = hasPowerup;
    }

    public static void selectSpecialBrick(AssetManager assetManager, Node bricksNode, int powerupsAmount) {
        //int numSurprises = 15;
        Random rnd = new Random();

        boolean hasPowerup = false;

        for (int i = 0; i <= powerupsAmount; i++) {

            while (!hasPowerup) {
                Brick brick = (Brick) bricksNode.getChild(rnd.nextInt(bricksNode.getChildren().size()));
//                Brick brick = (Brick) bricksNode.getChild(i);

                //Only commonBrick could have rewards
                if (brick instanceof CommonBrick) {
                    hasPowerup = brick.isHasPowerup();
                    if (!hasPowerup) {
                        brick.setHasPowerup(Boolean.TRUE);
                        hasPowerup = true;
                        ((CommonBrick) brick).setPowerup(new Powerup(assetManager, brick));
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

    public void countHits() {
        this.countHits++;
    }

    public int getCountHits() {
        return countHits;
    }

    public SmokeTrail getFX() {
        return FX;
    }

    public void removeBrick() {

        countHits();

        if (getCountHits() >= getHardness()) {

            //stateManager.getState(PlayerState.class).setScore(getPoints());

            playExplosionAudio();
            dropPowerupIfExist();
            /*if (this instanceof CommonBrick) {
                CommonBrick commonBrick = (CommonBrick) this;

                if (commonBrick.isHasPowerup()) {
                    Powerup powerup = commonBrick.getPowerup();
                    powerup.addControl(new PowerupControl(this.getParent().getParent(), stateManager));
                    ((Node) this.getParent().getParent().getChild("PowerupsNode")).attachChild(powerup);
                }
            }*/

//                executeExplosionEffect(this.getLocalTranslation());
//                removeFromParent();
            doRemove();
//            return true;
        } else {
            ((MetallicBrick) this).playMetallicReboundSound();
        }

//        return false;
    }

    private void executeExplosionEffect(Vector3f position) {
        VisualEffects.getDebris(position);

//        this.getParent().attachChild(fx);
//        fx.emitAllParticles();
    }

    public void doRemove() {
        stateManager.getState(PlayerState.class).setScore(getPoints());
        playExplosionAudio();
        dropPowerupIfExist();
        executeExplosionEffect(this.getLocalTranslation());
        removeFromParent();
    }

    public void playExplosionAudio() {
        ((AudioNode) this.getParent().getParent().getChild("brickExplosionAudio")).playInstance();
    }

    private void dropPowerupIfExist() {
        if (this instanceof CommonBrick) {
            CommonBrick commonBrick = (CommonBrick) this;

            if (commonBrick.isHasPowerup()) {
                Powerup powerup = commonBrick.getPowerup();
                powerup.addControl(new PowerupControl(this.getParent().getParent(), stateManager));
                ((Node) this.getParent().getParent().getChild("PowerupsNode")).attachChild(powerup);
            }
        }
    }
}
