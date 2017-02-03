/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.commons;

/**
 *
 * @author nicolas
 */
public enum PowerupType {
    
    FIREBALL(0, "Fireball", "Textures/pill/pill_texture_blue.png"),
    EXTRA_BALLS(1, "Add extra balls", "Textures/pill/pill_texture_light_blue.png"),
    LIFE(2, "Life", "Textures/pill/pill_texture_gray.png"),
    SLOWER(3, "Decrease ball speed", "Textures/pill/pill_texture_green.png"),
    FIRE(4, "Convert ball into fire", "Textures/pill/pill_texture_red.png");
    
    private final int codigo;
    private final String description;
    private final String texture;

    
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
    
    
}
