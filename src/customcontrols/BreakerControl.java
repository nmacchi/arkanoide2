/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package customcontrols;

import com.jme3.app.state.AppStateManager;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.export.Savable;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import mygame.entities.Breaker;
import mygame.Brick;
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
    
    BreakerControl() {
    }

    public BreakerControl(Node rootNode, AppStateManager stateManager) {
        this.rootNode = rootNode;
//        this.breakerBar = breakerBar;
        this.stateManager = stateManager;
    }

    @Override
    public void setSpatial(Spatial spatial) {
        //super.setSpatial(spatial);
        this.breaker = (Breaker) spatial;
    }

    @Override
    protected void controlUpdate(float tpf) {
        if (stateManager.getState(GamePlayAppState.class).isGameStarted()) {
            breaker.move(breaker.getDirection().mult(tpf * Breaker.getSpeed()));
            
            
            results.clear();

            rootNode.collideWith(breaker.getWorldBound(), results);
            if (results.size() > 0) {

                CollisionResult collisionResult = results.getClosestCollision();
                String collision = collisionResult.getGeometry().getName();
                
                if (collision.equals("Floor")) {
//                    stateManager.getState(GamePlayAppState.class).reset();
                    stateManager.detach(stateManager.getState(InputAppState.class));
                    stateManager.getState(GamePlayAppState.class).setGameStarted(Boolean.FALSE);
                    stateManager.getState(GamePlayAppState.class).setStopGame(Boolean.TRUE);
                    

                } else if (!collision.equals("Breaker") && !collision.equals("Thing") && !collision.equals("BreakerBar")) {
                   
                    breaker.changeDirection(collisionResult);
                    //changeDirection(tri.getNormal().normalizeLocal(), breaker.getDirection());

                    if (collisionResult.getGeometry() instanceof Brick) {
                        Brick brick = (Brick)collisionResult.getGeometry();
                        brick.removeBrick(rootNode, (Brick) collisionResult.getGeometry());
                    }
                }else if(collision.equals("BreakerBar")){
                    String zone = ((BreakerBar)collisionResult.getGeometry()).evaluateImpactZone(breaker.getLocalTranslation().getX());
                    
                    if(zone.equals("Left") || zone.equals("Right")){
                        breaker.getDirection().negateLocal();
                    }else if(zone.equals("Center")){
                        breaker.setDirection(Vector3f.UNIT_Y);
                    }else{
                        breaker.changeDirection(collision);
                    }
                }
            }
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

//    private void changeDirection(Vector3f normal, Vector3f direction) {
//        breaker.setDirection(direction.negate().add(normal.mult(normal.dot(direction.negate())).add(direction).mult(2)));
//    }
    

//    private void changeDirection(Vector3f direction) {
//        System.out.println("llega");
//        Vector3f newInvertedDirection = direction.mult(-1);
//
//        Quaternion q = new Quaternion();
//        q.fromAngleAxis(30, Vector3f.UNIT_Z);
//
//        breaker.setDirection(q.mult(newInvertedDirection));
//    }

//    private void removeBrick(Brick brick) {
//        brick.countHits();
//
//        if (brick.getCountHits() >= brick.getHardness()) {
//            
//            stateManager.getState(GamePlayAppState.class).setScore(brick.getPoints());
//            
//            if (brick instanceof CommonBrick) {
//                if (((CommonBrick) brick).isHasPowerup()) {
//                    Powerup powerup = ((CommonBrick) brick).getPowerup();
//                    powerup.addControl(new PowerupControl(rootNode, stateManager));
//                    rootNode.attachChild(powerup);
//                }
//            }
//            
//            brick.removeFromParent();
//            brick.getFX().executeFX(rootNode);
//        }
//    }
    
    private void calculateImpactZone(Geometry arkanoide){
        float x = arkanoide.getWorldTranslation().x;
        
        
    }
}
