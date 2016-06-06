/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package customcontrols;

import com.jme3.effect.ParticleEmitter;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.control.AbstractControl;
import effects.SmokeTrail;

/**
 *
 * @author nicolas
 */
public class BrickExplosionControl extends AbstractControl {
    
    private static float speed = 1;
    private float time = 0;
    private int state = 0;
    
    private ParticleEmitter smoketrail;
    
    public BrickExplosionControl(SmokeTrail smokeTrail, Node rootNode){
        this.smoketrail = smokeTrail.getSmoketrail(); 
    
        rootNode.attachChild(smoketrail);

    }
    
    @Override
    protected void controlUpdate(float tpf) {
        
        time += tpf / speed;
        if (state == 0){
            smoketrail.emitAllParticles();
            state++;
        }
        
        if (time > 1 / speed && state == 1){
            System.out.println("llega");
//            state = 0;
//            time = 0;
            smoketrail.killAllParticles();
            
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
