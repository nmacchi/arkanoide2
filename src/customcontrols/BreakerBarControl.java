/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package customcontrols;

import com.jme3.app.state.AppStateManager;
import com.jme3.collision.CollisionResults;
import com.jme3.effect.ParticleEmitter;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import effects.VisualEffects;
import java.util.List;
import mygame.Powerup;
import mygame.PowerupType;
import mygame.entities.Arkanoid;
import mygame.entities.Breaker;
import mygame.entities.BreakerBar;
import mygame.entities.BreakerBarFactory;
import mygame.entities.Spaceship;
import states.GameGuiAppState;
import states.GamePlayAppState;

/**
 *
 * @author nicolas
 */
public class BreakerBarControl extends AbstractControl {

//    private BreakerBar breakerBar;
    //private CollisionResults results = new CollisionResults();
    private CollisionResults results = new CollisionResults();
    private Node rootNode;
    private AppStateManager stateManager;
//    private BreakerBarFactory breakerBarFactory;
    
//    private float width;
    public BreakerBarControl(Node rootNode, AppStateManager stateManager) {
        this.rootNode = rootNode;
        this.stateManager = stateManager;
//        this.breakerBarFactory = new BreakerBarFactory();
    }

    @Override
    public void setSpatial(Spatial spatial) {
        super.setSpatial(spatial);

//        this.width = ((BoundingBox)spatial.getWorldBound()).getXExtent();
    }

    @Override
    protected void controlUpdate(float tpf) {
        //TODO: Verificar si ya hay un poder aplicado 

        rootNode.getChild("PowerupsNode").collideWith(spatial.getWorldBound(), results);
        if (results.size() > 0) {


            Powerup powerup = (Powerup) results.getClosestCollision().getGeometry();
            stateManager.getState(GamePlayAppState.class).setScore(powerup.getPoints());

            String arkanoidCurrentPower = BreakerBar.getCurrentPower();
            String catchedPowerup = powerup.getType().getName();

            if (PowerupType.PowerTypes.FIRE.name().equals(catchedPowerup) && !catchedPowerup.equals(arkanoidCurrentPower)) {
                
                verifyExtraBallActivated();

                executeChangeEffect(spatial.getWorldTranslation());

                ((Arkanoid) spatial).transformToSpaceship(stateManager, (Node) rootNode.getChild("BreakerBarNode"), spatial.getLocalTranslation());

            }

            //No es necesario comprobar si ya lo tiene, puede tomar este modificador varias veces
            if (PowerupType.PowerTypes.LIFE.name().equals(catchedPowerup)) {
                GamePlayAppState gpap = stateManager.getState(GamePlayAppState.class);
                gpap.addLife();
                stateManager.getState(GameGuiAppState.class).updateLivesIndicator(stateManager.getApplication(), gpap.getCurrentLives());
            }


            if (PowerupType.PowerTypes.SLOWER.name().equals(catchedPowerup)) {

                verifyExtraBallActivated();
               
                if((Geometry)((Node)rootNode.getChild("BreakerBarNode")).getChild(0) instanceof Spaceship){
                    executeChangeEffect(spatial.getWorldTranslation());
                    
                    ((Node)rootNode.getChild("BreakerBarNode")).detachAllChildren();               
                    Arkanoid arkanoide = stateManager.getState(GamePlayAppState.class).getArkanoid();
                    arkanoide.setLocalRotation(spatial.getLocalRotation());
                    ((Node)rootNode.getChild("BreakerBarNode")).attachChild(arkanoide);
                }
                
                ((Breaker) rootNode.getChild("Breaker")).decreaseSpeed();
            }

            if (PowerupType.PowerTypes.EXTRA_BALLS.name().equals(catchedPowerup) && !catchedPowerup.equals(arkanoidCurrentPower)) {
                stateManager.getState(GamePlayAppState.class).addExtraBalls();
            }

            BreakerBar.setCurrentPower(catchedPowerup);
            powerup.removeFromParent();

        }

        results.clear();

    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void executeChangeEffect(Vector3f position) {
        ParticleEmitter fx = VisualEffects.getChangeEffect(position);

        rootNode.attachChild(fx);
        fx.emitAllParticles();
    }

    private void verifyExtraBallActivated() {
        if ((Node) rootNode.getChild("ExtraBalls") != null) {
            //Eliminar las bolas extras 
            List<Spatial> extraBalls = ((Node) rootNode.getChild("ExtraBalls")).getChildren();

            for (Spatial extraBall : extraBalls) {
                ((Breaker) extraBall).executeExplosionEffect(extraBall.getWorldTranslation());
                extraBall.removeFromParent();
            }

//            rootNode.detachChild((Node) rootNode.getChild("ExtraBalls"));
        }
    }
}
