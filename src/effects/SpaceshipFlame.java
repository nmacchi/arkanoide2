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
    
    private static int instanceCount;
    
    public SpaceshipFlame(AssetManager assetManager, Vector3f spaceshipPos){
        this.assetManager = assetManager;
        this.position = spaceshipPos;
        
        createFlame();
        
        instanceCount++;
        calculateFlamePosition();
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
        spaceshipFire.setStartSize(0.039f);
        spaceshipFire.setEndSize(0.038f);
        spaceshipFire.setGravity(0, 0.5f, 0);
        spaceshipFire.setLowLife(0.5f);
        spaceshipFire.setHighLife(1.2f);
        spaceshipFire.setParticlesPerSec(100);
        spaceshipFire.setNumParticles(1000);
        spaceshipFire.setLocalTranslation(position);

    }

    public ParticleEmitter getSpaceshipFire() {
        return spaceshipFire;
    }
    
    private void calculateFlamePosition(){
        Vector3f newPosition = spaceshipFire.getLocalTranslation();
        
        newPosition.setY(newPosition.getY() - 0.06f);
        if((instanceCount % 2) == 0){
            spaceshipFire.setLocalTranslation(newPosition.setX(newPosition.getX() - 0.05f)); //LEFT
        }else{
            spaceshipFire.setLocalTranslation(newPosition.setX(newPosition.getX() + 0.05f)); //RIGHT
        }
    }
}
