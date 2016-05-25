/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.asset.TextureKey;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author nicolas.macchi
 */
public class CommonBrick extends Brick {

    private static List<String> brickColors = Arrays.asList("blue", "red", "green", "purple", "yellow");
    private static int DEFAULT_POINTS = 25;
    private static ColorRGBA default_color = new ColorRGBA(18f / 255f, 128f / 255f, 2f / 255f, 1f);
    private static int DEFAULT_HARDNESS = 1;
    private Powerup powerup;

    /**
     *
     */
    CommonBrick(AssetManager assetManager, Vector3f position) {
        super(assetManager, position);

        material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        material.setColor("Color", default_color);
        setMaterial(material);


        this.setPoints(DEFAULT_POINTS);
        this.setHardness(DEFAULT_HARDNESS);
    }

    CommonBrick(AssetManager assetManager, Vector3f position, Integer index) {
        super(assetManager, position);

        String color = brickColors.get(index);
        material.setTexture("DiffuseMap", assetManager.loadTexture(new TextureKey("Textures/brick/texture_"+color+".png", false)));
    }

    public Powerup getPowerup() {
        return powerup;
    }

    public void setPowerup(Powerup powerup) {
        this.powerup = powerup;
    }
}
