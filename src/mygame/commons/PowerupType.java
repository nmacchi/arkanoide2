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
    
    FIREBALL(0, "Fireball", ""),
    EXTRA_BALLS(1, "Add extra balls", ""),
    LIFE(2, "Life", ""),
    SLOWER(3, "Decrease ball speed", ""),
    FIRE(4, "Convert ball into fire", "");
    
    private final int codigo;
    private final String description;
    private final String textureName;

    
    PowerupType(int codigo, String description, String textureName){
        this.codigo = codigo;
        this.description = description;
        this.textureName = textureName;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getDescription() {
        return description;
    }
    
    
}
