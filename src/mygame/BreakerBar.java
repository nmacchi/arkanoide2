/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;

/**
 *
 * @author nicolas
 */
public class BreakerBar extends Geometry{
    private int score;
    private int lifes;
    
    //Dimentions
    private static float width = 0.12f;
    private static float height = 0.022f;
    private static float deep = 0.025f;
    
    private float speed = 0.7f;
    private Vector3f position;
    
    private boolean ballShooted;
    private static Vector3f initialPosition = new Vector3f(0.25f, 0.03f, 1f); 
    
    public BreakerBar(AssetManager assetManager){
        Geometry geometry = (Geometry)((Node)assetManager.loadModel("Models/arkanoide/Arkanoide.j3o")).getChild(0);
        
        setName("BreakerBar");
        setMesh(geometry.getMesh());
        setMaterial(geometry.getMaterial());
        
        setLocalTranslation(initialPosition);
        scale(0.06f, 0.07f, 0.045f);
        rotate(0f,1.60f,0f);
        
        this.lifes = 3;  //Set default lifes
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public boolean isBallShooted() {
        return ballShooted;
    }

    public final void setBallShooted(boolean ballShooted) {
        this.ballShooted = ballShooted;
    }
    
    public void move(String direction, float value, Breaker breaker){
        position = this.getLocalTranslation();
        if(direction.equals("Left")){
            setLocalTranslation(position.x - value*speed, position.y, position.z);
            if(!isBallShooted()){
               breaker.setLocalTranslation(breaker.getLocalTranslation().getX() - value*speed, breaker.getLocalTranslation().getY(), breaker.getLocalTranslation().getZ());
            }
        }
        
        if(direction.equals("Right")){
            setLocalTranslation(getLocalTranslation().x + value*speed, position.y, position.z);
            if(!isBallShooted()){
               breaker.setLocalTranslation(breaker.getLocalTranslation().getX() + value*speed, breaker.getLocalTranslation().getY(), breaker.getLocalTranslation().getZ());
            }
        }
        
        if(!ballShooted){
           breaker.getLocalTranslation().x = FastMath.clamp(breaker.getLocalTranslation().x, -0.60f, 0.60f);
        }
        
        getLocalTranslation().x = FastMath.clamp(getLocalTranslation().x, -0.60f, 0.60f);
    }



    public static Vector3f getInitialPosition() {
        return initialPosition;
    }

    public static void setInitialPosition(Vector3f initialPosition) {
        BreakerBar.initialPosition = initialPosition;
    }
    
    public static float getWidth() {
        return width;
    }

    public static void setWidth(float width) {
        BreakerBar.width = width;
    }

    public static float getHeight() {
        return height;
    }

    public static void setHeight(float height) {
        BreakerBar.height = height;
    }

    public static float getDeep() {
        return deep;
    }

    public static void setDeep(float deep) {
        BreakerBar.deep = deep;
    }

    public int getScore() {
        return score;
    }
    
    public String getFormattedScore(){
        return String.format("%08d", score);
    }
    
    public void setScore(int score) {
        this.score += score;
    }

    public int getLifes() {
        return lifes;
    }

    public void setLifes(int lifes) {
        this.lifes = lifes;
    }
    
    public void restLife(){
        this.lifes--;
    }
    
//    public boolean isImpactInBorderZone(Breaker breaker){
//        float impactPos = breaker.getLocalTranslation().x;
//
//        float posLeft = getLocalTranslation().x - (getWidth());
//        float posLeft2 = getLocalTranslation().x - (getWidth() / 1.5f);
//
//        float posRight = getLocalTranslation().x + (getWidth());
//        float posRight2 = getLocalTranslation().x + (getWidth() / 1.5f);
//
//        if ((impactPos >= posLeft && impactPos <= posLeft2) || (impactPos <= posRight && impactPos >= posRight2)) {
//            return true;
//        } 
//        
//        return false;
//    }
    
}
