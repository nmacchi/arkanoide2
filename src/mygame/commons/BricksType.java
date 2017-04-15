/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.commons;

import com.jme3.texture.Texture;

/**
 *
 * @author nicolas
 */
public enum BricksType {
    
    
    RED_BRICK(0, "RED BRICK", "Textures/brick/texture_red.png"),
    BLUE_BRICK(1, "BLUE BRICK", "Textures/brick/texture_blue.png"),
    YELLOW_BRICK(2, "YELLOW BRICK", "Textures/brick/texture_yellow.png"),
    GREEN_BRICK(3, "GREEN BRICK", "Textures/brick/texture_green.png"),
    PURPLE_BRICK(4, "PURPLE BRICK", "Textures/brick/texture_purple.png"),
    PINK_BRICK(5, "PINK BRICK", "Textures/brick/texture_pink.png"),
    ORANGE_BRICK(6, "ORANGE BRICK", "Textures/brick/texture_orange.png"),
    LIGHT_BLUE_BRICK(7, "LIGHT BLUE BRICK", "Textures/brick/texture_light_blue.png"),
    METALLIC_BRICK(8, "METALLIC BRICK", "Textures/metal_brick.jpg");
    
    
    private final int codigo;
    private final String name;
    private final String texturePath;
    
    BricksType(int codigo, String name, String texturePath){
        this.codigo = codigo;
        this.name = name;
        this.texturePath = texturePath;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getName() {
        return name;
    }

    public String getTexturePath() {
        return texturePath;
    }
    
    public static BricksType getBrickByIndex(int index){
        return BricksType.values()[index];
    }
    
    public static Texture getBrickTextureByType(BricksType type){
        Texture texture = null;
        
        switch(type){
            case RED_BRICK:
                texture = CommonTextures.RED_BRICK;
                break;
            case BLUE_BRICK:
                texture = CommonTextures.BLUE_BRICK;
                break;
            case YELLOW_BRICK:
                texture = CommonTextures.YELLOW_BRICK;
                break;
            case GREEN_BRICK:
                texture = CommonTextures.GREEN_BRICK;
                break;
            case PINK_BRICK:
                texture = CommonTextures.PINK_BRICK;
                break;
            case PURPLE_BRICK:
                texture = CommonTextures.PURPLE_BRICK;
                break;
            case ORANGE_BRICK:
                texture = CommonTextures.ORANGE_BRICK;
                break;
            case LIGHT_BLUE_BRICK:
                texture = CommonTextures.LIGHT_BLUE_BRICK;
                break;
            case METALLIC_BRICK:
                texture = CommonTextures.METALLIC_BRICK;
                break;
        }
        
        return texture;
    } 
}
