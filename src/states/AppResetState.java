/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package states;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import mygame.Breaker;
import mygame.BreakerBar;

/**
 *
 * @author nicolas
 */
public class AppResetState extends AbstractAppState{
    
    private AppStateManager stateManager;
    SimpleApplication app;
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
       
        app = (SimpleApplication)app;
        this.stateManager = app.getStateManager();
    }
    
    public void reset(){
        //Call GUI state for update
        int currentLives = stateManager.getState(AppPlayerState.class).getCurrentLives();
        stateManager.getState(AppGuiState.class).updateLivesIndicator(app, currentLives);
              
        //Set entities to initial position
        ((BreakerBar)app.getRootNode().getChild("BreakerBar")).removeFromParent();
        ((Breaker)app.getRootNode().getChild("Breaker")).removeFromParent();
        
        //Hacer algun efecto de explosion
        
        app.getRootNode().attachChild(stateManager.getState(AppInitState.class).getArkanoid());
        app.getRootNode().attachChild(stateManager.getState(AppInitState.class).getBall());

    }
    
    
    
}
