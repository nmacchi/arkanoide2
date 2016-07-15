/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package customcontrols;

import com.jme3.app.state.AppStateManager;
import com.jme3.bounding.BoundingBox;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import mygame.entities.Breaker;
import mygame.entities.Arkanoid;

/**
 *
 * @author nicolas
 */
public class BreakerBarControl extends AbstractControl {

//    private BreakerBar breakerBar;
    //private CollisionResults results = new CollisionResults();
    CollisionResults results = new CollisionResults();
    private Node rootNode;
    private AppStateManager stateManager;

    private float width;
    
    public BreakerBarControl(Node rootNode, AppStateManager stateManager) {
        this.rootNode = rootNode;
        this.stateManager = stateManager;
    }

    @Override
    public void setSpatial(Spatial spatial) {
        super.setSpatial(spatial);
        
        this.width = ((BoundingBox)spatial.getWorldBound()).getXExtent();
    }

    @Override
    protected void controlUpdate(float tpf) {
        results.clear();
        
        rootNode.getChild("Breaker").collideWith(spatial.getWorldBound(), results);
        if(results.size() > 0){
            CollisionResult collision = results.getClosestCollision();
            
            System.out.println(collision.getGeometry().getName());
            if(collision.getGeometry().getName().equals("Breaker")){
                String zone = evaluateImpactZone(collision.getGeometry().getLocalTranslation().getX());
                
                if(zone.equals("Left") || zone.equals("Right")){
                    ((Breaker)collision.getGeometry()).getDirection().negateLocal();
                }else{
                    ((Breaker)collision.getGeometry()).changeDirection(collision);
                }
                
                
                    
                
                
                
            }
            
        }
        
        
        
//        String activePower = (((Arkanoid) spatial).getActivePower());
////        System.out.println(activePower);
//        if (activePower != null) {
//            if (activePower.equals("FIRE")) {
//                
//                ((Arkanoid) spatial).setActivePower(null);
//                ((Arkanoid) spatial).setCurrentPower("FIRE");
//                
//                Vector3f position = ((Arkanoid) spatial).getLocalTranslation(); //Get arkanoid current position
//                ((Arkanoid) spatial).transformToSpaceship(stateManager, (Node)rootNode.getChild("BreakerBarNode"), position);   
//            }
//        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    private String evaluateImpactZone(float xBallPosition){
        String impact = "";
        
        if(xBallPosition == spatial.getWorldTranslation().x){
            impact = "Middle";
        }
        
        if(xBallPosition >= (spatial.getWorldTranslation().x - width/2) &&  xBallPosition <= (spatial.getWorldTranslation().x - ((width/2) + 0.01f))){
            impact = "Left";
        }
        
        if(xBallPosition <= (spatial.getWorldTranslation().x + width/2) && xBallPosition >= (spatial.getWorldTranslation().x - ((width/2) - 0.01f))){
            impact = "Right";
        }
        
        return impact;
    
    }
}
