/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package customcontrols;

import com.jme3.app.state.AppStateManager;
import com.jme3.collision.CollisionResults;
import com.jme3.export.Savable;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import mygame.Arkanoid;
import mygame.Powerup;
import states.GamePlayAppState;

/**
 *
 * @author nicolas.macchi
 */
public class PowerupControl extends AbstractControl implements Savable, Cloneable {

    private static float rotationSpeed = 2f;
    private static float translationSpeed = 3f;
    private Node rootNode;
    private AppStateManager stateManager;

    public PowerupControl() {
    }

    public PowerupControl(Node rootNode, AppStateManager stateManager) {

        this.rootNode = rootNode;
        this.stateManager = stateManager;
    }

    @Override
    public void setSpatial(Spatial spatial) {
        super.setSpatial(spatial);
    }

    @Override
    protected void controlUpdate(float tpf) {
        CollisionResults results = new CollisionResults();
        spatial.rotate(0, 0, tpf * rotationSpeed);
        spatial.setLocalTranslation(spatial.getLocalTranslation().x, spatial.getLocalTranslation().y - tpf / translationSpeed, spatial.getLocalTranslation().z);


        spatial.collideWith(((Geometry) rootNode.getChild("Floor")).getWorldBound(), results);
        if (results.size() != 0) {
            spatial.removeFromParent();
            results.clear();
        }

        if ((Geometry) rootNode.getChild("BreakerBar") != null) {
            spatial.collideWith(((Geometry) rootNode.getChild("BreakerBar")).getWorldBound(), results);
            if (results.size() != 0) {
                Geometry g = ((Geometry) rootNode.getChild("BreakerBar"));
                if (g instanceof Arkanoid) {
                    ((Arkanoid) g).setActivePower(((Powerup) spatial).getType().getName());
                }

                stateManager.getState(GamePlayAppState.class).setScore(((Powerup) spatial).getPoints());
                spatial.removeFromParent();
            }

        }


    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
