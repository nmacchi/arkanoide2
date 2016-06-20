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
public class Spaceship extends BreakerBar{
    
//    private AssetManager assetManager;
    
    private Bullet bullet1, bullet2;
    private float cooldownTime;
    private static float timeToWait = 0.25f;
    
    public Spaceship(AssetManager assetManager){
        super(assetManager);
        createSpaceship();    
    }
    
    private void createSpaceship(){
        Geometry geometry = (Geometry)((Node)assetManager.loadModel("Models/spaceship/spaceship_model.j3o")).getChild(0);
        
        setMesh(geometry.getMesh());
        setMaterial(geometry.getMaterial());
        
        scale(0.06f, 0.07f, 0.045f);
        rotate(0f,1.60f,0f);
        
        setLocalTranslation(new Vector3f(0.25f, 0.03f, 1f));
    }
    
    private void createBullets(){
        bullet1 = new Bullet(assetManager, getLocalTranslation());
        bullet2 = (Bullet)bullet1.clone();
        
        bullet1.calculatePosition(true);
        bullet2.calculatePosition(false);      
    }
    
    public void fire(Node rootNode){
        createBullets();
        bullet1.addControl(new BulletsControl(rootNode));
        bullet2.addControl(new BulletsControl(rootNode));
        rootNode.attachChild(bullet1);
        rootNode.attachChild(bullet2);
        
        cooldownTime=timeToWait;
    }

    public float getCooldownTime() {
        return cooldownTime;
    }

    public void setCooldownTime(float cooldownTime) {
        this.cooldownTime = cooldownTime;
    }
            
    public void restCooldownTime(float tpf){
        this.cooldownTime -= tpf;
    }
    
    
}
