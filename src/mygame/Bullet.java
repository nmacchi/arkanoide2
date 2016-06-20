/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Sphere;

/**
 *
 * @author nicolas
 */
public class Bullet extends Geometry{
    
    private static float bulletSpeed = 2f;
    Vector3f position;
    
    public Bullet(){}
    
    public Bullet(AssetManager assetManager, Vector3f spaceshipPosition){
         
        super("Bullet", new Sphere(16, 16, 0.015f, true, false));
        
        material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        material.setColor("Color", ColorRGBA.Orange);
        
        setLocalTranslation(spaceshipPosition);
    } 
    
    public void calculatePosition(Boolean leftPos){
        position = getLocalTranslation();
        if(leftPos){
            setLocalTranslation(position.setX(position.getX() - 0.05f));
        }else{
            setLocalTranslation(position.setX(position.getX() + 0.05f));
        }
    }

    public static float getBulletSpeed() {
        return bulletSpeed;
    }
    
    
    
}
