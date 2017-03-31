/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.commons;

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
    FIRE(5, "POWERUP_FIRE", "Textures/pill/pill_texture_red.png"),
    EXPAND(6, "POWERUP_EXPAND", "Textures/pill/pill_texture_pink.png");
    
    
    private final int codigo;
    private final String description;
    private final String texture;

    private static Random random = new Random();
    private static final int MIN_VALUE = 1;
    
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
    
    
    public static PowerupType getRandomType(){
        int pick = random.nextInt(5) + 1;
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
            case EXPAND:
                texture = CommonTextures.POWERUP_EXPAND;
                break;
        }
        
        return texture;
    }
    
    
}
