/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package triggers;

import com.jme3.effect.ParticleEmitter;

/**
 *
 * @author nicolas
 */
public class PlayEffect implements TriggerInterface{
    
    private boolean emitAllParticles;
    private ParticleEmitter effect;
    private boolean enabled = true;
    
    public PlayEffect(ParticleEmitter effect){
        this.effect = effect;
    }
    
    
    public void update(float tpf) {
     
    }

    public void trigger() {
        
        if(enabled){
            emitAllParticles = true;
            onTrigger();
        }
    }

    public void onTrigger() {
        if(emitAllParticles){
            effect.emitAllParticles();
            enabled = false;
        }
    }

    public void stop(){
        System.out.println("FIN EFECTO");
        effect.killAllParticles();
        effect.removeFromParent();
    }
    
    public boolean isEmitAllParticles() {
        return emitAllParticles;
    }

    public void setEmitAllParticles(boolean emitAllParticles) {
        this.emitAllParticles = emitAllParticles;
    }

    public ParticleEmitter getEffect() {
        return effect;
    }

    public void setEffect(ParticleEmitter effect) {
        this.effect = effect;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    
}
