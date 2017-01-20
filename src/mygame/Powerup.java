/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.asset.TextureKey;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import java.util.Random;
import static mygame.PowerupType.PowerTypes.EXTRA_BALLS;
import static mygame.PowerupType.PowerTypes.FIRE;
import static mygame.PowerupType.PowerTypes.FIREBALL;
import static mygame.PowerupType.PowerTypes.LIFE;
import static mygame.PowerupType.PowerTypes.SLOWER;
import mygame.commons.PowerupType;

/**
 *
 * @author nicolas.macchi
 */
public class Powerup extends Geometry {
    
//    enum PowerTypes{EXPAND,CATCH,LIFE,SLOWER,FIRE}
    
    private static int DEFAULT_POINTS = 75;
    private float speed = 2f;
    private int points;
    
    private PowerupType type;
    private AssetManager assetManager;
    
    private static Random random = new Random();
    
    public Powerup(AssetManager assetManager, Brick brick){  
        //type = new PowerupType(assetManager);
        
        Geometry pill = (Geometry) ((Node) assetManager.loadModel("Models/pill/Sphere.001.mesh.j3o")).getChild(0);
        setMesh(pill.getMesh());
        setName("Powerup");

        material = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        material.setBoolean("UseMaterialColors", true);
        material.setColor("Specular", ColorRGBA.White);
        material.setColor("Ambient", ColorRGBA.White);
        material.setFloat("Shininess", 32f);
//        material.setTexture("DiffuseMap", type.getTexture());
        material.setTexture("DiffuseMap", selectTextureByPowerupType());
        
        setMaterial(material);
        rotate(0f,1.60f,0f);
        scale(0.07f, 0.07f, 0.08f);
        
        setLocalTranslation(brick.getLocalTranslation());
        setPoints(DEFAULT_POINTS);
        
    } 

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public int getPoints() {
        return points;
    }

    private void setPoints(int points) {
        this.points = points;
    }

    public PowerupType getType() {
        return type;
    }

    public void setType(PowerupType type) {
        this.type = type;
    }
   
    
    private Texture selectTextureByPowerupType(){
        int pick = random.nextInt(PowerupType.values().length);
//        int pick = 2;
        switch(PowerupType.values()[pick]){
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
    }
    
}
