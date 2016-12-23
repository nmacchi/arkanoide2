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
import customcontrols.MetallicBrickLightingControl;

/**
 *
 * @author nicolas.macchi
 */
public class MetallicBrick extends Brick {
    
    private static int DEFAULT_POINTS = 50;
    private static int DEFAULT_HARDNESS = 2; 
    
    public MetallicBrick(AssetManager assetManager, Vector3f position, AppStateManager stateManager){
        super(assetManager, position, stateManager);
        
        material.setTexture("DiffuseMap", assetManager.loadTexture(new TextureKey("Textures/metal_brick.jpg", false)));
        
        this.setPoints(DEFAULT_POINTS);
        this.setHardness(DEFAULT_HARDNESS);
    }
    
    public void playMetallicReboundSound(){
        ((AudioNode)this.getParent().getParent().getChild("metallicReboundAudio")).playInstance();
    }
    
    public void doReboundLightEffect(){
      MetallicBrickLightingControl control = new MetallicBrickLightingControl(this.getParent(), assetManager);  
      control.setSpatial(this);
      control.createSpotLight();
      control.setEnabled(true);
      this.addControl(control);
    }
}
