/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import mygame.commons.BricksType;

/**
 *
 * @author nicolas.macchi
 */
public class CommonBrick extends Brick {

    //private static List<String> brickColors = Arrays.asList("blue", "red", "green", "purple", "yellow", "pink", "orange");
    private static final int DEFAULT_POINTS = 25;
    private static final int DEFAULT_HARDNESS = 1;
    private Powerup powerup;
    private final BricksType type;
    
    /**
     *
     * @param assetManager
     * @param position
     * @param index
     * @param stateManager
     */

    public CommonBrick(AssetManager assetManager, Vector3f position, Integer index, AppStateManager stateManager) {
        super(assetManager, position, stateManager);

        //String color = brickColors.get(index);
        //material.setTexture("DiffuseMap", assetManager.loadTexture(new TextureKey("Textures/brick/texture_"+color+".png", false)));
        type = BricksType.getBrickByIndex(index);
        material.setTexture("DiffuseMap", BricksType.getBrickTextureByType(type));
        
        points = DEFAULT_POINTS;
        hardness = DEFAULT_HARDNESS;
    }

    public Powerup getPowerup() {
        return powerup;
    }

    public void setPowerup(Powerup powerup) {
        this.powerup = powerup;
    }
    
    
}
