/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package customcontrols;

import com.jme3.collision.CollisionResults;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import mygame.Breaker;
import mygame.BreakerBar;

/**
 *
 * @author nicolas
 */
public class BreakerBarControl extends AbstractControl {
    
    private BreakerBar breakerBar;
    
    @Override
    public void setSpatial(Spatial spatial) {
        //super.setSpatial(spatial);
        this.breakerBar = (BreakerBar) spatial;
    }

    
    @Override
    protected void controlUpdate(float tpf) {
//       CollisionResults results = new CollisionResults();
       
       
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
