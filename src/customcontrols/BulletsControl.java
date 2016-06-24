/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package customcontrols;

import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import mygame.Brick;
import mygame.Bullet;

/**
 *
 * @author nicolas
 */
public class BulletsControl extends AbstractControl{
    
//    Bullet bullet;
    Node rootNode;
    
    public BulletsControl(Node rootNode){
        this.rootNode = rootNode;
    }
    
    @Override
    public void setSpatial(Spatial spatial) {
        //super.setSpatial(spatial);
        this.spatial = (Bullet) spatial;
    }
    
    
    @Override
    protected void controlUpdate(float tpf) {
        spatial.setLocalTranslation(spatial.getWorldTranslation().x, spatial.getWorldTranslation().y + tpf*Bullet.getBulletSpeed(), spatial.getWorldTranslation().z);
        
        CollisionResults results = new CollisionResults();
        
        rootNode.collideWith(spatial.getWorldBound(), results);
        if(results.size() > 0){
            System.out.println("LLEGA");
            CollisionResult collision = results.getClosestCollision();
            
            if(((Node)collision.getGeometry().getParent()).getName().equals("BricksNode")){
                spatial.removeFromParent();
                Brick brick = (Brick)collision.getGeometry();
                brick.removeBrick(rootNode, brick);
                
            }
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        
    }
    
}
