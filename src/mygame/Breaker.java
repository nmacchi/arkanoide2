/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.collision.Collidable;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Sphere;

/**
 *
 * @author nicolas
 */
public class Breaker extends Geometry implements Collidable {
    
    private static float speed = 0.5f;
    private Vector3f initialPosition = new Vector3f(0.0f, 0.08f, 1f);
    private Vector3f direction;
    
    
    private RigidBodyControl breaker_phy = new RigidBodyControl(1f);
    
    Breaker(AssetManager assetManager, BreakerBar breakerBar){
        super("Breaker", new Sphere(16, 16, 0.022f, true, false));
        
        material = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        material.setTexture("DiffuseMap", assetManager.loadTexture("Textures/breaker.jpg"));
        
        material.setBoolean("UseMaterialColors", true);
        material.setColor("Diffuse", ColorRGBA.White);
        material.setColor("Ambient", ColorRGBA.White);
        material.setFloat("Shininess", 32f);
        
        setLocalTranslation(initialPosition.setX(breakerBar.getLocalTranslation().getX()));
        this.direction = new Vector3f(getLocalTranslation().x + 1, getLocalTranslation().y + 1, 0);
    }
    
    /*
    public void applyPhysics(BulletAppState bulletAppState){
        //Vector3f initalDirection = new Vector3f(getLocalTranslation().x + 1, getLocalTranslation().y +1, 0);

        this.addControl(breaker_phy);
        bulletAppState.getPhysicsSpace().add(breaker_phy);
        
        breaker_phy.setFriction(0.0f);
        breaker_phy.setRestitution(1f);
        breaker_phy.setLinearVelocity(this.direction.mult(speed));     
    }*/
    
    /*
    public RigidBodyControl getBreaker_phy() {
        return breaker_phy;
    }

    public void setBreaker_phy(RigidBodyControl breaker_phy) {
        this.breaker_phy = breaker_phy;
    }*/

    public Vector3f getDirection() {
        return direction;
    }

    public void setDirection(Vector3f direction) {
        this.direction = direction;
    }

    public static float getSpeed() {
        return speed;
    }

    public static void setSpeed(float speed) {
        Breaker.speed = speed;
    }
    
    public void resetBall(BreakerBar breakerBar){
        
    }
}
