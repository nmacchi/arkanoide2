/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package customcontrols;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
import mygame.Spaceship;

/**
 *
 * @author nicolas
 */
public class SpaceshipControl extends AbstractControl{
    
    
    
    @Override
    protected void controlUpdate(float tpf) {
        ((Spaceship)spatial).restCooldownTime(tpf);
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
