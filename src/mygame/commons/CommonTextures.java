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
    
    //PORWERUPS
    public static Texture POWERUP_FIREBALL;
    public static Texture POWERUP_SLOWER;
    public static Texture POWERUP_LIFE;
    public static Texture POWERUP_EXTRABALL;
    public static Texture POWERUP_FIRE;
    public static Texture POWERUP_EXPAND;
    
    //BRICKS 
    public static Texture RED_BRICK;
    public static Texture BLUE_BRICK;
    public static Texture GREEN_BRICK;
    public static Texture PURPLE_BRICK;
    public static Texture YELLOW_BRICK;
    public static Texture ORANGE_BRICK;
    public static Texture PINK_BRICK;
    public static Texture METALLIC_BRICK;
    
    //BREAKER
    public static Texture BREAKER;
    
    //BACKGROUND
    public static Texture BACKGROUND;
    
    public CommonTextures(){ }
    
    public CommonTextures(AssetManager assetManager){
        CommonTextures.assetManager = assetManager;
    }
    
    public void loadTextures(){
        //POWERUPS
        POWERUP_FIREBALL = assetManager.loadTexture(new TextureKey(PowerupType.FIREBALL.getTexture(), false));
        POWERUP_SLOWER = assetManager.loadTexture(new TextureKey(PowerupType.SLOWER.getTexture(), false));
        POWERUP_LIFE = assetManager.loadTexture(new TextureKey(PowerupType.LIFE.getTexture(), false));
        POWERUP_EXTRABALL = assetManager.loadTexture(new TextureKey(PowerupType.EXTRA_BALLS.getTexture(), false));
        POWERUP_FIRE = assetManager.loadTexture(new TextureKey(PowerupType.FIRE.getTexture(), false));
        POWERUP_EXPAND = assetManager.loadTexture(new TextureKey(PowerupType.EXPAND.getTexture(), false));
        
        //BRICKS
        RED_BRICK = assetManager.loadTexture(new TextureKey(BricksType.RED_BRICK.getTexturePath(), false));
        BLUE_BRICK = assetManager.loadTexture(new TextureKey(BricksType.BLUE_BRICK.getTexturePath(), false));
        GREEN_BRICK = assetManager.loadTexture(new TextureKey(BricksType.GREEN_BRICK.getTexturePath(), false));
        PURPLE_BRICK = assetManager.loadTexture(new TextureKey(BricksType.PURPLE_BRICK.getTexturePath(), false));
        YELLOW_BRICK = assetManager.loadTexture(new TextureKey(BricksType.YELLOW_BRICK.getTexturePath(), false));
        ORANGE_BRICK = assetManager.loadTexture(new TextureKey(BricksType.ORANGE_BRICK.getTexturePath(), false));
        PINK_BRICK = assetManager.loadTexture(new TextureKey(BricksType.PINK_BRICK.getTexturePath(), false));
        METALLIC_BRICK = assetManager.loadTexture(new TextureKey(BricksType.METALLIC_BRICK.getTexturePath(), false));
        
        //BREAKER
        BREAKER = assetManager.loadTexture("Textures/breaker.jpg");
        
        //BACKGROUND
        BACKGROUND = assetManager.loadTexture(new TextureKey("Textures/space.jpg", true));
    }
    
    
    
    
}
