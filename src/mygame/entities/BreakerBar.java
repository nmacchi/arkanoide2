/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.entities;

import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingBox;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import mygame.commons.PowerupType;


/**
 *
 * @author nicolas
 */
public abstract class BreakerBar extends Geometry {
    

    protected AssetManager assetManager;
    protected float speed = 0.75f;
    private static Vector3f direction = new Vector3f();
    
    private static float maxLeftLimit = -0.68f;
    private static float maxRightLimit = 0.65f;
    
//    protected PowerupType PowerupType;
//    protected String activePower;
//    private static String currentPower;
    
    private static PowerupType currentPower;
    
    protected float width;
    
   
    public BreakerBar(){
        
    }
    
    public BreakerBar(AssetManager assetManager){
        this.assetManager = assetManager; 
        setName("BreakerBar");
        BreakerBar.currentPower = PowerupType.NA;
        //this.width = calculateGeometryWidth();
    }
    
    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

//    public PowerupType getPowerupType() {
//        return PowerupType;
//    }
//
//    public void setPowerupType(PowerupType PowerupType) {
//        this.PowerupType = PowerupType;
//    }

//    public String getActivePower() {
//        return activePower;
//    }
//
//    public void setActivePower(String activePower) {
//        this.activePower = activePower;
//    }

    public static Vector3f getDirection() {
        return direction;
    }

    public static float getMaxLeftLimit() {
        return maxLeftLimit;
    }

    public static float getMaxRightLimit() {
        return maxRightLimit;
    }

    public static PowerupType getCurrentPower() {
        return currentPower;
    }

    public static void setCurrentPower(PowerupType currentPower) {
        BreakerBar.currentPower = currentPower;
    }
    
    public float calculateGeometryWidth(){
        return ((BoundingBox)this.getWorldBound()).getXExtent();
    }
    
    public float getWidth(){
        return this.width;
    }
    
    public void setWidth(float width){
        this.width = width;
    }
    
    public String evaluateImpactZone(float xBallPosition){
        String impact = "";
        
//        if(xBallPosition >= (this.getWorldTranslation().x - 0.01f) &&  xBallPosition <= (this.getWorldTranslation().x + 0.01f)){
//            impact = "Center";
//        }
        
        if(xBallPosition == this.getWorldTranslation().x){
            impact = "Center";
        }
        
        if(xBallPosition >= (this.getWorldTranslation().x - width) &&  xBallPosition <= (this.getWorldTranslation().x - width + 0.03f)){
            impact = "Left";
        }
        
        if(xBallPosition <= (this.getWorldTranslation().x + width) && xBallPosition >= (this.getWorldTranslation().x + width - 0.03f)){
            impact = "Right";
        }
        return impact;
    
    }
    
    
    
}
