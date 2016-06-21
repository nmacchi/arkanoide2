/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package effects;

import com.jme3.asset.AssetManager;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.effect.shapes.EmitterSphereShape;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;

/**
 *
 * @author nicolas.macchi
 */
public class SpaceshipFlame {
    private AssetManager assetManager;
    private ParticleEmitter spaceshipFire;
    private Vector3f position;
    
    public SpaceshipFlame(AssetManager assetManager){
        this.assetManager = assetManager;
        createFlame();
    }
    
    private void createFlame(){
        Material material = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        material.setTexture("Texture", assetManager.loadTexture("Effects/Explosion/flame.png"));
        material.setFloat("Softness", 3f); // 
        
        //Fire
        spaceshipFire = new ParticleEmitter("Fire", ParticleMesh.Type.Triangle, 30);
        spaceshipFire.setMaterial(material);
        spaceshipFire.setShape(new EmitterSphereShape(Vector3f.ZERO, 0.022f));
        spaceshipFire.setImagesX(2);
        spaceshipFire.setImagesY(2); // 2x2 texture animation
        spaceshipFire.setEndColor(new ColorRGBA(1f, 0f, 0f, 1f)); // red
        spaceshipFire.setStartColor(new ColorRGBA(1f, 1f, 0f, 0.5f)); // yellow
        spaceshipFire.setStartSize(0.09f);
        spaceshipFire.setEndSize(0.08f);
        spaceshipFire.setGravity(0, 0.3f, 0);
        spaceshipFire.setLowLife(0.5f);
        spaceshipFire.setHighLife(1.2f);
    }

    public ParticleEmitter getSpaceshipFire() {
        return spaceshipFire;
    }
    
    public void calculateFlamePosition(){
        
    }
}
