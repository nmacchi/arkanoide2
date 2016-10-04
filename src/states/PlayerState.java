/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package states;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;

/**
 *
 * @author nicolas
 */
public class PlayerState extends AbstractAppState {
    
    private static int INITIAL_DEFAULT_LIVES = 3;
    private int currentLives;
    private int score;
    private AppStateManager stateManager;
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        
        this.stateManager = stateManager;
        setCurrentLives(INITIAL_DEFAULT_LIVES);
    }
    
    public int getCurrentLives() {
        return currentLives;
    }

    public void setCurrentLives(int currentLives) {
        this.currentLives = currentLives;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score += score;
        stateManager.getState(GameGuiAppState.class).updateScoreIndicator();
    }
    
    public void restLife() {
        this.currentLives--;
        stateManager.getState(GameGuiAppState.class).updateLivesIndicator(stateManager.getApplication(), getCurrentLives());
    }
    
    public void addLife(){
        this.currentLives++;
        stateManager.getState(GameGuiAppState.class).updateLivesIndicator(stateManager.getApplication(), getCurrentLives());
    }
    
    public String getFormattedScore() {
        return String.format("%08d", score);
    }
}
