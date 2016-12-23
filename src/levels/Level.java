/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package levels;

import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;

/**
 *
 * @author nicolas
 */
public abstract class Level {
    
    protected static float BRICK_SEPARATOR = 0.01f;
    
//    protected AssetManager assetManager;
//    protected AppStateManager stateManager;
    protected Node parent;
    
//    public Level(){
//        
//    }
    
//    public Level(AssetManager assetManager, AppStateManager stateManager, Node parent){
//        this.assetManager = assetManager;
//        this.stateManager = stateManager;
//        this.parent = parent;
//    }
    
    public abstract void buildLevel(AssetManager assetManager, AppStateManager stateManager, Node parent);
    
}
