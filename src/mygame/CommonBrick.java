/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.asset.TextureKey;
import com.jme3.audio.AudioNode;
import com.jme3.math.Vector3f;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author nicolas.macchi
 */
public class CommonBrick extends Brick {

    private static List<String> brickColors = Arrays.asList("blue", "red", "green", "purple", "yellow", "pink", "orange");
    private static int DEFAULT_POINTS = 25;
    private static int DEFAULT_HARDNESS = 1;
    private Powerup powerup;

    /**
     *
     */

    public CommonBrick(AssetManager assetManager, Vector3f position, Integer index, AppStateManager stateManager) {
        super(assetManager, position, stateManager);

        String color = brickColors.get(index);
        material.setTexture("DiffuseMap", assetManager.loadTexture(new TextureKey("Textures/brick/texture_"+color+".png", false)));
        
        this.setPoints(DEFAULT_POINTS);
        this.setHardness(DEFAULT_HARDNESS);
    }

    public Powerup getPowerup() {
        return powerup;
    }

    public void setPowerup(Powerup powerup) {
        this.powerup = powerup;
    }
    
    
}
