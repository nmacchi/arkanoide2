/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.entities;

/**
 *
 * @author nicolas
 */
public class BreakerBarFactory {
    
    public BreakerBar getBreakerBar(String type){
        if(type.equals("Arkanoid")){
            return new Arkanoid();
        }
        
        if(type.equals("Spaceship")){
            return new Spaceship();
        }
        
        return null;
    }
}
