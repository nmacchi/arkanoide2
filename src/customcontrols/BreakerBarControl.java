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
import mygame.Arkanoid;
import mygame.Spaceship;
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
        String powerType = (((Arkanoid) spatial).getActivePower());
        if (powerType != null) {
            if (powerType.equals("FIRE") && !((Arkanoid) spatial).isPowerupActivated()) {
                ((Arkanoid)spatial).setPowerupActivated(Boolean.TRUE);
                
                Vector3f position = ((Arkanoid) spatial).getWorldTranslation(); //Get arkanoid current position

                ((Node) rootNode.getChild("BreakerBarNode")).detachAllChildren(); //Remove arakanoid from parent

                //Get the spaceship model for arkanoid replace
                Spaceship spaceship = stateManager.getState(GamePlayAppState.class).getSpaceship();
                spaceship.setLocalTranslation(position);
                spaceship.addControl(new SpaceshipControl());

                ((Node) rootNode.getChild("BreakerBarNode")).attachChild(spaceship);
                spaceship.createTurbo();
            }
        }


//       CollisionResults results = new CollisionResults();


    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
