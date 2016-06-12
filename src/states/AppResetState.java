/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package states;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.scene.Node;
import mygame.Breaker;
import mygame.BreakerBar;

/**
 *
 * @author nicolas
 */
public class AppResetState extends AbstractAppState{
    
    private Node rootNode;
    private AppStateManager stateManager;
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
       
        rootNode = ((SimpleApplication)app).getRootNode();
        stateManager = app.getStateManager();
    }
    
    public void reset(){
        stateManager.getState(AppPlayerState.class).restLife();
        
        //Set entities to initial position
        ((BreakerBar)rootNode.getChild("BreakerBar")).removeFromParent();
        ((Breaker)rootNode.getChild("Breaker")).removeFromParent();
        
        //Hacer algun efecto de explosion
        
        
        
        
        
        
//        ((Breaker)rootNode.getChild("Breaker")).setLocalTranslation();
    }
    
    
    
}
