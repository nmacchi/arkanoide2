/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.commons;

import com.jme3.asset.TextureKey;
import com.jme3.texture.Texture;
import java.util.Random;


/**
 *
 * @author nicolas
 */
public enum PowerupType {
    
    NA(0, "N/A", ""),
    FIREBALL(1, "POWERUP_FIREBALL", "Textures/pill/pill_texture_blue.png"),
    EXTRA_BALLS(2, "POWERUP_EXTRABALLS", "Textures/pill/pill_texture_light_blue.png"),
    LIFE(3, "POWERUP_LIFE", "Textures/pill/pill_texture_gray.png"),
    SLOWER(4, "POWERUP_SLOWER", "Textures/pill/pill_texture_green.png"),
    FIRE(5, "POWERUP_FIRE", "Textures/pill/pill_texture_red.png");
    
    private final int codigo;
    private final String description;
    private final String texture;

    private static Random random = new Random();
    
    PowerupType(int codigo, String description, String texture){
        this.codigo = codigo;
        this.description = description;
        this.texture = texture;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getDescription() {
        return description;
    }

    public String getTexture() {
        return texture;
    }
    
   /* private void selectType(){
        int pick = random.nextInt(mygame.PowerupType.PowerTypes.values().length);
//        int pick = 2;
        switch(mygame.PowerupType.PowerTypes.values()[pick]){
            case FIREBALL:
                //this.name = mygame.PowerupType.PowerTypes.values()[pick].toString();
                this.texture = assetManager.loadTexture(new TextureKey("Textures/pill/pill_texture_blue.png", false));
                break;
            case EXTRA_BALLS:
                //this.name = mygame.PowerupType.PowerTypes.values()[pick].toString();        
                this.texture = assetManager.loadTexture(new TextureKey("Textures/pill/pill_texture_light_blue.png", false));
                break;
            case LIFE:
                //this.name = mygame.PowerupType.PowerTypes.values()[pick].toString();
                this.texture = assetManager.loadTexture(new TextureKey("Textures/pill/pill_texture_gray.png", false));
                break;
            case SLOWER:
                //this.name = mygame.PowerupType.PowerTypes.values()[pick].toString();
                this.texture = assetManager.loadTexture(new TextureKey("Textures/pill/pill_texture_green.png", false));
                break;
            case FIRE:
                //this.name = mygame.PowerupType.PowerTypes.values()[pick].toString();
                this.texture = assetManager.loadTexture(new TextureKey("Textures/pill/pill_texture_red.png", false));
                break;
        }
    }*/
    
    
    public static PowerupType getRandomType(){
        int pick = random.nextInt(PowerupType.values().length);
        return PowerupType.values()[pick];
    }
    
    
    public static Texture selectTextureByPowerupType(PowerupType poweruptype){
        Texture texture = null;
        
        switch(poweruptype){
            case FIREBALL:
                texture = CommonTextures.POWERUP_FIREBALL;  
                break;
            case EXTRA_BALLS:
                texture = CommonTextures.POWERUP_EXTRABALL;
                break;
            case LIFE:
                texture = CommonTextures.POWERUP_LIFE;
                break;
            case SLOWER:
                texture = CommonTextures.POWERUP_SLOWER;
                break;
            case FIRE:
                texture = CommonTextures.POWERUP_FIRE;
                break;
        }
        
        return texture;
    }
    
    
}
