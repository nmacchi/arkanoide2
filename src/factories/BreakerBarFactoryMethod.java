/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package factories;

import com.jme3.app.SimpleApplication;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import mygame.commons.BreakerBarTypes;

/**
 *
 * @author nicolas
 */
public abstract class BreakerBarFactoryMethod {
    
    public abstract void createrBar(BreakerBarTypes barType, Node parentNode, SimpleApplication simpleApplication, Vector3f position);
    
    
    
}
