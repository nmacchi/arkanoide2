/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package factories;

import com.jme3.app.SimpleApplication;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import customcontrols.BreakerBarControl;
import mygame.commons.BreakerBarTypes;
import mygame.entities.Arkanoid;
import mygame.entities.Spaceship;

/**
 *
 * @author nicolas
 */
public class BreakerBarFactory extends BreakerBarFactoryMethod {
    
    @Override
    public void createrBar(BreakerBarTypes barType, Node parent, SimpleApplication app, Vector3f position) {
        
        parent.detachAllChildren();
        
        
        if(barType.equals(BreakerBarTypes.ARKANOID)){
            //Arkanoid arkanoid;
            
            //if(position != null){
              Arkanoid arkanoid = new Arkanoid(app.getAssetManager(), position, parent);
                //arkanoid.setLocalTranslation(position);
            //}else{
            //   arkanoid = new Arkanoid(app.getAssetManager(), parent);
            //}
            
            arkanoid.addControl(new BreakerBarControl(parent.getParent(), app));
            
        }else if (barType.equals(BreakerBarTypes.SPACESHIP)){
            Spaceship spaceship = new Spaceship(app.getAssetManager(), position, parent);
            spaceship.addControl(new BreakerBarControl(parent.getParent(), app));
        }

    }
}
