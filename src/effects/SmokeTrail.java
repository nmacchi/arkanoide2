/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package effects;

import com.jme3.asset.AssetManager;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 *
 * @author nicolas.macchi
 */
public class SmokeTrail{
    private AssetManager assetManager;
    private ParticleEmitter smoketrail;
    
    private static final int COUNT_FACTOR = 1;
    private static final float COUNT_FACTOR_F = 1f;
    
    private Vector3f position;
    
    public SmokeTrail(AssetManager assetManager, Vector3f position){
        this.assetManager = assetManager;
        this.position = position;
        
        createSmokeTrail();
    }
    
    private void createSmokeTrail(){
        smoketrail = new ParticleEmitter("SmokeTrail", ParticleMesh.Type.Triangle, 22 * COUNT_FACTOR);
        smoketrail.setStartColor(new ColorRGBA(1f, 0.8f, 0.36f, (float) (1.0f / COUNT_FACTOR_F)));
        smoketrail.setEndColor(new ColorRGBA(1f, 0.8f, 0.36f, 0f));
        smoketrail.setStartSize(.2f);
        smoketrail.setEndSize(.3f);

//        smoketrail.setShape(new EmitterSphereShape(Vector3f.ZERO, 1f));
        smoketrail.setFacingVelocity(true);
        smoketrail.setParticlesPerSec(0);
        smoketrail.setGravity(0, 1, 0);
        smoketrail.setLowLife(.4f);
        smoketrail.setHighLife(.5f);
        smoketrail.setInitialVelocity(new Vector3f(0, 12, 0));
        smoketrail.setVelocityVariation(1);
        smoketrail.setImagesX(1);
        smoketrail.setImagesY(3);        
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        mat.setTexture("Texture", assetManager.loadTexture("Effects/Explosion/smoketrail.png"));
        smoketrail.setMaterial(mat);
        smoketrail.setLocalTranslation(position);
    }

    public ParticleEmitter getSmoketrail() {
        return smoketrail;
    }

    public Vector3f getPosition() {
        return position;
    }
    
    public void execute(){
//        node.attachChild(smoketrail);
        smoketrail.emitAllParticles();
    }
}
