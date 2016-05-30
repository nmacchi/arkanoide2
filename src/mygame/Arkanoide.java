/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.math.FastMath;
import com.jme3.scene.Node;

/**
 *
 * @author nicolas
 */
public class Arkanoide extends Node{
    
    Arkanoide(BreakerBar breakerBar, Breaker breaker){
        setName("arkanoide");
        attachChild(breakerBar);
        attachChild(breaker);
    }
    
    
    public void moveChildren(String direction, float value){
        //        position = this.getParent().getLocalTranslation();
        if(direction.equals("Left")){
            
            setLocalTranslation(getLocalTranslation().x - value*((BreakerBar)getChild(0)).getSpeed(), getLocalTranslation().y, getLocalTranslation().z);
//            if(!isBallShooted()){
//               breaker.setLocalTranslation(breaker.getLocalTranslation().getX() - value*speed, breaker.getLocalTranslation().getY(), breaker.getLocalTranslation().getZ());
//            }
        }
        
        if(direction.equals("Right")){
//            setLocalTranslation(getLocalTranslation().x + value*speed, position.y, position.z);
//            if(!isBallShooted()){
//               breaker.setLocalTranslation(breaker.getLocalTranslation().getX() + value*speed, breaker.getLocalTranslation().getY(), breaker.getLocalTranslation().getZ());
//            }
        }
        System.out.println(this.getLocalTranslation());
    }
}
