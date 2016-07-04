/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.entities;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import mygame.PowerupType;

/**
 *
 * @author nicolas
 */
public class BreakerBar extends Geometry{
    

    protected AssetManager assetManager;
    protected float speed = 0.7f;
    private static Vector3f direction = new Vector3f();
    
    private static float maxLeftLimit = -0.60f;
    private static float maxRightLimit = 0.60f;
    
    protected PowerupType PowerupType;
    protected String activePower;
    protected String currentPower;
    
    public BreakerBar(){
        
    }
    
    public BreakerBar(AssetManager assetManager){
        this.assetManager = assetManager; 
        setName("BreakerBar");         
    }
    
    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public PowerupType getPowerupType() {
        return PowerupType;
    }

    public void setPowerupType(PowerupType PowerupType) {
        this.PowerupType = PowerupType;
    }

    public String getActivePower() {
        return activePower;
    }

    public void setActivePower(String activePower) {
        this.activePower = activePower;
    }

    public static Vector3f getDirection() {
        return direction;
    }

    public static float getMaxLeftLimit() {
        return maxLeftLimit;
    }

    public static float getMaxRightLimit() {
        return maxRightLimit;
    }

    public String getCurrentPower() {
        return currentPower;
    }

    public void setCurrentPower(String currentPower) {
        this.currentPower = currentPower;
    }
    
    
    
}
