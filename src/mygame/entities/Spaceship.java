/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.entities;

import com.jme3.asset.AssetManager;
import com.jme3.effect.ParticleEmitter;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import customcontrols.SpaceshipControl;
import effects.SpaceshipFlame;
import mygame.Bullet;

/**
 *
 * @author nicolas
 */
public class Spaceship extends BreakerBar{
    
//    private AssetManager assetManager;
    
    private Bullet bullet1, bullet2;
    private float cooldownTime;
    private static float timeToWait = 0.25f;
   
    private ParticleEmitter flamming1;
    private ParticleEmitter flamming2;
    private Node localNode = new Node("FlameEffect");
    
    public Spaceship(){};
    
    public Spaceship(AssetManager assetManager){
        super(assetManager);
        createSpaceship();
    }
    
    private void createSpaceship(){
        Geometry geometry = (Geometry)((Node)assetManager.loadModel("Models/spaceship/spaceship_model.j3o")).getChild(0);
        
        setMesh(geometry.getMesh());
        setMaterial(geometry.getMaterial());
        
        scale(0.095f, 0.07f, 0.045f);
        rotate(0f,1.60f,0f);
        
        //setLocalTranslation(new Vector3f(0.25f, 0.03f, 1f));
//        setLocalTranslation(position);
        addControl(new SpaceshipControl());
    }
    
    private void createBullets(){
        bullet1 = new Bullet(assetManager, getWorldTranslation(), this.getParent().getParent());
        bullet2 = new Bullet(assetManager, getWorldTranslation(), this.getParent().getParent());  
        
        this.getParent().getParent().attachChild(bullet1);
        this.getParent().getParent().attachChild(bullet2);
    }
    
    public void fire(){
        createBullets();     
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

    public void addFlammingFX(Node parentNode){
        flamming1 = new SpaceshipFlame(assetManager, getLocalTranslation()).getSpaceshipFire();
        flamming2 = new SpaceshipFlame(assetManager, getLocalTranslation()).getSpaceshipFire();
        
        localNode.attachChild(flamming1);
        localNode.attachChild(flamming2);
        
        parentNode.attachChild(localNode);
    }


}
