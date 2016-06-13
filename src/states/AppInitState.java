/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package states;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import customcontrols.BreakerControl;
import mygame.Breaker;
import mygame.BreakerBar;

/**
 * Init principal entities, audio effects and key inputs
 * 
 * @author nicolas
 */
public class AppInitState extends AbstractAppState{
    
    private BreakerBar arkanoid;
    private Breaker ball;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);

        arkanoid = new BreakerBar(app.getAssetManager());
        ball = new Breaker(app.getAssetManager());
        ball.addControl(new BreakerControl(((SimpleApplication)app).getRootNode(), arkanoid, stateManager));
        
        ((SimpleApplication)app).getRootNode().attachChild(arkanoid);
        ((SimpleApplication)app).getRootNode().attachChild(ball);
    }

    public BreakerBar getArkanoid() {
        return arkanoid;
    }

    public Breaker getBall() {
        return ball;
    }
    
    
}
