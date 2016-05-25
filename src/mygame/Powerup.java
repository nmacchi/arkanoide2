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
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author nicolas.macchi
 */
public class Powerup extends Geometry {
    
    enum PowerTypes{EXPAND,CATCH,LIFE,SLOWER,FIRE}
    
    private static int DEFAULT_POINTS = 75;
    private float speed = 2f;
    private int points;
    
 
    private static Random random = new Random();
    
    public Powerup(AssetManager assetManager, Brick brick){  

        Geometry pill = (Geometry) ((Node) assetManager.loadModel("Models/pill/Sphere.001.mesh.xml")).getChild(0);
        setMesh(pill.getMesh());
        setName("Thing");

        material = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        material.setBoolean("UseMaterialColors", true);
        material.setColor("Specular", ColorRGBA.White);
        material.setColor("Ambient", ColorRGBA.White);
        material.setFloat("Shininess", 32f);
        
        selectTextureByPowerType(assetManager);
        
        setMaterial(material);
        rotate(0f,1.60f,0f);
        scale(0.07f, 0.07f, 0.08f);
        
        setLocalTranslation(brick.getLocalTranslation());
        setPoints(DEFAULT_POINTS);
        
    } 

    private void selectTextureByPowerType(AssetManager assetManager){
        int pick = random.nextInt(PowerTypes.values().length);
       
        switch(PowerTypes.values()[pick]){
            case EXPAND:
                material.setTexture("DiffuseMap", assetManager.loadTexture(new TextureKey("Textures/pill/pill_texture_blue.png", false)));
                break;
            case CATCH:
                material.setTexture("DiffuseMap", assetManager.loadTexture(new TextureKey("Textures/pill/pill_texture_green.png", false)));
                break;
            case LIFE:
                material.setTexture("DiffuseMap", assetManager.loadTexture(new TextureKey("Textures/pill/pill_texture_gray.png", false)));
                break;
            case SLOWER:
                material.setTexture("DiffuseMap", assetManager.loadTexture(new TextureKey("Textures/pill/pill_texture_light_blue.png", false)));
                break;
            case FIRE:
                material.setTexture("DiffuseMap", assetManager.loadTexture(new TextureKey("Textures/pill/pill_texture_red.png", false)));
                break;
        }
    
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
   
    
    
    
}
