/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import java.util.Random;

/**
 *
 * @author nicolas
 */
public class Brick extends Geometry {
    
    protected int points;
    protected int hardness;
    
    //Dimentions
    private static float width = 0.075f;
    private static float height = 0.025f;
    private static float deep = 0.025f;
    
    private static int count = 0;
    
    private RigidBodyControl brick_phy;
    private boolean hasPowerup;
    private int countHits;
    
    Brick(){}
    
    Brick(AssetManager assetManager, Vector3f position) {
        Geometry brick = (Geometry) ((Node) assetManager.loadModel("Models/brick/Cube.mesh.xml")).getChild(0);
        
        //Box box = new Box(width, height, deep);

//        material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        
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
    }

    public void addPhysics(BulletAppState bulletAppState) {
        brick_phy = new RigidBodyControl(0.0f);
        this.addControl(brick_phy);
        brick_phy.setRestitution(1f);
        bulletAppState.getPhysicsSpace().add(brick_phy);
    }

    public static float getWidth() {
        return width;
    }

    public static float getHeight() {
        return height;
    }

    public RigidBodyControl getBrick_phy() {
        return brick_phy;
    }

    public void setBrick_phy(RigidBodyControl brick_phy) {
        this.brick_phy = brick_phy;
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
                Brick brick = (Brick) bricksNode.getChild(rnd.nextInt(count));
                
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

    
    
    
}
