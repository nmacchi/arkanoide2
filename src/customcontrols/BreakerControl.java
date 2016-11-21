/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package customcontrols;

import com.jme3.app.state.AppStateManager;
import com.jme3.collision.CollisionResults;
import com.jme3.export.Savable;
import com.jme3.math.Ray;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import mygame.Brick;
import mygame.PowerupType;
import mygame.entities.Breaker;
import mygame.entities.BreakerBar;
import states.GamePlayAppState;
import states.InputAppState;

/**
 *
 * @author nicolas
 */
public class BreakerControl extends AbstractControl implements Savable, Cloneable {

    private Node rootNode;
    private CollisionResults results = new CollisionResults();
    private Breaker breaker;
    private AppStateManager stateManager;
    private Ray r = new Ray();
    private float timer;
    boolean flag = false;

    BreakerControl() {
    }

    public BreakerControl(Node rootNode, AppStateManager stateManager) {
        this.rootNode = rootNode;
        this.stateManager = stateManager;

    }

    @Override
    public void setSpatial(Spatial spatial) {
        this.breaker = (Breaker) spatial;
    }

    @Override
    protected void controlUpdate(float tpf) {
        if (stateManager.getState(GamePlayAppState.class).isGameStarted()) {
            
            breaker.move(breaker.getDirection().mult(tpf * breaker.getSpeed()));

            if (BreakerBar.getCurrentPower().equals(PowerupType.PowerTypes.SLOWER.name())) {
                timer += tpf;

                if (timer >= 10f) {
                    BreakerBar.setCurrentPower("");
                    breaker.setSpeed(Breaker.getInitialSpeed());
                    timer = 0f;
                }
            }

            r.setOrigin(breaker.getLocalTranslation());
            r.setDirection(breaker.getDirection());

            rootNode.getChild("BricksNode").collideWith(breaker.getWorldBound(), results);
            if (results.size() > 0) {
                //Colisiona con uno de los ladrillos
                breaker.setDirection(breaker.reflectVector(results.getClosestCollision()));

                if (results.getClosestCollision().getGeometry() instanceof Brick) {
                    ((Brick) results.getClosestCollision().getGeometry()).removeBrick();

                    breaker.countHit();
                }
                
                results.clear();
            }

            rootNode.getChild("Gamefield").collideWith(breaker.getWorldBound(), results);
            if (results.size() > 0) {

                //Colisiona contra las paredes o el suelo
                if (results.getClosestCollision().getGeometry().getName().equals("Floor")) {
                    //System.out.println(((Node)rootNode.getChild("BreakerNode")).getChildren().size());
                    if (((Node) rootNode.getChild("BreakerNode")).getChildren().size() == 1) {
                        removeFromScene();

                        stateManager.detach(stateManager.getState(InputAppState.class));
                        stateManager.getState(GamePlayAppState.class).setGameStarted(Boolean.FALSE);
                        stateManager.getState(GamePlayAppState.class).setGameStop(Boolean.TRUE);           
                    } else {
                        removeFromScene();
                    }

                } else {
                    breaker.setDirection(breaker.reflectVector(results.getClosestCollision()));
                }

                results.clear();
            }

            ((Geometry) rootNode.getChild("BreakerBar")).collideWith(r, results);
            if (results.size() > 0) {
                if (results.getClosestCollision().getDistance() <= 0.05f) {
                    String zone = ((BreakerBar) rootNode.getChild("BreakerBar")).evaluateImpactZone(results.getClosestCollision().getContactPoint().x);
                    breaker.changeDirection(results.getClosestCollision(), zone);
                    
                    breaker.countHit();
                }
                results.clear();
            }

        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void removeFromScene() {
        breaker.removeFromParent();
        breaker.removeControl(this);
    }
    
    
}
