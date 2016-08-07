/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package customcontrols;

import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingBox;
import com.jme3.collision.CollisionResults;
import com.jme3.export.Savable;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.shape.Line;
import mygame.Brick;
import mygame.entities.Breaker;
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
    private AssetManager assetManager;
    
    BreakerControl() {
    }

    public BreakerControl(Node rootNode, AppStateManager stateManager, AssetManager assetManager) {
        this.rootNode = rootNode;
        this.stateManager = stateManager;
        this.assetManager = assetManager;
    }

    @Override
    public void setSpatial(Spatial spatial) {
        //super.setSpatial(spatial);
        this.breaker = (Breaker) spatial;
    }

    @Override
    protected void controlUpdate(float tpf) {
        if (stateManager.getState(GamePlayAppState.class).isGameStarted()) {
//            rootNode.detachChildNamed("line");
            
            breaker.move(breaker.getDirection().mult(tpf * Breaker.getSpeed()));
            
            Ray r = new Ray();
            r.setOrigin(breaker.getLocalTranslation());
            r.setDirection(breaker.getDirection());
            
//            System.out.println("Origen: " +r.getOrigin());
//            System.out.println("Direccion r: " +r.getDirection());
//            System.out.println("Direccion b: " +breaker.getDirection());
            
//            Geometry line = new Geometry("line", new Line(r.getOrigin(), r.getDirection().mult(0.0025f)));
//            Material m = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
//            m.setColor("Color",ColorRGBA.Blue);
//            line.setMaterial(m);
            
//            rootNode.attachChild(line);
            
            
            rootNode.getChild("BricksNode").collideWith(breaker.getWorldBound(), results);
            if(results.size() > 0){
                //Colisiona con uno de los ladrillos
                breaker.setDirection(breaker.reflectVector(results.getClosestCollision()));

                if (results.getClosestCollision().getGeometry() instanceof Brick) {
                    ((Brick)results.getClosestCollision().getGeometry()).removeBrick(rootNode);
                }
                
                results.clear();
            }
            
            rootNode.getChild("Gamefield").collideWith(breaker.getWorldBound(), results);
            if(results.size() > 0){
                //Colisiona contra las paredes o el suelo
                if(results.getClosestCollision().getGeometry().getName().equals("Floor")){
                    stateManager.detach(stateManager.getState(InputAppState.class));
                    stateManager.getState(GamePlayAppState.class).setGameStarted(Boolean.FALSE);
                    stateManager.getState(GamePlayAppState.class).setStopGame(Boolean.TRUE);
                }else{
                    breaker.setDirection(breaker.reflectVector(results.getClosestCollision()));    
                }
                
                results.clear();
            }
            
            
//            ((Geometry)((Node)rootNode.getChild("BreakerBarNode")).getChild("BreakerBar")).collideWith(r, results);
//            if(results.size() > 0){
//                System.out.println(results.getClosestCollision());
//            }
            
//            breaker.collideWith(((Geometry)((Node)rootNode.getChild("BreakerBarNode")).getChild(0)).getWorldBound(), results);
           //((Geometry)((Node)rootNode.getChild("BreakerBarNode")).getChild(0)).getModelBound().collideWith(breaker, results);
            
            
            
//            ((Geometry)rootNode.getChild("BreakerBar")).getModelBound().collideWith(breaker, results);
            ((Geometry)rootNode.getChild("BreakerBar")).getModelBound().collideWith(r, results);
            if(results.size() > 0){
//                System.out.println(results.getClosestCollision().getContactPoint());
                
                System.out.println(results.getClosestCollision().getDistance());
//                String zone = ((BreakerBar)results.getClosestCollision().getGeometry()).evaluateImpactZone(breaker.getLocalTranslation().getX());
//                breaker.changeDirection(results.getClosestCollision(), zone);
                
                results.clear();
            }
            
//            rootNode.collideWith(breaker.getWorldBound(), results);
//            if (results.size() > 0) {
//
//                CollisionResult collisionResult = results.getClosestCollision();
//                String collision = collisionResult.getGeometry().getName();
                //System.out.println("**** " + collision + " ****");
//                if (collision.equals("Floor")) {
////                    stateManager.getState(GamePlayAppState.class).reset();
//                    stateManager.detach(stateManager.getState(InputAppState.class));
//                    stateManager.getState(GamePlayAppState.class).setGameStarted(Boolean.FALSE);
//                    stateManager.getState(GamePlayAppState.class).setStopGame(Boolean.TRUE);
//                    

//                } else if (!collision.equals("Breaker") && !collision.equals("Thing") && !collision.equals("BreakerBar")) {
                    
                    //Colisiona con uno de los ladrillos
//                    breaker.setDirection(breaker.reflectVector(collisionResult));
//
//                    if (collisionResult.getGeometry() instanceof Brick) {
//                        Brick brick = (Brick)collisionResult.getGeometry();
//                        brick.removeBrick(rootNode, (Brick) collisionResult.getGeometry());
//                    }
//                }else if(collision.equals("BreakerBar")){
//                    String zone = ((BreakerBar)collisionResult.getGeometry()).evaluateImpactZone(breaker.getLocalTranslation().getX());
//                    breaker.changeDirection(collisionResult, zone);
//                }
                
                results.clear();
                
            //}
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
    
    
}
