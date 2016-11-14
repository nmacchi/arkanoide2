/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package customcontrols;

import com.jme3.app.state.AppStateManager;
import com.jme3.collision.CollisionResults;
import com.jme3.effect.ParticleEmitter;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
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
import mygame.entities.Spaceship;
import states.GamePlayAppState;
import states.PlayerState;

/**
 *
 * @author nicolas
 */
public class BreakerBarControl extends AbstractControl {

    private CollisionResults results = new CollisionResults();
    private Node rootNode;
    private AppStateManager stateManager;

    public BreakerBarControl(Node rootNode, AppStateManager stateManager) {
        this.rootNode = rootNode;
        this.stateManager = stateManager;
//        this.breakerBarFactory = new BreakerBarFactory();
    }

    @Override
    public void setSpatial(Spatial spatial) {
        super.setSpatial(spatial);
    }

    @Override
    protected void controlUpdate(float tpf) {
        if (!stateManager.getState(GamePlayAppState.class).isGameStop()) {
            if (spatial instanceof Spaceship) {
                ((Spaceship) spatial).restCooldownTime(tpf);
            }

            rootNode.getChild("PowerupsNode").collideWith(spatial.getWorldBound(), results);
            if (results.size() > 0) {


                Powerup powerup = (Powerup) results.getClosestCollision().getGeometry();
                stateManager.getState(PlayerState.class).setScore(powerup.getPoints());

                String arkanoidCurrentPower = BreakerBar.getCurrentPower();
                String catchedPowerup = powerup.getType().getName();

                if (PowerupType.PowerTypes.FIRE.name().equals(catchedPowerup) && !catchedPowerup.equals(arkanoidCurrentPower)) {

                    verifyExtraBallActivated();


                    if (spatial instanceof Arkanoid) {
                        executeChangeEffect(spatial.getWorldTranslation());
                           
                        ((Node) rootNode.getChild("BreakerBarNode")).detachAllChildren();
                        Spaceship spaceship = stateManager.getState(GamePlayAppState.class).getSpaceship();
                        spaceship.setLocalTranslation(spatial.getLocalTranslation());
                        spaceship.addControl(new SpaceshipControl());
                        
                        
                        ((Node) rootNode.getChild("BreakerBarNode")).attachChild(spaceship);
                         spaceship.addFlammingFX();
                    }


                    //executeChangeEffect(spatial.getWorldTranslation());

                    //((Arkanoid) spatial).transformToSpaceship(stateManager, (Node) rootNode.getChild("BreakerBarNode"), spatial.getLocalTranslation());

                }

                //No es necesario comprobar si ya lo tiene, puede tomar este modificador varias veces
                if (PowerupType.PowerTypes.LIFE.name().equals(catchedPowerup)) {
                    stateManager.getState(PlayerState.class).addLife();
                }


                if (PowerupType.PowerTypes.SLOWER.name().equals(catchedPowerup)) {

                    verifyExtraBallActivated();

                    if (spatial instanceof Spaceship) {
                        executeChangeEffect(spatial.getWorldTranslation());

                        ((Node) rootNode.getChild("BreakerBarNode")).detachAllChildren();
                        Arkanoid arkanoide = stateManager.getState(GamePlayAppState.class).getArkanoid();
                        arkanoide.setLocalTranslation(spatial.getLocalTranslation());
                        ((Node) rootNode.getChild("BreakerBarNode")).attachChild(arkanoide);
                    }

                    ((Breaker) ((Node)rootNode.getChild("BreakerNode")).getChild(0)).decreaseSpeed();
                }

                if (PowerupType.PowerTypes.EXTRA_BALLS.name().equals(catchedPowerup) && !catchedPowerup.equals(arkanoidCurrentPower)) {
                    stateManager.getState(GamePlayAppState.class).addExtraBalls();


                    if (spatial instanceof Spaceship) {
                        executeChangeEffect(spatial.getWorldTranslation());

                        ((Node) rootNode.getChild("BreakerBarNode")).detachAllChildren();
                        Arkanoid arkanoide = stateManager.getState(GamePlayAppState.class).getArkanoid();
                        arkanoide.setLocalTranslation(spatial.getLocalTranslation());
                        ((Node) rootNode.getChild("BreakerBarNode")).attachChild(arkanoide);
                    }
                }

                BreakerBar.setCurrentPower(catchedPowerup);
                powerup.removeFromParent();

            }
            results.clear();
        }




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
        Node breakerNode = (Node) rootNode.getChild("BreakerNode");

        //Hay mas de una bolita activa
        if (breakerNode.getChildren().size() > 1) {

            List<Spatial> extraBalls = breakerNode.getChildren();

            for (int i = 0; i < 2; i++) {
                //Eliminar las bolas extras
                Breaker extraBall = (Breaker) extraBalls.get(0);
                extraBall.executeExplosionEffect(extraBall.getWorldTranslation());
                extraBall.removeFromParent();
            }
        }
    }
}
