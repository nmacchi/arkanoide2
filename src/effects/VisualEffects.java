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
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 *
 * @author nicolas
 */
public class VisualEffects {
    
    public final static String FLASH = "Flash"; 
    public final static String FLAME = "Flame"; 
    public final static String ROUNDSPARK = "Roundspark"; 
    public final static String SMOKETRAIL = "SmokeTrail"; 
    public final static String SPARK = "Spark";
    public final static String SHOCKWAVE = "Shockwave";
    public final static String DEBRIS = "Debris";
    

    private static Node explosionEffect = new Node("explosionFX");
    private static AssetManager assetManager;
    private static Node rootNode;
    
    private static final int COUNT_FACTOR = 1;
    private static final float COUNT_FACTOR_F = 1f;
    private static final boolean POINT_SPRITE = true;
    private static final ParticleMesh.Type EMITTER_TYPE = POINT_SPRITE ? ParticleMesh.Type.Point : ParticleMesh.Type.Triangle;
    
    public void initVisualEffect(AssetManager assetManager, Node rootNode){
        VisualEffects.assetManager = assetManager;
        VisualEffects.rootNode = rootNode;
        
    }
    
    
    private static void createFlame(Vector3f position) {
        ParticleEmitter flame = new ParticleEmitter("Flame", EMITTER_TYPE, 32 * COUNT_FACTOR);
        flame.setSelectRandomImage(true);
        flame.setStartColor(new ColorRGBA(1f, 0.4f, 0.05f, (float) (1f / COUNT_FACTOR_F)));
        flame.setEndColor(new ColorRGBA(.4f, .22f, .12f, 0f));
        flame.setStartSize(1.3f);
        flame.setEndSize(2f);
        flame.setShape(new EmitterSphereShape(Vector3f.ZERO, 1f));
        flame.setParticlesPerSec(0);
        flame.setGravity(0, -5, 0);
        flame.setLowLife(.4f);
        flame.setHighLife(.5f);
        flame.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 7, 0));
        flame.getParticleInfluencer().setVelocityVariation(1f);
        flame.setImagesX(2);
        flame.setImagesY(2);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        mat.setTexture("Texture", assetManager.loadTexture("Effects/Explosion/flame.png"));
        flame.setMaterial(mat);
        flame.setLocalTranslation(position);

        rootNode.attachChild(flame);
        
        flame.emitAllParticles();
    }

    private static void createFlash(Vector3f position) {
        ParticleEmitter flash = new ParticleEmitter("Flash", EMITTER_TYPE, 24 * COUNT_FACTOR);
        flash.setSelectRandomImage(true);
        flash.setStartColor(new ColorRGBA(1f, 0.8f, 0.36f, (float) (1f / COUNT_FACTOR_F)));
        flash.setEndColor(new ColorRGBA(1f, 0.8f, 0.36f, 0f));
        flash.setStartSize(.1f);
        flash.setEndSize(3.0f);
        flash.setShape(new EmitterSphereShape(Vector3f.ZERO, .05f));
        flash.setParticlesPerSec(0);
        flash.setGravity(0, 0, 0);
        flash.setLowLife(.2f);
        flash.setHighLife(.2f);
        flash.setInitialVelocity(new Vector3f(0, 5f, 0));
        flash.setVelocityVariation(1);
        flash.setImagesX(2);
        flash.setImagesY(2);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        mat.setTexture("Texture", assetManager.loadTexture("Effects/Explosion/flash.png"));
        mat.setBoolean("PointSprite", POINT_SPRITE);
        flash.setMaterial(mat);
        flash.setLocalTranslation(position);
        
        rootNode.attachChild(flash);
        flash.emitAllParticles();
    }

    private static void createRoundSpark(Vector3f position) {
        ParticleEmitter roundspark = new ParticleEmitter("RoundSpark", EMITTER_TYPE, 20 * COUNT_FACTOR);
        roundspark.setStartColor(new ColorRGBA(1f, 0.29f, 0.34f, (float) (1.0 / COUNT_FACTOR_F)));
        roundspark.setEndColor(new ColorRGBA(0, 0, 0, (float) (0.5f / COUNT_FACTOR_F)));
        roundspark.setStartSize(1.2f);
        roundspark.setEndSize(1.8f);
        roundspark.setShape(new EmitterSphereShape(Vector3f.ZERO, 2f));
        roundspark.setParticlesPerSec(0);
        roundspark.setGravity(0, -.5f, 0);
        roundspark.setLowLife(1.8f);
        roundspark.setHighLife(2f);
        roundspark.setInitialVelocity(new Vector3f(0, 3, 0));
        roundspark.setVelocityVariation(.5f);
        roundspark.setImagesX(1);
        roundspark.setImagesY(1);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        mat.setTexture("Texture", assetManager.loadTexture("Effects/Explosion/roundspark.png"));
        mat.setBoolean("PointSprite", POINT_SPRITE);
        roundspark.setMaterial(mat);
        roundspark.setLocalTranslation(position);
        
        rootNode.attachChild(roundspark);
        roundspark.emitAllParticles();
    }

    private static void createSpark(Vector3f position) {
        ParticleEmitter spark = new ParticleEmitter("Spark", ParticleMesh.Type.Triangle, 30 * COUNT_FACTOR);
        spark.setStartColor(new ColorRGBA(1f, 0.8f, 0.36f, (float) (1.0f / COUNT_FACTOR_F)));
        spark.setEndColor(new ColorRGBA(1f, 0.8f, 0.36f, 0f));
        spark.setStartSize(.5f);
        spark.setEndSize(.5f);
        spark.setFacingVelocity(true);
        spark.setParticlesPerSec(0);
        spark.setGravity(0, 5, 0);
        spark.setLowLife(1.1f);
        spark.setHighLife(1.5f);
        spark.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 20, 0));
        spark.getParticleInfluencer().setVelocityVariation(1);
        spark.setImagesX(1);
        spark.setImagesY(1);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        mat.setTexture("Texture", assetManager.loadTexture("Effects/Explosion/spark.png"));
        spark.setMaterial(mat);
        spark.setLocalTranslation(position);
        
        rootNode.attachChild(spark);
        spark.emitAllParticles();
    }

    private static void createSmokeTrail(Vector3f position) {
        ParticleEmitter smoketrail = new ParticleEmitter("SmokeTrail", ParticleMesh.Type.Triangle, 22 * COUNT_FACTOR);
        smoketrail.setStartColor(new ColorRGBA(1f, 0.8f, 0.36f, (float) (1.0f / COUNT_FACTOR_F)));
        smoketrail.setEndColor(new ColorRGBA(1f, 0.8f, 0.36f, 0f));
        smoketrail.setStartSize(.2f);
        smoketrail.setEndSize(1f);

        smoketrail.setShape(new EmitterSphereShape(Vector3f.ZERO, 1f));
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
        
        rootNode.attachChild(smoketrail);
        smoketrail.emitAllParticles();
    }
    
    private static void createBallExplosionEffect(Vector3f position) {
        ParticleEmitter smoketrail = new ParticleEmitter("SmokeTrail", ParticleMesh.Type.Triangle, 55 * COUNT_FACTOR);
        smoketrail.setStartColor(new ColorRGBA(1f, 1f, 1f, (float) (1.0f / COUNT_FACTOR_F)));
        smoketrail.setEndColor(new ColorRGBA(1f, 1f, 1f, 0f));
        smoketrail.setStartSize(.1f);
        smoketrail.setEndSize(.03f);

        smoketrail.setShape(new EmitterSphereShape(Vector3f.ZERO, 1f));
        smoketrail.setFacingVelocity(true);
        smoketrail.setParticlesPerSec(0);
        smoketrail.setGravity(0, 0, 0);
        smoketrail.setLowLife(.4f);
        smoketrail.setHighLife(.6f);
        smoketrail.setInitialVelocity(new Vector3f(0, 12, 0));
        smoketrail.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 2f, 0));
        smoketrail.setVelocityVariation(1);
        smoketrail.setImagesX(1);
        smoketrail.setImagesY(3);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        mat.setTexture("Texture", assetManager.loadTexture("Effects/Explosion/smoketrail.png"));
        smoketrail.setMaterial(mat);
        smoketrail.setLocalTranslation(position);
        
        rootNode.attachChild(smoketrail);
        smoketrail.emitAllParticles();
    }
    
    

    private static void createChangeEffect(Vector3f position) {
        ParticleEmitter smoketrail = new ParticleEmitter("SmokeTrail", ParticleMesh.Type.Triangle, 75 * COUNT_FACTOR);
        smoketrail.setStartColor(new ColorRGBA(1f, 0.4f, 0.05f, (float) (1.0f / COUNT_FACTOR_F)));
        smoketrail.setEndColor(new ColorRGBA(.4f, .22f, .12f, 0f));
        smoketrail.setStartSize(.1f);
        smoketrail.setEndSize(.03f);

        smoketrail.setShape(new EmitterSphereShape(Vector3f.ZERO, 0.020f));
        smoketrail.setFacingVelocity(true);
        smoketrail.setParticlesPerSec(0);
        smoketrail.setGravity(0, 0, 0);
        smoketrail.setLowLife(.4f);
        smoketrail.setHighLife(.6f);
        smoketrail.setInitialVelocity(new Vector3f(0, 12, 0));
        smoketrail.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 2f, 0));
        smoketrail.setVelocityVariation(1);
        smoketrail.setImagesX(1);
        smoketrail.setImagesY(3);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        mat.setTexture("Texture", assetManager.loadTexture("Effects/Explosion/smoketrail.png"));
        smoketrail.setMaterial(mat);
        smoketrail.setLocalTranslation(position);
        
        rootNode.attachChild(smoketrail);
        smoketrail.emitAllParticles();
    }
    
    
   
    private static void createDebris(Vector3f position) {
        ParticleEmitter debris = new ParticleEmitter("Debris", ParticleMesh.Type.Triangle, 15 * COUNT_FACTOR);
        debris.setSelectRandomImage(true);
        debris.setRandomAngle(true);
        debris.setRotateSpeed(FastMath.TWO_PI * 4);
        debris.setStartColor(new ColorRGBA(1f, 0.59f, 0.28f, (float) (1.0f / COUNT_FACTOR_F)));
        debris.setEndColor(new ColorRGBA(.5f, 0.5f, 0.5f, 0f));
        debris.setStartSize(.2f);
        debris.setEndSize(.2f);

        debris.setShape(new EmitterSphereShape(Vector3f.ZERO, .05f));
        debris.setParticlesPerSec(0);
        debris.setGravity(0, 12f, 0);
        debris.setLowLife(1.4f);
        debris.setHighLife(1.5f);
        debris.setInitialVelocity(new Vector3f(0, 15, 0));
        debris.setVelocityVariation(.60f);
        debris.setImagesX(3);
        debris.setImagesY(3);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        mat.setTexture("Texture", assetManager.loadTexture("Effects/Explosion/Debris.png"));
        debris.setMaterial(mat);
        debris.setLocalTranslation(position);

        rootNode.attachChild(debris);
        debris.emitAllParticles();
    }

    private static void createShockwave(Vector3f position) {
        ParticleEmitter shockwave = new ParticleEmitter("Shockwave", ParticleMesh.Type.Triangle, 22 * COUNT_FACTOR);
        shockwave.setRandomAngle(true);
        shockwave.setFaceNormal(Vector3f.UNIT_Y);
        shockwave.setStartColor(new ColorRGBA(.48f, 0.17f, 0.01f, (float) (.8f / COUNT_FACTOR_F)));
        shockwave.setEndColor(new ColorRGBA(.48f, 0.17f, 0.01f, 0f));

        shockwave.setStartSize(0f);
        shockwave.setEndSize(7f);

        shockwave.setParticlesPerSec(0);
        shockwave.setGravity(0, 0, 0);
        shockwave.setLowLife(0.5f);
        shockwave.setHighLife(3.5f);
        shockwave.setInitialVelocity(new Vector3f(0, 0, 0));
        shockwave.setVelocityVariation(0f);
        shockwave.setImagesX(1);
        shockwave.setImagesY(1);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        mat.setTexture("Texture", assetManager.loadTexture("Effects/Explosion/shockwave.png"));
        shockwave.setMaterial(mat);
        shockwave.setLocalTranslation(position);
        
        rootNode.attachChild(shockwave);
        shockwave.emitAllParticles();
    }
    
    private static void createFlammingBall(Vector3f position, Node parent){
        Material material = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        material.setTexture("Texture", assetManager.loadTexture("Effects/Explosion/flame.png"));
        material.setFloat("Softness", 3f); 
        
        //Fire
        ParticleEmitter flame = new ParticleEmitter("Fire", ParticleMesh.Type.Triangle, 50);
        flame.setMaterial(material);
        flame.setShape(new EmitterSphereShape(Vector3f.ZERO, 0.022f));
        flame.setImagesX(2);
        flame.setImagesY(2); // 2x2 texture animation
        flame.setEndColor(new ColorRGBA(1f, 0f, 0f, 1f)); // red
        flame.setStartColor(new ColorRGBA(1f, 1f, 0f, 0.5f)); // yellow
        flame.setStartSize(0.049f);
        flame.setEndSize(0.038f);
        flame.setGravity(0, 0.0f, 0);
        flame.setLowLife(0.3f);
        flame.setHighLife(0.9f);
        flame.setParticlesPerSec(80);
        flame.setLocalTranslation(position);
        
        parent.attachChild(flame);
        flame.emitAllParticles();
    }

    public static void getFlame(Vector3f position) {
        createFlame(position);
    }

    public static void getFlash(Vector3f position) {
        createFlash(position);
    }

    public static void getSpark(Vector3f position) {
        createSpark(position);
    }

    public static void getRoundspark(Vector3f position) {
        createRoundSpark(position);
    }

    public static void getSmoketrail(Vector3f position) {
        createSmokeTrail(position);
    }

    public static void getDebris(Vector3f position) {
        createDebris(position);
    }

    public static void getShockwave(Vector3f position) {
        createShockwave(position);
    }
    
    public static void getChangeEffect(Vector3f position) {
        createChangeEffect(position);
    }
    
     public static void getBallExplosionEffect(Vector3f position) {
        createBallExplosionEffect(position);
    }
    
     public static void getFlammingBall(Vector3f position, Node parent){
         createFlammingBall(position, parent);
     }
     
    public static Node getExplosionEffect() {
        return explosionEffect;
    }
    
}
