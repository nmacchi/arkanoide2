/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.asset.TextureKey;
import com.jme3.texture.Texture;
import java.util.Random;
import static mygame.Powerup.PowerTypes.EXPAND;

/**
 *
 * @author nicolas
 */
public class PowerupType {
    
    enum PowerTypes{EXPAND,CATCH,LIFE,SLOWER,FIRE}
    
    private static Random random = new Random();

    private String name;
    private Texture texture;
    private AssetManager assetManager;
    
    PowerupType(AssetManager assetManager){
        this.assetManager = assetManager;
        selectType();
    }
    
    private void selectType(){
        int pick = random.nextInt(Powerup.PowerTypes.values().length);
       
        switch(Powerup.PowerTypes.values()[pick]){
            case EXPAND:
                this.name = Powerup.PowerTypes.values()[pick].toString();
                this.texture = assetManager.loadTexture(new TextureKey("Textures/pill/pill_texture_blue.png", false));
                break;
            case CATCH:
                this.name = Powerup.PowerTypes.values()[pick].toString();        
                this.texture = assetManager.loadTexture(new TextureKey("Textures/pill/pill_texture_green.png", false));
                break;
            case LIFE:
                this.name = Powerup.PowerTypes.values()[pick].toString();
                this.texture = assetManager.loadTexture(new TextureKey("Textures/pill/pill_texture_gray.png", false));
                break;
            case SLOWER:
                this.name = Powerup.PowerTypes.values()[pick].toString();
                this.texture = assetManager.loadTexture(new TextureKey("Textures/pill/pill_texture_light_blue.png", false));
                break;
            case FIRE:
                this.name = Powerup.PowerTypes.values()[pick].toString();
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
