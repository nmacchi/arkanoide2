/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package customcontrols;

import com.jme3.app.state.AppStateManager;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import mygame.entities.Arkanoid;
import mygame.entities.Spaceship;
import states.GamePlayAppState;

/**
 *
 * @author nicolas
 */
public class BreakerBarControl extends AbstractControl {

//    private BreakerBar breakerBar;
    //private CollisionResults results = new CollisionResults();
    private Node rootNode;
    private AppStateManager stateManager;

    public BreakerBarControl(Node rootNode, AppStateManager stateManager) {
        this.rootNode = rootNode;
        this.stateManager = stateManager;
    }

    @Override
    public void setSpatial(Spatial spatial) {
        //super.setSpatial(spatial);
        this.spatial = spatial;
    }

    @Override
    protected void controlUpdate(float tpf) {
        String activePower = (((Arkanoid) spatial).getActivePower());
//        System.out.println(activePower);
        if (activePower != null) {
            if (activePower.equals("FIRE")) {
                
                ((Arkanoid) spatial).setActivePower(null);
                ((Arkanoid) spatial).setCurrentPower("FIRE");
                
                Vector3f position = ((Arkanoid) spatial).getLocalTranslation(); //Get arkanoid current position
                ((Arkanoid) spatial).transformToSpaceship(stateManager, (Node)rootNode.getChild("BreakerBarNode"), position);   
            }
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
