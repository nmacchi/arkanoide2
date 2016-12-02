/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.asset.TextureKey;
import com.jme3.texture.Texture;
import java.util.Random;

/**
 *
 * @author nicolas
 */
public class PowerupType {
    
    public enum PowerTypes{FIREBALL,EXTRA_BALLS,LIFE,SLOWER,FIRE}
    
    private static Random random = new Random();

    private String name;
    private Texture texture;
    private AssetManager assetManager;
    
    PowerupType(AssetManager assetManager){
        this.assetManager = assetManager;
        selectType();
    }
    
    private void selectType(){
        int pick = random.nextInt(PowerTypes.values().length);
//        int pick = 0;
        switch(PowerTypes.values()[pick]){
            case FIREBALL:
                this.name = PowerTypes.values()[pick].toString();
                this.texture = assetManager.loadTexture(new TextureKey("Textures/pill/pill_texture_blue.png", false));
                break;
            case EXTRA_BALLS:
                this.name = PowerTypes.values()[pick].toString();        
                this.texture = assetManager.loadTexture(new TextureKey("Textures/pill/pill_texture_light_blue.png", false));
                break;
            case LIFE:
                this.name = PowerTypes.values()[pick].toString();
                this.texture = assetManager.loadTexture(new TextureKey("Textures/pill/pill_texture_gray.png", false));
                break;
            case SLOWER:
                this.name = PowerTypes.values()[pick].toString();
                this.texture = assetManager.loadTexture(new TextureKey("Textures/pill/pill_texture_green.png", false));
                break;
            case FIRE:
                this.name = PowerTypes.values()[pick].toString();
                this.texture = assetManager.loadTexture(new TextureKey("Textures/pill/pill_texture_red.png", false));
                break;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }
    
    
}
