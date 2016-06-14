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
public class GamePlayAppState extends AbstractAppState{
    
    private int currentLives;
    private int score;
    
    private BreakerBar arkanoid;
    private Breaker ball;
    
    AppStateManager stateManager;
    SimpleApplication app;
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        
        this.stateManager = stateManager;
        this.app = (SimpleApplication)app;
        
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
    
    public int getCurrentLives() {
        return currentLives;
    }

    public void setCurrentLives(int currentLives) {
        this.currentLives = currentLives;
    }
    
    public void restLife(){
        this.currentLives--;
        //Call Update GUI 
    }
    
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score += score;
        stateManager.getState(GameGuiAppState.class).updateScoreIndicator();
    }

    public String getFormattedScore(){
        return String.format("%08d", score);
    }
    
    public void reset(){
        restLife();
        
        //Call GUI state for update
        stateManager.getState(GameGuiAppState.class).updateLivesIndicator(app, currentLives);
              
        //Set entities to initial position
        ((BreakerBar)app.getRootNode().getChild("BreakerBar")).removeFromParent();
        ((Breaker)app.getRootNode().getChild("Breaker")).removeFromParent();
        
        //Hacer algun efecto de explosion
        
        app.getRootNode().attachChild(stateManager.getState(GamePlayAppState.class).getArkanoid());
        app.getRootNode().attachChild(stateManager.getState(GamePlayAppState.class).getBall());

    }
}
