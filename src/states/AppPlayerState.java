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
public class AppPlayerState extends AbstractAppState{
    
    private static int INITIAL_DEFAULT_LIVES = 3;
    
    private int currentLives;
    
    private int score;
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
       
        this.currentLives = INITIAL_DEFAULT_LIVES;
    }

    @Override
    public void setEnabled(boolean active) {
        
    }

    @Override
    public void cleanup() {
        
    }

    public int getCurrentLives() {
        return currentLives;
    }

    public void setCurrentLives(int currentLives) {
        this.currentLives = currentLives;
    }
    
    public void restLife(){
        this.currentLives--;
    }
    
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getFormattedScore(){
        return String.format("%08d", score);
    }
    
    
}
