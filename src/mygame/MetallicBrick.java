/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.math.Vector3f;
import mygame.commons.BricksType;

/**
 *
 * @author nicolas.macchi
 */
public class MetallicBrick extends Brick {
    
    private static final int DEFAULT_POINTS = 50;
    private static final int DEFAULT_HARDNESS = 2; 
    
    public MetallicBrick(AssetManager assetManager, Vector3f position, AppStateManager stateManager){
        super(assetManager, position, stateManager);
        
        //material.setTexture("DiffuseMap", assetManager.loadTexture(new TextureKey("Textures/metal_brick.jpg", false)));
        material.setTexture("DiffuseMap", BricksType.getBrickTextureByType(BricksType.METALLIC_BRICK));
        
        this.setPoints(DEFAULT_POINTS);
        this.setHardness(DEFAULT_HARDNESS);
    }
    
    public void playMetallicReboundSound(){
        ((AudioNode)this.getParent().getParent().getChild("metallicReboundAudio")).playInstance();
    }
}
