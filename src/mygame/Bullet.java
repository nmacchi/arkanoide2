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
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;
import customcontrols.BulletsControl;

/**
 *
 * @author nicolas
 */
public class Bullet extends Geometry{
    
    private static int instanceCount;
    private static float bulletSpeed = 2f;
//    Vector3f position;
    
    public Bullet(){}
    
    public Bullet(AssetManager assetManager, Vector3f spaceshipPosition, Node rootNode){
         
        super("Bullet", new Sphere(16, 16, 0.015f, true, false));
        
        material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        material.setColor("Color", ColorRGBA.Orange);
        
        setLocalTranslation(spaceshipPosition);
        
        instanceCount++;
        calculatePosition();
        addControl(new BulletsControl(rootNode));
    } 
    
    private void calculatePosition(){
        Vector3f position = getLocalTranslation();
        if((instanceCount % 2) == 0){
            setLocalTranslation(position.setX(position.getX() - 0.05f)); //LEFT
        }else{
            setLocalTranslation(position.setX(position.getX() + 0.05f)); //RIGHT
        }
    }

    public static float getBulletSpeed() {
        return bulletSpeed;
    }
    
    
    
}
