/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.commons;

import com.jme3.asset.AssetManager;
import com.jme3.asset.TextureKey;
import com.jme3.texture.Texture;

/**
 *
 * @author nicolas
 */
public class CommonTextures {
    
    private static AssetManager assetManager;
    
    //Porwerups
    public static Texture POWERUP_FIREBALL;
    public static Texture POWERUP_SLOWER;
    public static Texture POWERUP_LIFE;
    public static Texture POWERUP_EXTRABALL;
    public static Texture POWERUP_FIRE;
    
    
    public CommonTextures(){ }
    
    public CommonTextures(AssetManager assetManager){
        CommonTextures.assetManager = assetManager;
    }
    
    private void loadTextures(){
        POWERUP_FIREBALL = assetManager.loadTexture(new TextureKey(PowerupType.FIREBALL.getTexture(), false));
        POWERUP_SLOWER = assetManager.loadTexture(new TextureKey(PowerupType.SLOWER.getTexture(), false));
        POWERUP_LIFE = assetManager.loadTexture(new TextureKey(PowerupType.LIFE.getTexture(), false));
        POWERUP_EXTRABALL = assetManager.loadTexture(new TextureKey(PowerupType.EXTRA_BALLS.getTexture(), false));
        POWERUP_FIRE = assetManager.loadTexture(new TextureKey(PowerupType.FIRE.getTexture(), false));
    }
    
    
    
    
}
