/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package customcontrols;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.audio.AudioNode;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import effects.VisualEffects;
import factories.BreakerBarFactory;
import mygame.Powerup;
import mygame.commons.BreakerBarTypes;
import mygame.commons.PowerupType;
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
    private SimpleApplication app;
    private BreakerBarFactory breakerBarCreator;

    public BreakerBarControl(Node rootNode, SimpleApplication app) {
        this.rootNode = rootNode;
        this.stateManager = app.getStateManager();
        this.app = app;
        this.breakerBarCreator = new BreakerBarFactory();
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

                PowerupType arkanoidCurrentPower = BreakerBar.getCurrentPower();
                PowerupType catchedPowerup = powerup.getType();

                if (PowerupType.FIRE.equals(catchedPowerup) && !catchedPowerup.equals(arkanoidCurrentPower)) {

                    verifyExtraBallActivated();

                    if (spatial instanceof Arkanoid) {
                        executeChangeEffect(spatial.getWorldTranslation());
                        breakerBarCreator.createrBar(BreakerBarTypes.SPACESHIP, (Node) rootNode.getChild("BreakerBarNode"), app, spatial.getLocalTranslation());
                    }

                }

                //No es necesario comprobar si ya lo tiene, puede tomar este modificador varias veces
                if (PowerupType.LIFE.equals(catchedPowerup)) {
                    stateManager.getState(PlayerState.class).addLife();
                    ((AudioNode)rootNode.getChild("lifeWonAudio")).playInstance();
                }


                if (PowerupType.SLOWER.equals(catchedPowerup)) {

                    verifyExtraBallActivated();

                    if (spatial instanceof Spaceship) {
                        executeChangeEffect(spatial.getWorldTranslation());
                        breakerBarCreator.createrBar(BreakerBarTypes.ARKANOID, (Node) rootNode.getChild("BreakerBarNode"), app, spatial.getLocalTranslation());
                    }

                    ((Breaker) ((Node) rootNode.getChild("BreakerNode")).getChild(0)).decreaseSpeed();
                }

                if (PowerupType.EXTRA_BALLS.equals(catchedPowerup) && !catchedPowerup.equals(arkanoidCurrentPower)) {
                    stateManager.getState(GamePlayAppState.class).addExtraBalls();

                    if (spatial instanceof Spaceship) {
                        executeChangeEffect(spatial.getWorldTranslation());
                        breakerBarCreator.createrBar(BreakerBarTypes.ARKANOID, (Node) rootNode.getChild("BreakerBarNode"), app, spatial.getLocalTranslation());
                    }
                }

                //Por ahora es la FIRE_BALL
                if (PowerupType.FIREBALL.equals(catchedPowerup) && !catchedPowerup.equals(arkanoidCurrentPower)) {
                    verifyExtraBallActivated();

                    if (spatial instanceof Spaceship) {
                        executeChangeEffect(spatial.getWorldTranslation());
                        breakerBarCreator.createrBar(BreakerBarTypes.ARKANOID, (Node) rootNode.getChild("BreakerBarNode"), app, spatial.getLocalTranslation());
                    }

                    ((Breaker) ((Node) rootNode.getChild("BreakerNode")).getChild(0)).changeToFireBall();
                    ((Breaker) ((Node) rootNode.getChild("BreakerNode")).getChild(0)).playFireballAudio();
                    
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
        VisualEffects.getChangeEffect(position);
//
//        rootNode.attachChild(fx);
//        fx.emitAllParticles();
    }

    /*private void addExtraBalls() {
     Node node = (Node) rootNode.getChild("BreakerNode");
     Breaker ball = (Breaker) rootNode.getChild("Breaker");

     for (int i = 0; i < 2; i++) {
     Quaternion quat = new Quaternion();
     Vector3f direction = new Vector3f();

     int rotationDegree;
     if (node.getChildren().size() < 1) {
     rotationDegree = 10;
     } else {
     rotationDegree = -10;
     }

     quat.fromAngleAxis(FastMath.PI * rotationDegree / 180, Vector3f.UNIT_Z);
     quat.mult(ball.getDirection(), direction);

     Breaker extraBall = new Breaker(stateManager.getApplication().getAssetManager(), ball.getLocalTranslation(), ball.getSpeed(), direction);
     extraBall.addControl(new BreakerControl(rootNode, stateManager));
     rootNode.attachChild(extraBall);
     }
     }*/
    private void verifyExtraBallActivated() {
        if (BreakerBar.getCurrentPower().equals(PowerupType.EXTRA_BALLS)) {
            stateManager.getState(GamePlayAppState.class).removeExtraballsFromScene();
        }
    }
}
