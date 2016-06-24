/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package customcontrols;

import com.jme3.effect.ParticleEmitter;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;

/**
 *
 * @author nicolas.macchi
 */
public class ArkanoidExplosionFXControl extends AbstractControl {
    
    private static float speed = 1f;
    private float time = 0;
    private int state = 0;
    private Node explosionEffect;
    
    @Override
    public void setSpatial(Spatial spatial){
        this.explosionEffect = (Node)spatial;
    }
    
    @Override
    protected void controlUpdate(float tpf) {
        time += tpf / speed;
        if (time > 0.25f && state == 0){
            ((ParticleEmitter)explosionEffect.getChild("Flash")).emitAllParticles();
            ((ParticleEmitter)explosionEffect.getChild("Spark")).emitAllParticles();
            ((ParticleEmitter)explosionEffect.getChild("SmokeTrail")).emitAllParticles();
            ((ParticleEmitter)explosionEffect.getChild("Debris")).emitAllParticles();
            ((ParticleEmitter)explosionEffect.getChild("Shockwave")).emitAllParticles();
            state++;
        }
        if (time > 0.25f + .05f / speed && state == 1){
            ((ParticleEmitter)explosionEffect.getChild("Flame")).emitAllParticles();
            ((ParticleEmitter)explosionEffect.getChild("RoundSpark")).emitAllParticles();
            state++;
        }
        
        // rewind the effect
        if (time > 3 / speed && state == 2){
            state = 0;
            time = 0;
            
            ((ParticleEmitter)explosionEffect.getChild("Flash")).killAllParticles();
            ((ParticleEmitter)explosionEffect.getChild("Spark")).killAllParticles();
            ((ParticleEmitter)explosionEffect.getChild("SmokeTrail")).killAllParticles();
            ((ParticleEmitter)explosionEffect.getChild("Debris")).killAllParticles();
            ((ParticleEmitter)explosionEffect.getChild("Flame")).killAllParticles();
            ((ParticleEmitter)explosionEffect.getChild("RoundSpark")).killAllParticles();
            ((ParticleEmitter)explosionEffect.getChild("Shockwave")).killAllParticles();
            
            explosionEffect.removeControl(this);
            explosionEffect.removeFromParent();
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
