/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.collision.CollisionResult;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;
import customcontrols.BreakerControl;

/**
 *
 * @author nicolas
 */
public class Breaker extends Geometry  {
    
    private static float speed = 0.5f;
    private static Vector3f initialPosition = new Vector3f(0.25f, 0.08f, 1f);
    private Vector3f direction;
    
    
    
    public Breaker(AssetManager assetManager){
        super("Breaker", new Sphere(16, 16, 0.022f, true, false));
        
        material = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        material.setTexture("DiffuseMap", assetManager.loadTexture("Textures/breaker.jpg"));
        
        material.setBoolean("UseMaterialColors", true);
        material.setColor("Diffuse", ColorRGBA.White);
        material.setColor("Ambient", ColorRGBA.White);
        material.setFloat("Shininess", 32f);
        
        setLocalTranslation(initialPosition);
    }
    
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

    public static Vector3f getInitialPosition() {
        return initialPosition;
    }
    
    public void setInitialDirection(){
        this.direction = new Vector3f(getLocalTranslation().x + 1, getLocalTranslation().y + 1, 0);
    }
  
}
