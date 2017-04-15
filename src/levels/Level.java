/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package levels;

import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.math.FastMath;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import mygame.CommonBrick;

/**
 *
 * @author nicolas
 */
public abstract class Level {
    
    protected static float BRICK_SEPARATOR = 0.01f;
    
    protected Node parent;
    
    /**
     * Set the percentaje of powerups related to the amount of common bricks per level  
     */
    private static final int PERCENTAJE_REWARDS = 15;
    
    public abstract void buildLevel(AssetManager assetManager, AppStateManager stateManager, Node parent);
    
    public int calculateNumberOfPowerups(Node parent){
        int countCommonBricks = 0;
        for(Spatial brick : parent.getChildren()){
            if(brick instanceof CommonBrick){
                countCommonBricks++;
            }
        }
        
        Float powerupsAmount = FastMath.floor((countCommonBricks * PERCENTAJE_REWARDS) / 100);
        return powerupsAmount.intValue();
    }
    
}
