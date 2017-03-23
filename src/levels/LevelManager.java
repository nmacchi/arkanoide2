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
    
    private static final List<Level> levels = new ArrayList<Level>();
    private AssetManager assetManager;
    private AppStateManager stateManager;
    private Node bricksNode;
    
    private static int index;
    
    private static Boolean isLevelLoading;
    
    public LevelManager(){
        
    }
    
    public LevelManager(AssetManager assetManager, AppStateManager stateManager, Node bricksNode){
        this.assetManager = assetManager;
        this.stateManager = stateManager;
        this.bricksNode = bricksNode;
        
        index = 0; //Spot to the first level by default
    }
    
    public static void loadLevels(){
        levels.add(new Level1());
        levels.add(new Level1());
        levels.add(new Level1());
        //ADD MORE LEVELS
    }
    
    /**
     * Attach bricks to parent node
     */
    public void initLevel(){
        isLevelLoading = true;
        ((Level)levels.get(index)).buildLevel(assetManager, stateManager, bricksNode);
    }
    
    public int nextLevel(){
        bricksNode.detachAllChildren();
        initLevel();
        index++;
        return index;
    }
    
    public static int getCurrentLevel(){
        return index + 1;
    }

    public static Boolean IsLevelLoading() {
        return isLevelLoading;
    }

    public static void setLevelLoading(Boolean isLevelLoading) {
        LevelManager.isLevelLoading = isLevelLoading;
    }

    
    
    
    
}
