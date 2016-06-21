/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.effect.ParticleEmitter;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import effects.SpaceshipFlame;

/**
 *
 * @author nicolas
 */
public class Spaceship extends BreakerBar{
    
//    private AssetManager assetManager;
    
    private Bullet bullet1, bullet2;
    private float cooldownTime;
    private static float timeToWait = 0.25f;
    
    private ParticleEmitter turbo;
    private ParticleEmitter turbo1;
    private ParticleEmitter turbo2;
    
    public Spaceship(AssetManager assetManager){
        super(assetManager);
        createSpaceship();
        turbo = new SpaceshipFlame(assetManager).getSpaceshipFire();
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
        bullet1 = new Bullet(assetManager, getLocalTranslation(), this.getParent());
        bullet2 = new Bullet(assetManager, getLocalTranslation(), this.getParent());  
        
        this.getParent().attachChild(bullet1);
        this.getParent().attachChild(bullet2);
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

    public ParticleEmitter getTurbo() {
        return turbo;
    }
    
    
}
