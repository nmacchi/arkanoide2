/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package levels;

import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nicolas
 */
public class LevelManager {
    
    private List<Level> levels = new ArrayList<Level>();
    private AssetManager assetManager;
    private AppStateManager stateManager;
    private Node bricksNode;
    
    private int index;
    
    public LevelManager(){
        
    }
    
    public LevelManager(AssetManager assetManager, AppStateManager stateManager, Node bricksNode){
        this.assetManager = assetManager;
        this.stateManager = stateManager;
        this.bricksNode = bricksNode;
    }
    
    public void loadLevels(){
        levels.add(new Level1());
        //ADD MORE LEVELS
    }
    
    public void initLevel(){
        ((Level)levels.get(index)).buildLevel(assetManager, stateManager, bricksNode);
    }
    
    public void nextLevel(){
        index++;
        bricksNode.detachAllChildren();
        initLevel();
    }
    
}
